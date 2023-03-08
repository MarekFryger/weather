import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CurrentFormService } from './current-form.service';
import { CurrentService } from '../service/current.service';
import { ICurrent } from '../current.model';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';

import { CurrentUpdateComponent } from './current-update.component';

describe('Current Management Update Component', () => {
  let comp: CurrentUpdateComponent;
  let fixture: ComponentFixture<CurrentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let currentFormService: CurrentFormService;
  let currentService: CurrentService;
  let locationService: LocationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CurrentUpdateComponent],
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
      .overrideTemplate(CurrentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CurrentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    currentFormService = TestBed.inject(CurrentFormService);
    currentService = TestBed.inject(CurrentService);
    locationService = TestBed.inject(LocationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Location query and add missing value', () => {
      const current: ICurrent = { id: 456 };
      const location: ILocation = { id: 74936 };
      current.location = location;

      const locationCollection: ILocation[] = [{ id: 84971 }];
      jest.spyOn(locationService, 'query').mockReturnValue(of(new HttpResponse({ body: locationCollection })));
      const additionalLocations = [location];
      const expectedCollection: ILocation[] = [...additionalLocations, ...locationCollection];
      jest.spyOn(locationService, 'addLocationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ current });
      comp.ngOnInit();

      expect(locationService.query).toHaveBeenCalled();
      expect(locationService.addLocationToCollectionIfMissing).toHaveBeenCalledWith(
        locationCollection,
        ...additionalLocations.map(expect.objectContaining)
      );
      expect(comp.locationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const current: ICurrent = { id: 456 };
      const location: ILocation = { id: 98472 };
      current.location = location;

      activatedRoute.data = of({ current });
      comp.ngOnInit();

      expect(comp.locationsSharedCollection).toContain(location);
      expect(comp.current).toEqual(current);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICurrent>>();
      const current = { id: 123 };
      jest.spyOn(currentFormService, 'getCurrent').mockReturnValue(current);
      jest.spyOn(currentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ current });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: current }));
      saveSubject.complete();

      // THEN
      expect(currentFormService.getCurrent).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(currentService.update).toHaveBeenCalledWith(expect.objectContaining(current));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICurrent>>();
      const current = { id: 123 };
      jest.spyOn(currentFormService, 'getCurrent').mockReturnValue({ id: null });
      jest.spyOn(currentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ current: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: current }));
      saveSubject.complete();

      // THEN
      expect(currentFormService.getCurrent).toHaveBeenCalled();
      expect(currentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICurrent>>();
      const current = { id: 123 };
      jest.spyOn(currentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ current });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(currentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLocation', () => {
      it('Should forward to locationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(locationService, 'compareLocation');
        comp.compareLocation(entity, entity2);
        expect(locationService.compareLocation).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
