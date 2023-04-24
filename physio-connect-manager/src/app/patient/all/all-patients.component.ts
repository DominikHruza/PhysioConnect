import { Component, OnInit } from '@angular/core';
import { PatientService } from '../patient.service';
import { Observable } from 'rxjs';
import { Patient } from '../patient.model';
import * as fromApp from '../../store/app.reducer';
import { Store } from '@ngrx/store';

@Component({
  selector: 'app-all',
  templateUrl: './all-patients.component.html',
  styleUrls: ['./all-patients.component.css'],
})
export class AllPatientsComponent implements OnInit {
  practitionerId: number;
  allPatients$: Observable<Patient[]>;

  constructor(
    private patientService: PatientService,
    private store: Store<fromApp.AppState>
  ) {}

  ngOnInit(): void {
    this.store
      .select((state) => state.auth.loggedInUser)
      .subscribe((practitioner) => {
        this.practitionerId = practitioner!.id;
      });

    this.allPatients$ = this.patientService.getAll(this.practitionerId);
  }
}
