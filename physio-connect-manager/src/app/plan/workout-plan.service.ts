import { Injectable } from '@angular/core';
import * as fromApp from '../store/app.reducer';
import { Store } from '@ngrx/store';
import { HttpClient } from '@angular/common/http';
import { Exercise, ExerciseDto, WorkoutPlanDto } from './workout-plan.model';
import { VideoInstructionService } from '../video-instruction/video-instruction.service';
import { forkJoin, Observable, of, tap } from 'rxjs';
import {
  loadingEnd,
  loadingStart,
} from '../shared/spinner/store/loading.actions';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class WorkoutPlanService {
  patientId: number;
  practitionerId: number;

  constructor(
    private store: Store<fromApp.AppState>,
    private http: HttpClient,
    private videoInstructionService: VideoInstructionService
  ) {}

  addNewWorkoutPlan(patientId: number) {
    this.store.dispatch(loadingStart());
    const newPlan = this.prepareAddPlanRequestBody(patientId);
    forkJoin(this.uploadVideos(newPlan)).subscribe(() => {
      this.http.post('/api/workout-plan', newPlan).subscribe(() => {
        this.store.dispatch(loadingEnd());
      });
    });
  }

  editNewWorkoutPlan(patientId: number) {
    this.store.dispatch(loadingStart());
    const updatedPlan = this.prepareEditPlanRequestBody();
    forkJoin(this.uploadVideos(updatedPlan)).subscribe(() => {
      this.http
        .put(`/api/workout-plan/${updatedPlan.id}`, updatedPlan)
        .subscribe(() => {
          this.store.dispatch(loadingEnd());
        });
    });
  }

  getWorkoutPlans(patientId: number) {
    this.store.dispatch(loadingStart());
    return this.http
      .get<WorkoutPlanDto[]>(`/api/workout-plan/all/${patientId}`)
      .pipe(
        map((response) => {
          this.store.dispatch(loadingEnd());
          return response;
        })
      );
  }

  getPlanWithExercises(planId: number) {
    this.store.dispatch(loadingStart());
    return this.http
      .get<WorkoutPlanDto>(`/api/workout-plan/exercises/${planId}`)
      .pipe(
        map((response) => {
          this.store.dispatch(loadingEnd());
          return response;
        })
      );
  }

  private prepareAddPlanRequestBody(patientId: number) {
    let newPlan = {};

    this.store
      .select((state) => state)
      .subscribe((state) => {
        this.prepareExerciseDtos(state.workoutPlan.exercises);
        newPlan = {
          practitionerId: state.auth.loggedInUser!.id,
          patientId,
          exercises: this.prepareExerciseDtos(state.workoutPlan.exercises),
          isActive: true,
        } as WorkoutPlanDto;
      });
    return newPlan as WorkoutPlanDto;
  }

  private prepareEditPlanRequestBody() {
    let updatedPlan = {};
    this.store
      .select((state) => state)
      .subscribe((state) => {
        updatedPlan = {
          ...state.workoutPlan.workoutPlanForEdit!,
          exercises: this.prepareExerciseDtos(
            state.workoutPlan.workoutPlanForEdit!.exercises
          ),
          isActive: true,
        } as WorkoutPlanDto;
      });
    return updatedPlan as WorkoutPlanDto;
  }

  private prepareExerciseDtos(exercises: Exercise[] | ExerciseDto[]) {
    return exercises.map((exercise) => {
      const exerciseDto: ExerciseDto = {
        id: (exercise as Exercise).id || (exercise as ExerciseDto).id,
        exrxName:
          (exercise as Exercise).name || (exercise as ExerciseDto).exrxName,
        exrxRepetition:
          (exercise as Exercise).reps ||
          (exercise as ExerciseDto).exrxRepetition,
        exrxSets:
          (exercise as Exercise).sets || (exercise as ExerciseDto).exrxSets,
        videoInstrUrl:
          (exercise as Exercise).file ||
          (exercise as ExerciseDto).videoInstrUrl,
        description: exercise.description,
      };
      return exerciseDto;
    });
  }

  private uploadVideos(newPlan: WorkoutPlanDto) {
    let uploadObservableList: Observable<{ videoUrl: string }>[] = [
      of({ videoUrl: '' }),
    ];
    newPlan.exercises.map((exercise) => {
      if (
        !exercise.id &&
        exercise.videoInstrUrl !== null &&
        exercise.videoInstrUrl instanceof File
      ) {
        let uploadResultObservable$ = this.videoInstructionService
          .uploadVideoInstruction(exercise.videoInstrUrl)
          .pipe(
            tap((response) => {
              exercise.videoInstrUrl = (
                response as { videoUrl: string }
              ).videoUrl;
            })
          );
        uploadObservableList.push(
          uploadResultObservable$ as Observable<{ videoUrl: string }>
        );
      }
    });
    return uploadObservableList;
  }
}
