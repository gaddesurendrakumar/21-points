import { Moment } from 'moment';

export interface IPoints {
  id?: number;
  date?: Moment;
  exercise?: number;
  meals?: number;
  alcohol?: number;
  notes?: string;
  userLogin?: string;
  userId?: number;
}

export class Points implements IPoints {
  constructor(
    public id?: number,
    public date?: Moment,
    public exercise?: number,
    public meals?: number,
    public alcohol?: number,
    public notes?: string,
    public userLogin?: string,
    public userId?: number
  ) {}
}
