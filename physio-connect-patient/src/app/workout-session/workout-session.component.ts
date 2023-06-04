import { Component } from '@angular/core';
import { BehaviorSubject, combineLatest, tap } from 'rxjs';
import { Store } from '@ngrx/store';
import * as fromApp from '../store/app.reducer';
import { PlannedExercise } from '../plan/plan.model';
import { startPlanLoading } from '../plan/store/plan.actions';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { SessionExercise, WorkoutSession } from './workout-session.model';
import {
  exerciseCompleted,
  workoutSessionStart,
} from './store/workout-session.actions';
import * as moment from 'moment';
import { WorkoutSessionService } from './workout-session.service';
import { map, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-workout-session',
  templateUrl: './workout-session.component.html',
  styleUrls: ['./workout-session.component.css'],
})
export class WorkoutSessionComponent {
  selectedExerciseIndex: number = 0;
  selectedExercise: SessionExercise;
  countOfExercisesInSession: number;

  readonly planId$ = this.route.queryParams.pipe(
    map((params) => {
      return params['planId'];
    })
  );

  readonly plannedExercises$ = this.planId$.pipe(
    switchMap((planId) => {
      this.store.dispatch(startPlanLoading({ planId }));
      return this.store.select((state) => state.workoutPlan.plannedExercises);
    })
  );

  readonly startWorkoutSession$ = this.planId$.pipe(
    switchMap((planId) => {
      return this.plannedExercises$.pipe(
        tap((exercises) => {
          let sessionExercises = this.initSessionExercises(exercises);
          this.startWorkoutSession(planId, sessionExercises);
        })
      );
    })
  );

  readonly workoutSessionExercises$ = this.store.select((state) => {
    let sessionExercises =
      state.workoutSession.workoutSession?.completedExercises!;
    this.countOfExercisesInSession = sessionExercises.length;
    return sessionExercises;
  });

  private exerciseSelectedSubject = new BehaviorSubject<number>(0);
  readonly exerciseSelectedAction$ =
    this.exerciseSelectedSubject.asObservable();

  selectedExercise$ = combineLatest([
    this.workoutSessionExercises$,
    this.exerciseSelectedAction$,
  ]).pipe(
    map(([exercises, exerciseIndex]) => {
      this.selectedExerciseIndex = exerciseIndex;
      this.selectedExercise = exercises[exerciseIndex];
      return exercises[exerciseIndex];
    })
  );

  constructor(
    private store: Store<fromApp.AppState>,
    private route: ActivatedRoute,
    private workoutSessionService: WorkoutSessionService,
    private router: Router
  ) {}

  handleNextExerciseClick(exerciseForm: NgForm) {
    this.store.dispatch(
      exerciseCompleted(this.buildCompletedExercise(exerciseForm))
    );
    this.resetExerciseForm(exerciseForm);
    this.exerciseSelectedSubject.next(this.selectedExerciseIndex + 1);
  }

  handlePreviousExerciseClick(exerciseForm: NgForm) {
    // this.store.dispatch(
    //   exerciseCompleted(this.buildCompletedExercise(exerciseForm))
    // );
    this.resetExerciseForm(exerciseForm);
    this.exerciseSelectedSubject.next(this.selectedExerciseIndex - 1);
  }

  handleCompleteSessionClick() {
    this.store
      .select((state) => state.workoutSession.workoutSession!)
      .pipe(
        switchMap((workoutSession) => {
          return this.workoutSessionService.saveWorkoutSession({
            ...workoutSession,
            completedAt: moment().format('yyyy-MM-DDTHH:mm'),
          });
        })
      )
      .subscribe(() => {
        this.router.navigate(['/plan']);
      });
  }

  private startWorkoutSession(planId: number, exercises: SessionExercise[]) {
    const newWorkoutSession: WorkoutSession = {
      planId,
      startedAt: moment().format('yyyy-MM-DDTHH:mm'),
      completedAt: '',
      patientComment: '',
      completedExercises: exercises,
    };
    this.store.dispatch(
      workoutSessionStart({ workoutSession: newWorkoutSession })
    );
  }

  private buildCompletedExercise(exerciseForm: NgForm) {
    return {
      ...this.selectedExercise,
      completedRepetition: this.calculateCompletedReps(
        this.selectedExercise.plannedSets,
        exerciseForm
      ),
      completedSets: this.calculateCompletedSets(
        this.selectedExercise.plannedSets,
        exerciseForm
      ),
      effortLevel: exerciseForm.value.effortLevel,
      painLevel: exerciseForm.value.painLevel,
      patientComment: exerciseForm.value.comment,
    };
  }

  private calculateCompletedReps(numberOfSets: number, form: NgForm) {
    let completedReps = 0;
    for (let i = 1; i <= numberOfSets; i++) {
      let repNum = form.value[`set-${i}`];
      completedReps += repNum;
    }
    return completedReps;
  }

  private calculateCompletedSets(numberOfSets: number, form: NgForm) {
    let completedSets = 0;
    for (let i = 1; i <= numberOfSets; i++) {
      let repNum = form.value[`set-${i}`];
      if (repNum && repNum > 0) completedSets += 1;
    }
    return completedSets;
  }

  private initSessionExercises = (exercises: PlannedExercise[]) => {
    return exercises.map((exercise) => {
      return {
        exerciseId: exercise.id,
        name: exercise.exrxName,
        instructionUrl: exercise.videoInstrUrl,
        plannedRepetition: exercise.exrxRepetition,
        completedRepetition: 0,
        plannedSets: exercise.exrxSets,
        completedSets: 0,
        effortLevel: 0,
        painLevel: 0,
        patientComment: '',
      } as SessionExercise;
    });
  };

  private resetExerciseForm(exerciseForm: NgForm) {
    exerciseForm.resetForm({
      comment: '',
      painLevel: 0,
      effortLevel: 0,
    });
  }
  getExerciseUrl(exercise: SessionExercise) {
    if (!exercise.instructionUrl) {
      return '';
    }
    console.log(exercise.instructionUrl)
    return exercise.instructionUrl;
  }
}
