import { ILocation, NewLocation } from './location.model';

export const sampleWithRequiredData: ILocation = {
  id: 91847,
  name: 'parsing',
  lat: 93501,
  lon: 8097,
};

export const sampleWithPartialData: ILocation = {
  id: 92404,
  name: 'Island',
  country: 'Paraguay',
  lat: 18692,
  lon: 79889,
  timeZone: 'Virginia Arkansas primary',
};

export const sampleWithFullData: ILocation = {
  id: 16917,
  name: 'Internal',
  country: 'Aruba',
  lat: 41809,
  lon: 27801,
  timeZone: 'Fresh Wooden',
};

export const sampleWithNewData: NewLocation = {
  name: 'panel Tools',
  lat: 6372,
  lon: 25255,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
