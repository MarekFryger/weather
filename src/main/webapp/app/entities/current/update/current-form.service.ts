import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICurrent, NewCurrent } from '../current.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICurrent for edit and NewCurrentFormGroupInput for create.
 */
type CurrentFormGroupInput = ICurrent | PartialWithRequiredKeyOf<NewCurrent>;

type CurrentFormDefaults = Pick<NewCurrent, 'id' | 'isDay'>;

type CurrentFormGroupContent = {
  id: FormControl<ICurrent['id'] | NewCurrent['id']>;
  lastUpdateLong: FormControl<ICurrent['lastUpdateLong']>;
  lastUpdateTime: FormControl<ICurrent['lastUpdateTime']>;
  tempC: FormControl<ICurrent['tempC']>;
  tempF: FormControl<ICurrent['tempF']>;
  isDay: FormControl<ICurrent['isDay']>;
  windMph: FormControl<ICurrent['windMph']>;
  windKph: FormControl<ICurrent['windKph']>;
  windDegree: FormControl<ICurrent['windDegree']>;
  windDirection: FormControl<ICurrent['windDirection']>;
  pressureMb: FormControl<ICurrent['pressureMb']>;
  pressureIn: FormControl<ICurrent['pressureIn']>;
  precipMm: FormControl<ICurrent['precipMm']>;
  precipIn: FormControl<ICurrent['precipIn']>;
  humidity: FormControl<ICurrent['humidity']>;
  cloud: FormControl<ICurrent['cloud']>;
  feelslikeC: FormControl<ICurrent['feelslikeC']>;
  feelslikeF: FormControl<ICurrent['feelslikeF']>;
  uv: FormControl<ICurrent['uv']>;
  gustMph: FormControl<ICurrent['gustMph']>;
  gustKph: FormControl<ICurrent['gustKph']>;
  location: FormControl<ICurrent['location']>;
};

export type CurrentFormGroup = FormGroup<CurrentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CurrentFormService {
  createCurrentFormGroup(current: CurrentFormGroupInput = { id: null }): CurrentFormGroup {
    const currentRawValue = {
      ...this.getFormDefaults(),
      ...current,
    };
    return new FormGroup<CurrentFormGroupContent>({
      id: new FormControl(
        { value: currentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      lastUpdateLong: new FormControl(currentRawValue.lastUpdateLong),
      lastUpdateTime: new FormControl(currentRawValue.lastUpdateTime),
      tempC: new FormControl(currentRawValue.tempC),
      tempF: new FormControl(currentRawValue.tempF),
      isDay: new FormControl(currentRawValue.isDay),
      windMph: new FormControl(currentRawValue.windMph),
      windKph: new FormControl(currentRawValue.windKph),
      windDegree: new FormControl(currentRawValue.windDegree),
      windDirection: new FormControl(currentRawValue.windDirection),
      pressureMb: new FormControl(currentRawValue.pressureMb),
      pressureIn: new FormControl(currentRawValue.pressureIn),
      precipMm: new FormControl(currentRawValue.precipMm),
      precipIn: new FormControl(currentRawValue.precipIn),
      humidity: new FormControl(currentRawValue.humidity),
      cloud: new FormControl(currentRawValue.cloud),
      feelslikeC: new FormControl(currentRawValue.feelslikeC),
      feelslikeF: new FormControl(currentRawValue.feelslikeF),
      uv: new FormControl(currentRawValue.uv),
      gustMph: new FormControl(currentRawValue.gustMph),
      gustKph: new FormControl(currentRawValue.gustKph),
      location: new FormControl(currentRawValue.location),
    });
  }

  getCurrent(form: CurrentFormGroup): ICurrent | NewCurrent {
    return form.getRawValue() as ICurrent | NewCurrent;
  }

  resetForm(form: CurrentFormGroup, current: CurrentFormGroupInput): void {
    const currentRawValue = { ...this.getFormDefaults(), ...current };
    form.reset(
      {
        ...currentRawValue,
        id: { value: currentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CurrentFormDefaults {
    return {
      id: null,
      isDay: false,
    };
  }
}
