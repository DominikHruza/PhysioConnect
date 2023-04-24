import { Component, OnDestroy, OnInit } from "@angular/core";
import * as fromApp from '../../store/app.reducer';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Exercise } from '../workout-plan.model';
import { exerciseAdded, exerciseRemoved, exercisesCleared } from "../store/plan.actions";
import { WorkoutPlanService } from '../workout-plan.service';
import { ActivatedRoute } from '@angular/router';
import * as LoadingState from '../../shared/spinner/store/loading.reducer';
import { NgForm } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-plan',
  templateUrl: './add-plan.component.html',
  styleUrls: ['./add-plan.component.css'],
})
export class AddPlanComponent implements OnInit, OnDestroy {
  exercises$: Observable<Exercise[]>;
  isLoading$: Observable<LoadingState.State>;
  private patientId: number;
  resultMsg: string | null;
  type: string;

  constructor(
    private store: Store<fromApp.AppState>,
    private workoutPlanService: WorkoutPlanService,
    private route: ActivatedRoute,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.patientId = parseInt(params['patientId']);
    });
    this.exercises$ = this.store.select((state) => state.workoutPlan.exercises);
    this.isLoading$ = this.store.select((state) => state.isLoading);
  }

  onRemoveBtnClick(index: number) {
    this.store.dispatch(exerciseRemoved({ index }));
  }

  onPlanSave() {
    this.workoutPlanService.addNewWorkoutPlan(this.patientId);
    this.resultMsg = 'Plan saved!';
    this.type = 'alert-success';
  }

  onHandleAlert() {
    this.resultMsg = null;
  }

  handleAddExerciseSubmit($event: {
    addExerciseForm: NgForm;
    file: File | null;
  }) {
    const newExercise: Exercise = {
      name: $event.addExerciseForm.value.name,
      sets: $event.addExerciseForm.value.setNum,
      reps: $event.addExerciseForm.value.repNum,
      description: $event.addExerciseForm.value.description,
      file: $event.file,
    };

    this.store.dispatch(exerciseAdded(newExercise));
    $event.addExerciseForm.resetForm();
  }

  ngOnDestroy(): void {
    this.store.dispatch(exercisesCleared())
  }
}
