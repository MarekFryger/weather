import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICurrent, NewCurrent } from '../current.model';

export type PartialUpdateCurrent = Partial<ICurrent> & Pick<ICurrent, 'id'>;

export type EntityResponseType = HttpResponse<ICurrent>;
export type EntityArrayResponseType = HttpResponse<ICurrent[]>;

@Injectable({ providedIn: 'root' })
export class CurrentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/currents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(current: NewCurrent): Observable<EntityResponseType> {
    return this.http.post<ICurrent>(this.resourceUrl, current, { observe: 'response' });
  }

  update(current: ICurrent): Observable<EntityResponseType> {
    return this.http.put<ICurrent>(`${this.resourceUrl}/${this.getCurrentIdentifier(current)}`, current, { observe: 'response' });
  }

  partialUpdate(current: PartialUpdateCurrent): Observable<EntityResponseType> {
    return this.http.patch<ICurrent>(`${this.resourceUrl}/${this.getCurrentIdentifier(current)}`, current, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurrent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurrent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCurrentIdentifier(current: Pick<ICurrent, 'id'>): number {
    return current.id;
  }

  compareCurrent(o1: Pick<ICurrent, 'id'> | null, o2: Pick<ICurrent, 'id'> | null): boolean {
    return o1 && o2 ? this.getCurrentIdentifier(o1) === this.getCurrentIdentifier(o2) : o1 === o2;
  }

  addCurrentToCollectionIfMissing<Type extends Pick<ICurrent, 'id'>>(
    currentCollection: Type[],
    ...currentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const currents: Type[] = currentsToCheck.filter(isPresent);
    if (currents.length > 0) {
      const currentCollectionIdentifiers = currentCollection.map(currentItem => this.getCurrentIdentifier(currentItem)!);
      const currentsToAdd = currents.filter(currentItem => {
        const currentIdentifier = this.getCurrentIdentifier(currentItem);
        if (currentCollectionIdentifiers.includes(currentIdentifier)) {
          return false;
        }
        currentCollectionIdentifiers.push(currentIdentifier);
        return true;
      });
      return [...currentsToAdd, ...currentCollection];
    }
    return currentCollection;
  }
}
