import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ConditionFormService, ConditionFormGroup } from './condition-form.service';
import { ICondition } from '../condition.model';
import { ConditionService } from '../service/condition.service';
import { ICurrent } from 'app/entities/current/current.model';
import { CurrentService } from 'app/entities/current/service/current.service';

@Component({
  selector: 'jhi-condition-update',
  templateUrl: './condition-update.component.html',
})
export class ConditionUpdateComponent implements OnInit {
  isSaving = false;
  condition: ICondition | null = null;

  currentsSharedCollection: ICurrent[] = [];

  editForm: ConditionFormGroup = this.conditionFormService.createConditionFormGroup();

  constructor(
    protected conditionService: ConditionService,
    protected conditionFormService: ConditionFormService,
    protected currentService: CurrentService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCurrent = (o1: ICurrent | null, o2: ICurrent | null): boolean => this.currentService.compareCurrent(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ condition }) => {
      this.condition = condition;
      if (condition) {
        this.updateForm(condition);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const condition = this.conditionFormService.getCondition(this.editForm);
    if (condition.id !== null) {
      this.subscribeToSaveResponse(this.conditionService.update(condition));
    } else {
      this.subscribeToSaveResponse(this.conditionService.create(condition));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICondition>>): void {
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

  protected updateForm(condition: ICondition): void {
    this.condition = condition;
    this.conditionFormService.resetForm(this.editForm, condition);

    this.currentsSharedCollection = this.currentService.addCurrentToCollectionIfMissing<ICurrent>(
      this.currentsSharedCollection,
      condition.current
    );
  }

  protected loadRelationshipsOptions(): void {
    this.currentService
      .query()
      .pipe(map((res: HttpResponse<ICurrent[]>) => res.body ?? []))
      .pipe(map((currents: ICurrent[]) => this.currentService.addCurrentToCollectionIfMissing<ICurrent>(currents, this.condition?.current)))
      .subscribe((currents: ICurrent[]) => (this.currentsSharedCollection = currents));
  }
}
