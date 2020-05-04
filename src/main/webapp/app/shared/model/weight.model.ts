import { Moment } from 'moment';

export interface IWeight {
  id?: number;
  weight?: number;
  timestamp?: Moment;
  userLogin?: string;
  userId?: number;
}

export class Weight implements IWeight {
  constructor(public id?: number, public weight?: number, public timestamp?: Moment, public userLogin?: string, public userId?: number) {}
}
