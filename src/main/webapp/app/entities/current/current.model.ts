import { ILocation } from 'app/entities/location/location.model';

export interface ICurrent {
  id: number;
  lastUpdateLong?: number | null;
  lastUpdateTime?: string | null;
  tempC?: number | null;
  tempF?: number | null;
  isDay?: boolean | null;
  windMph?: number | null;
  windKph?: number | null;
  windDegree?: number | null;
  windDirection?: string | null;
  pressureMb?: number | null;
  pressureIn?: number | null;
  precipMm?: number | null;
  precipIn?: number | null;
  humidity?: number | null;
  cloud?: number | null;
  feelslikeC?: number | null;
  feelslikeF?: number | null;
  uv?: number | null;
  gustMph?: number | null;
  gustKph?: number | null;
  location?: Pick<ILocation, 'id'> | null;
}

export type NewCurrent = Omit<ICurrent, 'id'> & { id: null };
