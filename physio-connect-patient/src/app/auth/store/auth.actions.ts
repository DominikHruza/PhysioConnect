import { createAction, props } from '@ngrx/store';
import { User } from '../model/user.model';
import { LoginRequest } from '../model/login.model';

export const LOGIN_START = '[AUTH] Login Start';
export const LOGIN_ERROR = '[AUTH] Login Error';
export const AUTO_LOGIN_START = '[AUTH] Auto Login Start';
export const LOGIN_SUCCESS = '[AUTH] Login Success';
export const LOGOUT = "[AUTH] Logout"

export const loginStart = createAction(
  LOGIN_START,
  props<LoginRequest>()
);

export const login = createAction(
  LOGIN_SUCCESS,
  props<User>()
);

export const loginError = createAction(
  LOGIN_ERROR,
  props<{errMsg: string}>()
);

export const autoLoginStart = createAction(
  AUTO_LOGIN_START,
  props<{refreshToken: string}>()
);


export const logout = createAction(
  LOGOUT,
)



