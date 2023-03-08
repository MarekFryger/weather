import { ICurrent } from 'app/entities/current/current.model';

export interface ICondition {
  id: number;
  description?: string | null;
  iconLink?: string | null;
  code?: number | null;
  current?: Pick<ICurrent, 'id'> | null;
}

export type NewCondition = Omit<ICondition, 'id'> & { id: null };
