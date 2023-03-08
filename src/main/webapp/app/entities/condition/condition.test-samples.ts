import { ICondition, NewCondition } from './condition.model';

export const sampleWithRequiredData: ICondition = {
  id: 6098,
};

export const sampleWithPartialData: ICondition = {
  id: 35744,
};

export const sampleWithFullData: ICondition = {
  id: 71076,
  description: 'cutting-edge Fiji',
  iconLink: 'Analyst virtual',
  code: 187,
};

export const sampleWithNewData: NewCondition = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
