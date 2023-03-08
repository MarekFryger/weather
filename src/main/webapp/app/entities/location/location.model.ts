export interface ILocation {
  id: number;
  name?: string | null;
  country?: string | null;
  lat?: number | null;
  lon?: number | null;
  timeZone?: string | null;
}

export type NewLocation = Omit<ILocation, 'id'> & { id: null };
