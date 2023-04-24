import { User } from '../model/user.model';
import { createReducer, on } from '@ngrx/store';
import * as AuthActions from './auth.actions';

export interface State {
  loggedInUser: User | null;
  errorMsg: string | null;
}

const initialState: State = {
  loggedInUser: null,
  errorMsg: null
}

export const authReducer = createReducer(
  initialState,
  on(AuthActions.login, (state, user) => ({ ...state, loggedInUser: user })),
  on(AuthActions.logout, (state) => ({...state, loggedInUser: null})),
  on(AuthActions.loginError, (state, payload) => ({...state, errorMsg: payload.errMsg})),
);
