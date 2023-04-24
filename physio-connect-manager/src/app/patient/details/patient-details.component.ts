import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WorkoutPlanService } from '../../plan/workout-plan.service';
import { Observable } from 'rxjs';
import { WorkoutPlanDto } from '../../plan/workout-plan.model';
import { Store } from '@ngrx/store';
import * as fromApp from '../../store/app.reducer';
import { loadPlans } from '../../plan/store/plan.actions';
import { DocumentService } from '../../document/document.service';
import { PatientDocument } from '../../document/list/document.model';
import { Patient } from "../patient.model";
import { PatientService } from "../patient.service";

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css'],
})
export class PatientDetailsComponent implements OnInit {
  patientId: number;
  workoutPlans$: Observable<WorkoutPlanDto[]>;
  workoutPlans: WorkoutPlanDto[];
  documents$: Observable<PatientDocument[]>;
  patient$: Observable<Patient>

  constructor(
    private store: Store<fromApp.AppState>,
    private route: ActivatedRoute,
    private workoutPlanService: WorkoutPlanService,
    private documentService: DocumentService,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.patientId = params['id'];
      this.patient$ = this.patientService.getPatientDetails(this.patientId);
      this.documents$ = this.documentService.getDocumentsWithAddTracking(this.patientId)
      this.workoutPlanService
        .getWorkoutPlans(this.patientId)
        .subscribe((workoutPlans) => {
          this.workoutPlans = workoutPlans;
          this.store.dispatch(loadPlans({ plans: workoutPlans }));
        });
    });
  }

  onDocumentAdded() {
    this.ngOnInit();
  }
}
