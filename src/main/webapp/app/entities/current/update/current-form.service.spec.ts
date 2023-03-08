import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../current.test-samples';

import { CurrentFormService } from './current-form.service';

describe('Current Form Service', () => {
  let service: CurrentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CurrentFormService);
  });

  describe('Service methods', () => {
    describe('createCurrentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCurrentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastUpdateLong: expect.any(Object),
            lastUpdateTime: expect.any(Object),
            tempC: expect.any(Object),
            tempF: expect.any(Object),
            isDay: expect.any(Object),
            windMph: expect.any(Object),
            windKph: expect.any(Object),
            windDegree: expect.any(Object),
            windDirection: expect.any(Object),
            pressureMb: expect.any(Object),
            pressureIn: expect.any(Object),
            precipMm: expect.any(Object),
            precipIn: expect.any(Object),
            humidity: expect.any(Object),
            cloud: expect.any(Object),
            feelslikeC: expect.any(Object),
            feelslikeF: expect.any(Object),
            uv: expect.any(Object),
            gustMph: expect.any(Object),
            gustKph: expect.any(Object),
            location: expect.any(Object),
          })
        );
      });

      it('passing ICurrent should create a new form with FormGroup', () => {
        const formGroup = service.createCurrentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastUpdateLong: expect.any(Object),
            lastUpdateTime: expect.any(Object),
            tempC: expect.any(Object),
            tempF: expect.any(Object),
            isDay: expect.any(Object),
            windMph: expect.any(Object),
            windKph: expect.any(Object),
            windDegree: expect.any(Object),
            windDirection: expect.any(Object),
            pressureMb: expect.any(Object),
            pressureIn: expect.any(Object),
            precipMm: expect.any(Object),
            precipIn: expect.any(Object),
            humidity: expect.any(Object),
            cloud: expect.any(Object),
            feelslikeC: expect.any(Object),
            feelslikeF: expect.any(Object),
            uv: expect.any(Object),
            gustMph: expect.any(Object),
            gustKph: expect.any(Object),
            location: expect.any(Object),
          })
        );
      });
    });

    describe('getCurrent', () => {
      it('should return NewCurrent for default Current initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCurrentFormGroup(sampleWithNewData);

        const current = service.getCurrent(formGroup) as any;

        expect(current).toMatchObject(sampleWithNewData);
      });

      it('should return NewCurrent for empty Current initial value', () => {
        const formGroup = service.createCurrentFormGroup();

        const current = service.getCurrent(formGroup) as any;

        expect(current).toMatchObject({});
      });

      it('should return ICurrent', () => {
        const formGroup = service.createCurrentFormGroup(sampleWithRequiredData);

        const current = service.getCurrent(formGroup) as any;

        expect(current).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICurrent should not enable id FormControl', () => {
        const formGroup = service.createCurrentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCurrent should disable id FormControl', () => {
        const formGroup = service.createCurrentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
