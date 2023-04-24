import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { LoginRequest } from './model/login.model';
import { LoginService } from './login.service';
import { Store } from '@ngrx/store';
import * as fromApp from '../store/app.reducer';
import * as AuthActions from './store/auth.actions';
import { Observable } from 'rxjs';
import * as LoadingState from '../shared/spinner/store/loading.reducer';
import * as AuthState from './store/auth.reducer';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent implements OnInit {
  resultMsg: string | null;
  type: string;
  isLoading$: Observable<LoadingState.State>;
  authState$: Observable<AuthState.State>;

  constructor(
    private loginService: LoginService,
    private store: Store<fromApp.AppState>
  ) {}

  ngOnInit(): void {
    this.isLoading$ = this.store.select((state) => state.isLoading);
    this.store
      .select((state) => state.auth)
      .subscribe((auth) => {
        if (auth.errorMsg) {
          this.resultMsg = auth.errorMsg;
          this.type = 'alert-danger';
        }
      });
  }

  onHandleAlert() {
    this.resultMsg = null;
  }

  onSubmit(loginForm: NgForm) {
    const loginData: LoginRequest = {
      email: loginForm.value.email,
      password: loginForm.value.password,
    };

    this.store.dispatch(AuthActions.loginStart(loginData));
  }
}
