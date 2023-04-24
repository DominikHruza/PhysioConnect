import {PlannedExercise} from "../plan.model";
import {createReducer, on} from "@ngrx/store";
import {loadPlannedExercises} from "./plan.actions";


export interface State {
  plannedExercises: PlannedExercise[]
}

const initialState: State = {
  plannedExercises: []
}

export const workoutPlanReducer = createReducer(
  initialState,
  on(loadPlannedExercises, (state, payload) => ({...state, plannedExercises: payload.exercises}))
)
