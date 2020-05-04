import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'points',
        loadChildren: () => import('./points/points.module').then(m => m.TwentyOnePointsPointsModule)
      },
      {
        path: 'weight',
        loadChildren: () => import('./weight/weight.module').then(m => m.TwentyOnePointsWeightModule)
      },
      {
        path: 'bloodpressure',
        loadChildren: () => import('./bloodpressure/bloodpressure.module').then(m => m.TwentyOnePointsBloodpressureModule)
      },
      {
        path: 'preferences',
        loadChildren: () => import('./preferences/preferences.module').then(m => m.TwentyOnePointsPreferencesModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TwentyOnePointsEntityModule {}
