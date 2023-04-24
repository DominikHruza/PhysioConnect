import { Injectable } from '@angular/core';
import { mergeMap, Observable, of, take } from 'rxjs';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Store } from '@ngrx/store';
import * as fromApp from '../store/app.reducer';
import { LoginService } from './login.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private store: Store<fromApp.AppState>,
    private loginService: LoginService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    router: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Promise<boolean | UrlTree>
    | Observable<boolean | UrlTree> {
    return this.store.select('auth').pipe(
      take(1),
      mergeMap((auth) => {
        let user = auth.loggedInUser;
        let refreshToken;
        if (!user && (refreshToken = localStorage.getItem('refreshToken'))) {
          return this.loginService.autoLogin(refreshToken);
        }
        return of(auth);
      }),
      mergeMap((auth) => {
        if (
          auth.loggedInUser &&
          auth.loggedInUser.roles.includes(route.data['role'])
        ) {
          return of(true);
        }
        return of(this.router.createUrlTree(['/login']));
      })
    );
  }
}
