import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CurrentFormService, CurrentFormGroup } from './current-form.service';
import { ICurrent } from '../current.model';
import { CurrentService } from '../service/current.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';

@Component({
  selector: 'jhi-current-update',
  templateUrl: './current-update.component.html',
})
export class CurrentUpdateComponent implements OnInit {
  isSaving = false;
  current: ICurrent | null = null;

  locationsSharedCollection: ILocation[] = [];

  editForm: CurrentFormGroup = this.currentFormService.createCurrentFormGroup();

  constructor(
    protected currentService: CurrentService,
    protected currentFormService: CurrentFormService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLocation = (o1: ILocation | null, o2: ILocation | null): boolean => this.locationService.compareLocation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ current }) => {
      this.current = current;
      if (current) {
        this.updateForm(current);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const current = this.currentFormService.getCurrent(this.editForm);
    if (current.id !== null) {
      this.subscribeToSaveResponse(this.currentService.update(current));
    } else {
      this.subscribeToSaveResponse(this.currentService.create(current));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(current: ICurrent): void {
    this.current = current;
    this.currentFormService.resetForm(this.editForm, current);

    this.locationsSharedCollection = this.locationService.addLocationToCollectionIfMissing<ILocation>(
      this.locationsSharedCollection,
      current.location
    );
  }

  protected loadRelationshipsOptions(): void {
    this.locationService
      .query()
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) => this.locationService.addLocationToCollectionIfMissing<ILocation>(locations, this.current?.location))
      )
      .subscribe((locations: ILocation[]) => (this.locationsSharedCollection = locations));
  }
}
