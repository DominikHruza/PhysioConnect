import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Exercise, ExerciseDto } from '../workout-plan.model';
import * as LoadingState from '../../shared/spinner/store/loading.reducer';
import { WorkoutPlanService } from '../workout-plan.service';
import { Store } from '@ngrx/store';
import * as fromApp from '../../store/app.reducer';
import {
  planEditExerciseAdded,
  planEditExerciseRemoved,
  startLoadEditPlan,
} from '../store/plan.actions';
import { ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EditExerciseComponent } from '../../exercise/edit/edit-exercise.component';

@Component({
  selector: 'app-plan-details',
  templateUrl: './plan-details.component.html',
  styleUrls: ['./plan-details.component.css'],
})
export class PlanDetailsComponent implements OnInit {
  exercises$: Observable<ExerciseDto[]>;
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
    let planId = parseInt(this.route.snapshot.params['id']);
    this.store.dispatch(startLoadEditPlan({ planId }));
    this.exercises$ = this.store.select(
      (state) => state.workoutPlan.workoutPlanForEdit?.exercises!
    );
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

    this.store.dispatch(planEditExerciseAdded(newExercise));
  }

  onPlanSave() {
    this.workoutPlanService.editNewWorkoutPlan(this.patientId);
    this.resultMsg = 'Plan updated!';
    this.type = 'alert-success';
  }

  onRemoveBtnClick(index: number) {
    this.store.dispatch(planEditExerciseRemoved({ index }));
  }

  onHandleAlert() {}

  onEditClick(exercise: ExerciseDto, i: number) {
    let modalRef = this.modalService.open(EditExerciseComponent);
    modalRef.componentInstance.exerciseIndex = i;
    modalRef.componentInstance.exercise = exercise;
  }

  getExerciseUrl(exercise: ExerciseDto) {
    if (!exercise.videoInstrUrl && typeof exercise.videoInstrUrl !== 'string') {
      return '';
    }

    return exercise.videoInstrUrl as string;
  }
}
