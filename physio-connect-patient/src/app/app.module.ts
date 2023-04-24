import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { VisitComponent } from './visit/visit.component';
import { PlanComponent } from './plan/plan.component';
import { NavigationComponent } from './navigation/navigation.component';
import { LoginComponent } from './auth/login/login.component';

import {AppRoutingModule} from "./app-routing.module";
import { ExerciseComponent } from './exercise/exercise.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import {AlertComponent} from "./shared/alert/alert.component";
import {FormsModule} from "@angular/forms";
import {AuthEffects} from "./auth/store/auth.effects";
import {environment} from "../environments/environment";
import * as fromApp from './store/app.reducer';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import {AuthInterceptorService} from "./auth/auth.interceptor";
import { WorkoutSessionComponent } from './workout-session/workout-session.component';
import {WorkoutPlanEffects} from "./plan/store/plan.effects";
import { VideoInstructionComponent } from './video-instruction/video-instruction.component';
import {VgCoreModule} from "@videogular/ngx-videogular/core";
import {VgControlsModule} from "@videogular/ngx-videogular/controls";
import { WorkoutCompleteComponent } from './workout-complete/workout-complete.component';
import { RegisterComponent } from './auth/register/register.component';
import {ValidateEqualModule} from "ng-validate-equal";
import { APIInterceptor } from './shared/interceptor/api.intercepror';

@NgModule({
  declarations: [
    AppComponent,
    VisitComponent,
    PlanComponent,
    NavigationComponent,
    LoginComponent,
    ExerciseComponent,
    AlertComponent,
    WorkoutSessionComponent,
    VideoInstructionComponent,
    WorkoutCompleteComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    StoreModule.forRoot(fromApp.appReducer),
    EffectsModule.forRoot([AuthEffects, WorkoutPlanEffects]),
    StoreDevtoolsModule.instrument({ logOnly: environment.production }),
    VgCoreModule,
    VgControlsModule,
    ValidateEqualModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: APIInterceptor,
      multi: true,
    }
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [AppComponent]
})
export class AppModule { }
