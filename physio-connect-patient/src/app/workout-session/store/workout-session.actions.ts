import { createAction, props } from '@ngrx/store';
import { SessionExercise, WorkoutSession } from '../workout-session.model';

export const START_WORKOUT_SESSION = '[WORKOUT_SESSION] Start';
export const EXERCISE_COMPLETED = '[WORKOUT_SESSION] Exercise Completed';

export const workoutSessionStart = createAction(
  START_WORKOUT_SESSION,
  props<{ workoutSession: WorkoutSession }>()
);

export const exerciseCompleted = createAction(
  EXERCISE_COMPLETED,
  props<SessionExercise>()
);
