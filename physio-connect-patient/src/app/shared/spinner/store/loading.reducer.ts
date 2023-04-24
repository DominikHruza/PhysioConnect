import { createReducer, on } from '@ngrx/store';
import * as LoadingActions from './loading.actions';

export interface State {
  isLoading: boolean;
}

const initialState: State = {
  isLoading: false
}

export const loadingReducer = createReducer(
  initialState,
  on(LoadingActions.loadingStart, (state) => ({ ...state, isLoading: true })),
  on(LoadingActions.loadingEnd, (state) => ({...state, isLoading: false }))
);
