import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TwentyOnePointsSharedModule } from 'app/shared/shared.module';
import { PreferencesComponent } from './preferences.component';
import { PreferencesDetailComponent } from './preferences-detail.component';
import { preferencesRoute } from './preferences.route';

@NgModule({
  imports: [TwentyOnePointsSharedModule, RouterModule.forChild(preferencesRoute)],
  declarations: [PreferencesComponent, PreferencesDetailComponent]
})
export class TwentyOnePointsPreferencesModule {}
