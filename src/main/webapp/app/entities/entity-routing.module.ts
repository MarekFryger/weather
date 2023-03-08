import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'condition',
        data: { pageTitle: 'weatherApp.condition.home.title' },
        loadChildren: () => import('./condition/condition.module').then(m => m.ConditionModule),
      },
      {
        path: 'current',
        data: { pageTitle: 'weatherApp.current.home.title' },
        loadChildren: () => import('./current/current.module').then(m => m.CurrentModule),
      },
      {
        path: 'location',
        data: { pageTitle: 'weatherApp.location.home.title' },
        loadChildren: () => import('./location/location.module').then(m => m.LocationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
