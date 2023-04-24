import {WorkoutSession} from "../workout-session.model";
import {createReducer, on} from "@ngrx/store";
import {exerciseCompleted, workoutSessionStart} from "./workout-session.actions";

export interface State {
  workoutSession: WorkoutSession | null
}

const initialState: State = {
  workoutSession: null
}

export const workoutSessionReducer = createReducer(
  initialState,
  on(workoutSessionStart, (state, {workoutSession}) =>({...state, workoutSession})),
  on(exerciseCompleted, (state, payload) => (
    {
      ...state,
      workoutSession:
      {
        ...state.workoutSession!,
        completedExercises: state.workoutSession!.completedExercises.map(
          (exercise, index) => exercise.exerciseId === payload.exerciseId ?
            {...exercise,
              completedSets: payload.completedSets,
              completedRepetition: payload.completedRepetition,
              effortLevel: payload.effortLevel,
              painLevel: payload.painLevel,
              patientComment: payload.patientComment,
            }
            : exercise
        )
      }
    }))
)
