import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CurrentComponent } from './list/current.component';
import { CurrentDetailComponent } from './detail/current-detail.component';
import { CurrentUpdateComponent } from './update/current-update.component';
import { CurrentDeleteDialogComponent } from './delete/current-delete-dialog.component';
import { CurrentRoutingModule } from './route/current-routing.module';

@NgModule({
  imports: [SharedModule, CurrentRoutingModule],
  declarations: [CurrentComponent, CurrentDetailComponent, CurrentUpdateComponent, CurrentDeleteDialogComponent],
})
export class CurrentModule {}
