import { createAction, props } from '@ngrx/store';
import { Exercise, WorkoutPlanDto } from '../workout-plan.model';

export const EXERCISE_ADDED = '[Plan] Exercise Added';
export const EXERCISE_REMOVED = "[Plan] Exercise Removed"
export  const CLEAR_EXERCISES = "[Plan] Exercises Cleared"
export const PLAN_EDIT_EXERCISE_UPDATED = "[Plan Edit] Exercise Updated"
export const PLAN_EDIT_EXERCISE_ADDED = "[Plan Edit] Exercise Added"
export const PLAN_EDIT_EXERCISE_REMOVED = "[Plan Edit] Exercise Removed"
export const START_EDIT_PLAN_LOAD = '[Plan] Start Load'
export const LOAD_EDIT_PLAN = "[Plan] Load Edit Plan"
export const LOAD_PLANS = "[Plan] Load Plans"

export const exerciseAdded = createAction(
  EXERCISE_ADDED,
  props<Exercise>()
)

export const exerciseRemoved = createAction(
  EXERCISE_REMOVED,
  props<{index: number}>()
)

export const exercisesCleared = createAction(
  CLEAR_EXERCISES
)
export const planEditExerciseAdded = createAction(
  PLAN_EDIT_EXERCISE_ADDED,
  props<Exercise>()
)

export const planEditExerciseUpdated = createAction(
  PLAN_EDIT_EXERCISE_UPDATED,
  props<{id: number | undefined, name: string, sets: number, reps: number, description:string,  exerciseIndex: number}>()
)

export const planEditExerciseRemoved = createAction(
  PLAN_EDIT_EXERCISE_REMOVED,
  props<{index: number}>()
)

export const startLoadEditPlan = createAction(
  START_EDIT_PLAN_LOAD,
  props<{planId: number}>()
)

export const loadEditPlan = createAction(
  LOAD_EDIT_PLAN,
  props<WorkoutPlanDto>()
)

export const loadPlans= createAction(
  LOAD_PLANS,
  props<{plans: WorkoutPlanDto[]}>()
)
