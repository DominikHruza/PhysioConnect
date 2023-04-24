import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { VisitListComponent } from './visit/list/visit-list.component';
import { PatientDetailsComponent } from './patient/details/patient-details.component';
import { AddPatientComponent } from './patient/add/add-patient.component';
import { AddPlanComponent } from './plan/add/add-plan.component';
import { PlanDetailsComponent } from './plan/details/plan-details.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { AllPatientsComponent } from './patient/all/all-patients.component';
import { AuthComponent } from './auth/auth.component';
import { NavigationComponent } from './navigation/navigation.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AddVisitComponent } from './visit/add/add-visit.component';
import { AlertComponent } from './shared/alert/alert.component';
import { FormsModule } from '@angular/forms';
import { StoreModule } from '@ngrx/store';
import * as fromApp from './store/app.reducer';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { environment } from '../environments/environment';
import { SpinnerComponent } from './shared/spinner/spinner.component';
import { AuthInterceptorService } from './auth/auth.interceptor';
import { AuthEffects } from './auth/store/auth.effects';
import { EffectsModule } from '@ngrx/effects';
import { AddExerciseComponent } from './exercise/add/add-exercise.component';
import { AllPlanComponent } from './plan/all/all-plan.component';
import { AddDocumentComponent } from './document/add/add-document.component';
import { DocumentListComponent } from './document/list/document-list.component';
import { PlanEffects } from './plan/store/plan.effects';
import { VgCoreModule } from '@videogular/ngx-videogular/core';
import { VgControlsModule } from '@videogular/ngx-videogular/controls';
import { EditExerciseComponent } from './exercise/edit/edit-exercise.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { WorkoutSessionListComponent } from './workout-session/list/workout-session-list.component';
import { VideoPlayerComponent } from './video-instruction/video-player/video-player.component';
import { APIInterceptor } from "./shared/interceptor/api.intercepror";

@NgModule({
  declarations: [
    AppComponent,
    VisitListComponent,
    PatientDetailsComponent,
    AddPatientComponent,
    NavigationComponent,
    AddPlanComponent,
    PlanDetailsComponent,
    NotFoundComponent,
    AllPatientsComponent,
    AuthComponent,
    AddVisitComponent,
    AlertComponent,
    SpinnerComponent,
    AddExerciseComponent,
    AllPlanComponent,
    AddDocumentComponent,
    DocumentListComponent,
    EditExerciseComponent,
    WorkoutSessionListComponent,
    VideoPlayerComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    StoreModule.forRoot(fromApp.appReducer),
    EffectsModule.forRoot([AuthEffects, PlanEffects]),
    StoreDevtoolsModule.instrument({ logOnly: environment.production }),
    VgCoreModule,
    VgControlsModule,
    NgbModule,
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
  bootstrap: [AppComponent],
})
export class AppModule {}
