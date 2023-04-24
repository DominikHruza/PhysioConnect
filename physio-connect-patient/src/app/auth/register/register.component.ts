import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {Observable} from "rxjs";
import * as LoadingState from "../../shared/spinner/store/loading.reducer";
import {RegistrationRequest} from "../model/registration.model";
import {AuthService} from "../auth.service";
import {Store} from "@ngrx/store";
import * as fromApp from '../../store/app.reducer';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  resultMsg: string | null;
  type: string;
  isLoading$: Observable<LoadingState.State>;

  constructor(private authService: AuthService,  private store: Store<fromApp.AppState>) { }

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

  onSubmit(registerForm: NgForm) {
    const request: RegistrationRequest = {
      email: registerForm.value.email,
      password: registerForm.value.password,
      repeatPassword: registerForm.value.confirmPassword,
      registrationCode: registerForm.value.regCode
    }

    this.authService.registerPatient(request).subscribe(
      () => {
        this.resultMsg = "Register successful. Try to login";
        this.type = 'alert-success'
      },
      (error) => {
        this.resultMsg = error;
        this.type = 'alert-danger'
      }
    )
  }

  onHandleAlert() {
    this.resultMsg = null;
  }
}
