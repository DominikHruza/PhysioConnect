import {
  LoginRequest,
  LoginResponse,
  PhysioConnectJwtToken,
} from './model/login.model';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Store } from '@ngrx/store';
import * as fromApp from '../store/app.reducer';
import { catchError, map } from 'rxjs/operators';
import { EMPTY, mergeMap, throwError } from 'rxjs';
import jwtDecode from 'jwt-decode';
import { Injectable } from '@angular/core';
import { login, logout } from './store/auth.actions';

import { User, UserRole } from './model/user.model';
import { Router } from '@angular/router';
import {
  loadingEnd,
  loadingStart,
} from '../shared/spinner/store/loading.actions';

@Injectable({ providedIn: 'root' })
export class LoginService {
  constructor(
    private http: HttpClient,
    private store: Store<fromApp.AppState>,
    private router: Router
  ) {}

  login(loginData: LoginRequest) {
    this.store.dispatch(loadingStart());
    return this.http.post<LoginResponse>('/api/auth/login', loginData).pipe(
      catchError((error) => {
        this.store.dispatch(loadingEnd());
        return this.handleLoginError(error);
      }),
      map((responseBody) => {
        let user = this.handleLoginSuccess(responseBody);
        this.store.dispatch(loadingEnd());
        this.router.navigate(['/dashboard']);
        return user;
      })
    );
  }

  autoLogin(refreshToken: string) {
    let authorizationHeader = new HttpHeaders({
      Authorization: 'Bearer ' + refreshToken,
    });
    return this.http
      .get<LoginResponse>('/api/auth/token/refresh', {
        headers: authorizationHeader,
      })
      .pipe(
        catchError((error) => {
          localStorage.removeItem('refreshToken');
          this.router.navigate(['/login']);
          return EMPTY;
        }),
        mergeMap((responseBody) => {
          let user = this.handleLoginSuccess(responseBody);
          this.store.dispatch(login(user));
          return this.store.select('auth');
        })
      );
  }

  logout() {
    this.store.dispatch(logout());
    localStorage.removeItem('refreshToken');
    this.router.navigate(['/login']);
  }

  private handleLoginSuccess(loginResponse: LoginResponse) {
    let accessToken = loginResponse.accessToken;
    let refreshToken = loginResponse.refreshToken;
    const decodedAccessToken = jwtDecode<PhysioConnectJwtToken>(accessToken);
    const { sub_id, sub, roles, exp } = decodedAccessToken;

    if (!roles.includes(UserRole.PRACTITIONER)) {
      this.handleLoginError(new HttpErrorResponse({ status: 403 }));
    }

    this.startAutoLogoutUserTimer(decodedAccessToken.exp! * 1000 - Date.now());
    const user = new User(sub_id, sub!, roles, exp!, accessToken);
    localStorage.setItem('refreshToken', refreshToken);
    return user;
  }

  private handleLoginError(error: HttpErrorResponse) {
    let errorMessage: string = 'Unknown Error occurred. Contact administrator';
    switch (error.status) {
      case 403:
        errorMessage = 'Invalid Credentials';
        break;
      case 500:
        errorMessage = 'Server Error. Contact administrator';
    }
    return throwError(() => errorMessage);
  }

  startAutoLogoutUserTimer(expirationDuration: number) {
    setTimeout(() => {
      this.store.dispatch(logout());
    }, expirationDuration);
  }
}
