import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Patient } from '../patient.model';
import * as fromApp from '../../store/app.reducer';
import { PatientService } from '../patient.service';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import * as LoadingState from '../../shared/spinner/store/loading.reducer';

@Component({
  selector: 'app-add-patient',
  templateUrl: './add-patient.component.html',
  styleUrls: ['./add-patient.component.css'],
})
export class AddPatientComponent implements OnInit {
  practitionerId: number;
  resultMsg: string | null;
  type: string;
  isLoading$: Observable<LoadingState.State>;

  constructor(
    private patientService: PatientService,
    private store: Store<fromApp.AppState>
  ) {}

  ngOnInit(): void {
    this.isLoading$ = this.store.select((state) => state.isLoading);
    this.store
      .select((state) => state.auth.loggedInUser)
      .subscribe((practitioner) => {
        this.practitionerId = practitioner!.id;
      });
  }

  onAddPatientSubmit(addPatientForm: NgForm) {
    let newPatient: Patient = {
      id: null,
      practitionerId: this.practitionerId,
      email: addPatientForm.value.email,
      firstname: addPatientForm.value.firstname,
      lastname: addPatientForm.value.lastname,
      diagnosis: addPatientForm.value.diagnosis,
    };

    this.patientService.addNew(newPatient).subscribe({
      next: () => {
        this.resultMsg = 'Patient saved!';
        this.type = 'alert-success';
      },
      error: (errorMsg) => {
        this.resultMsg = errorMsg;
        this.type = 'alert-danger';
      },
    });
  }

  onHandleAlert() {
    this.resultMsg = null;
  }
}
