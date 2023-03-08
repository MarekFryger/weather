import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICurrent } from '../current.model';
import { CurrentService } from '../service/current.service';

@Injectable({ providedIn: 'root' })
export class CurrentRoutingResolveService implements Resolve<ICurrent | null> {
  constructor(protected service: CurrentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICurrent | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((current: HttpResponse<ICurrent>) => {
          if (current.body) {
            return of(current.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
