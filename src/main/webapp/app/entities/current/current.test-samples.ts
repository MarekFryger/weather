import { ICurrent, NewCurrent } from './current.model';

export const sampleWithRequiredData: ICurrent = {
  id: 42850,
};

export const sampleWithPartialData: ICurrent = {
  id: 45993,
  lastUpdateTime: 'Michigan deposit Carolina',
  tempF: 65516,
  windMph: 19753,
  pressureMb: 24311,
  pressureIn: 30378,
  precipMm: 3312,
  cloud: 28042,
  feelslikeC: 51944,
  feelslikeF: 90689,
  gustMph: 72709,
  gustKph: 82578,
};

export const sampleWithFullData: ICurrent = {
  id: 31530,
  lastUpdateLong: 79286,
  lastUpdateTime: 'Union Small quantifying',
  tempC: 99756,
  tempF: 24814,
  isDay: false,
  windMph: 72262,
  windKph: 43075,
  windDegree: 29577,
  windDirection: 'Pound functionalities',
  pressureMb: 6646,
  pressureIn: 20825,
  precipMm: 66417,
  precipIn: 69164,
  humidity: 69287,
  cloud: 93191,
  feelslikeC: 27127,
  feelslikeF: 11421,
  uv: 72059,
  gustMph: 40067,
  gustKph: 41204,
};

export const sampleWithNewData: NewCurrent = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
