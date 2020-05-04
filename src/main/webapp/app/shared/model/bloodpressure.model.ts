import { Moment } from 'moment';

export interface IBloodpressure {
  id?: number;
  timestamp?: Moment;
  systolic?: number;
  diastolic?: number;
  userLogin?: string;
  userId?: number;
}

export class Bloodpressure implements IBloodpressure {
  constructor(
    public id?: number,
    public timestamp?: Moment,
    public systolic?: number,
    public diastolic?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
