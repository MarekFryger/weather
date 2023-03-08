import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CurrentComponent } from '../list/current.component';
import { CurrentDetailComponent } from '../detail/current-detail.component';
import { CurrentUpdateComponent } from '../update/current-update.component';
import { CurrentRoutingResolveService } from './current-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const currentRoute: Routes = [
  {
    path: '',
    component: CurrentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CurrentDetailComponent,
    resolve: {
      current: CurrentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CurrentUpdateComponent,
    resolve: {
      current: CurrentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CurrentUpdateComponent,
    resolve: {
      current: CurrentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(currentRoute)],
  exports: [RouterModule],
})
export class CurrentRoutingModule {}
