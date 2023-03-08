import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ConditionFormService } from './condition-form.service';
import { ConditionService } from '../service/condition.service';
import { ICondition } from '../condition.model';
import { ICurrent } from 'app/entities/current/current.model';
import { CurrentService } from 'app/entities/current/service/current.service';

import { ConditionUpdateComponent } from './condition-update.component';

describe('Condition Management Update Component', () => {
  let comp: ConditionUpdateComponent;
  let fixture: ComponentFixture<ConditionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let conditionFormService: ConditionFormService;
  let conditionService: ConditionService;
  let currentService: CurrentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ConditionUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ConditionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConditionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    conditionFormService = TestBed.inject(ConditionFormService);
    conditionService = TestBed.inject(ConditionService);
    currentService = TestBed.inject(CurrentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Current query and add missing value', () => {
      const condition: ICondition = { id: 456 };
      const current: ICurrent = { id: 88751 };
      condition.current = current;

      const currentCollection: ICurrent[] = [{ id: 90173 }];
      jest.spyOn(currentService, 'query').mockReturnValue(of(new HttpResponse({ body: currentCollection })));
      const additionalCurrents = [current];
      const expectedCollection: ICurrent[] = [...additionalCurrents, ...currentCollection];
      jest.spyOn(currentService, 'addCurrentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ condition });
      comp.ngOnInit();

      expect(currentService.query).toHaveBeenCalled();
      expect(currentService.addCurrentToCollectionIfMissing).toHaveBeenCalledWith(
        currentCollection,
        ...additionalCurrents.map(expect.objectContaining)
      );
      expect(comp.currentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const condition: ICondition = { id: 456 };
      const current: ICurrent = { id: 24162 };
      condition.current = current;

      activatedRoute.data = of({ condition });
      comp.ngOnInit();

      expect(comp.currentsSharedCollection).toContain(current);
      expect(comp.condition).toEqual(condition);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICondition>>();
      const condition = { id: 123 };
      jest.spyOn(conditionFormService, 'getCondition').mockReturnValue(condition);
      jest.spyOn(conditionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ condition });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: condition }));
      saveSubject.complete();

      // THEN
      expect(conditionFormService.getCondition).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(conditionService.update).toHaveBeenCalledWith(expect.objectContaining(condition));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICondition>>();
      const condition = { id: 123 };
      jest.spyOn(conditionFormService, 'getCondition').mockReturnValue({ id: null });
      jest.spyOn(conditionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ condition: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: condition }));
      saveSubject.complete();

      // THEN
      expect(conditionFormService.getCondition).toHaveBeenCalled();
      expect(conditionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICondition>>();
      const condition = { id: 123 };
      jest.spyOn(conditionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ condition });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(conditionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCurrent', () => {
      it('Should forward to currentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(currentService, 'compareCurrent');
        comp.compareCurrent(entity, entity2);
        expect(currentService.compareCurrent).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
