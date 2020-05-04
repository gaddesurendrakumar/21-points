import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBloodpressure, Bloodpressure } from 'app/shared/model/bloodpressure.model';
import { BloodpressureService } from './bloodpressure.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-bloodpressure-update',
  templateUrl: './bloodpressure-update.component.html'
})
export class BloodpressureUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    timestamp: [null, [Validators.required]],
    systolic: [null, [Validators.required]],
    diastolic: [null, [Validators.required]],
    userId: []
  });

  constructor(
    protected bloodpressureService: BloodpressureService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bloodpressure }) => {
      if (!bloodpressure.id) {
        const today = moment().startOf('day');
        bloodpressure.timestamp = today;
      }

      this.updateForm(bloodpressure);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(bloodpressure: IBloodpressure): void {
    this.editForm.patchValue({
      id: bloodpressure.id,
      timestamp: bloodpressure.timestamp ? bloodpressure.timestamp.format(DATE_TIME_FORMAT) : null,
      systolic: bloodpressure.systolic,
      diastolic: bloodpressure.diastolic,
      userId: bloodpressure.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bloodpressure = this.createFromForm();
    if (bloodpressure.id !== undefined) {
      this.subscribeToSaveResponse(this.bloodpressureService.update(bloodpressure));
    } else {
      this.subscribeToSaveResponse(this.bloodpressureService.create(bloodpressure));
    }
  }

  private createFromForm(): IBloodpressure {
    return {
      ...new Bloodpressure(),
      id: this.editForm.get(['id'])!.value,
      timestamp: this.editForm.get(['timestamp'])!.value ? moment(this.editForm.get(['timestamp'])!.value, DATE_TIME_FORMAT) : undefined,
      systolic: this.editForm.get(['systolic'])!.value,
      diastolic: this.editForm.get(['diastolic'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBloodpressure>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
