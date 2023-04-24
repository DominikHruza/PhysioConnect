import { Exercise, WorkoutPlanDto } from '../workout-plan.model';
import { createReducer, on } from '@ngrx/store';
import {
  exerciseAdded,
  exerciseRemoved, exercisesCleared,
  loadEditPlan,
  loadPlans,
  planEditExerciseAdded,
  planEditExerciseRemoved,
  planEditExerciseUpdated
} from "./plan.actions";

export interface State {
  workoutPlanForEdit: WorkoutPlanDto | null;
  exercises: Exercise[];
  workoutPlans: WorkoutPlanDto[];
}

const initialState: State = {
  workoutPlans: [],
  workoutPlanForEdit: null,
  exercises: [],
}

export const planReducer = createReducer(
  initialState,
  on(exerciseAdded, (state, payload) => ({...state, exercises: [...state.exercises, payload]})),

  on(exerciseRemoved, (state, payload) => {
    return {
      ...state,
      exercises: state.exercises.filter(
        (ex, exIdx) => exIdx !== payload.index )
    }
  }),

  on(exercisesCleared, (state) => {
    return {
      ...state,
      exercises: []
    }
  }),

  on(planEditExerciseAdded, (state, payload) => {
    return {
      ...state,
      workoutPlanForEdit: {
        ...state.workoutPlanForEdit!,
        exercises: [
          ...state.workoutPlanForEdit!.exercises,
          {
            exrxName: payload.name,
            exrxSets: payload.sets,
            exrxRepetition:payload.reps,
            videoInstrUrl: payload.file,
            description:payload.description
          }]
      }
    }
  }),

  on(planEditExerciseUpdated, (state, payload) => {
    return {
      ...state,
      workoutPlanForEdit: {
        ...state.workoutPlanForEdit!,
        exercises: state.workoutPlanForEdit!.exercises.map(
          (exercise, index) => index === payload.exerciseIndex ?
            {...exercise,
              exrxName: payload.name,
              exrxSets: payload.sets,
              exrxRepetition: payload.reps,
              description: payload.description
            }
            : exercise
        )
      }
    }
  }),
  on(planEditExerciseRemoved, (state, payload) => {
    return {
      ...state,
      workoutPlanForEdit: {
        ...state.workoutPlanForEdit!,
        exercises:  state.workoutPlanForEdit!.exercises.filter(
          (ex, exIdx) => exIdx !== payload.index )
      }
    }
  }),
  on(loadEditPlan, (state, payload) => ({...state, workoutPlanForEdit: payload})),
  on(loadPlans, (state, payload) => ({...state, workoutPlans: payload.plans}))
)
