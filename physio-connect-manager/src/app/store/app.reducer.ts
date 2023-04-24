import * as fromAuth from '../auth/store/auth.reducer';
import * as fromLoading from '../shared/spinner/store/loading.reducer';
import { ActionReducerMap } from '@ngrx/store';
import * as fromPlan from '../plan/store/plan.reducer';

export interface AppState {
  auth: fromAuth.State;
  isLoading: fromLoading.State;
  workoutPlan: fromPlan.State;
}

export const appReducer: ActionReducerMap<AppState> = {
  auth: fromAuth.authReducer,
  isLoading: fromLoading.loadingReducer,
  workoutPlan: fromPlan.planReducer
}
