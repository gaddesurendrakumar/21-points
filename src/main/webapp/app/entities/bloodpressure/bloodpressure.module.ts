import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TwentyOnePointsSharedModule } from 'app/shared/shared.module';
import { BloodpressureComponent } from './bloodpressure.component';
import { BloodpressureDetailComponent } from './bloodpressure-detail.component';
import { BloodpressureUpdateComponent } from './bloodpressure-update.component';
import { BloodpressureDeleteDialogComponent } from './bloodpressure-delete-dialog.component';
import { bloodpressureRoute } from './bloodpressure.route';

@NgModule({
  imports: [TwentyOnePointsSharedModule, RouterModule.forChild(bloodpressureRoute)],
  declarations: [BloodpressureComponent, BloodpressureDetailComponent, BloodpressureUpdateComponent, BloodpressureDeleteDialogComponent],
  entryComponents: [BloodpressureDeleteDialogComponent]
})
export class TwentyOnePointsBloodpressureModule {}
