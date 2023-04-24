import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Injectable } from '@angular/core';
import { catchError, exhaustMap, map } from 'rxjs/operators';
import * as AuthActions from './auth.actions';
import { LoginService } from '../login.service';
import { of } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class AuthEffects {
  login = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.loginStart),
      exhaustMap((loginData) =>
        this.loginService.login(loginData).pipe(
          map((loginResponse) => {
            this.router.navigate(['']);
            return AuthActions.login(loginResponse);
          }),
          catchError((error) => of(AuthActions.loginError({ errMsg: error })))
        )
      )
    )
  );

  constructor(
    private actions$: Actions,
    private loginService: LoginService,
    private router: Router
  ) {}
}
