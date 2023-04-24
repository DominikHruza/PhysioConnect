import * as fromAuth from '../auth/store/auth.reducer';
import * as fromLoading from '../shared/spinner/store/loading.reducer';
import * as fromWorkoutPlan from '../plan/store/plan.reducer';
import * as fromWorkoutSession from '../workout-session/store/workout-session.reducer'
import { ActionReducerMap } from '@ngrx/store';


export interface AppState {
  auth: fromAuth.State;
  isLoading: fromLoading.State;
  workoutPlan: fromWorkoutPlan.State,
  workoutSession: fromWorkoutSession.State
}

export const appReducer: ActionReducerMap<AppState> = {
  auth: fromAuth.authReducer,
  isLoading: fromLoading.loadingReducer,
  workoutPlan: fromWorkoutPlan.workoutPlanReducer,
  workoutSession: fromWorkoutSession.workoutSessionReducer
}
