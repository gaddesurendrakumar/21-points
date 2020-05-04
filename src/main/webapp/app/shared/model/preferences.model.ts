import { Units } from 'app/shared/model/enumerations/units.model';

export interface IPreferences {
  id?: number;
  weeklyGoal?: number;
  weightUnits?: Units;
  userLogin?: string;
  userId?: number;
}

export class Preferences implements IPreferences {
  constructor(
    public id?: number,
    public weeklyGoal?: number,
    public weightUnits?: Units,
    public userLogin?: string,
    public userId?: number
  ) {}
}
