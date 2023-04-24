import {createAction, props} from "@ngrx/store";
import {PlannedExercise} from "../plan.model";

export const LOAD_START = '[PLANNED_EXERCISES] Start Loading'
export const LOAD_PLANNED_EXERCISES = '[PLANNED_EXERCISES] Load'

export const startPlanLoading = createAction(
  LOAD_START,
  props<{planId: number}>()
);

export const loadPlannedExercises = createAction(
  LOAD_PLANNED_EXERCISES,
  props<{exercises: PlannedExercise[]}>()
);
