import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBloodpressure } from 'app/shared/model/bloodpressure.model';
import { BloodpressureService } from './bloodpressure.service';

@Component({
  templateUrl: './bloodpressure-delete-dialog.component.html'
})
export class BloodpressureDeleteDialogComponent {
  bloodpressure?: IBloodpressure;

  constructor(
    protected bloodpressureService: BloodpressureService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bloodpressureService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bloodpressureListModification');
      this.activeModal.close();
    });
  }
}
