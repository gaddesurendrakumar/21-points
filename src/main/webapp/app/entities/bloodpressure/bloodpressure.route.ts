import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBloodpressure, Bloodpressure } from 'app/shared/model/bloodpressure.model';
import { BloodpressureService } from './bloodpressure.service';
import { BloodpressureComponent } from './bloodpressure.component';
import { BloodpressureDetailComponent } from './bloodpressure-detail.component';
import { BloodpressureUpdateComponent } from './bloodpressure-update.component';

@Injectable({ providedIn: 'root' })
export class BloodpressureResolve implements Resolve<IBloodpressure> {
  constructor(private service: BloodpressureService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBloodpressure> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bloodpressure: HttpResponse<Bloodpressure>) => {
          if (bloodpressure.body) {
            return of(bloodpressure.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bloodpressure());
  }
}

export const bloodpressureRoute: Routes = [
  {
    path: '',
    component: BloodpressureComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'twentyOnePointsApp.bloodpressure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BloodpressureDetailComponent,
    resolve: {
      bloodpressure: BloodpressureResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'twentyOnePointsApp.bloodpressure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BloodpressureUpdateComponent,
    resolve: {
      bloodpressure: BloodpressureResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'twentyOnePointsApp.bloodpressure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BloodpressureUpdateComponent,
    resolve: {
      bloodpressure: BloodpressureResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'twentyOnePointsApp.bloodpressure.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
