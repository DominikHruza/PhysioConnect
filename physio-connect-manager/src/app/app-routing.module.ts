import { RouterModule, Routes } from '@angular/router';
import { VisitListComponent } from './visit/list/visit-list.component';
import { AddPatientComponent } from './patient/add/add-patient.component';
import { PatientDetailsComponent } from './patient/details/patient-details.component';
import { AddPlanComponent } from './plan/add/add-plan.component';
import { PlanDetailsComponent } from './plan/details/plan-details.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { AllPatientsComponent } from './patient/all/all-patients.component';
import { AuthComponent } from './auth/auth.component';
import { NgModule } from '@angular/core';
import { AuthGuard } from './auth/auth.guard';
import { UserRole } from './auth/model/user.model';
import { WorkoutSessionListComponent } from './workout-session/list/workout-session-list.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  { path: 'login', component: AuthComponent },
  {
    path: 'dashboard',
    component: VisitListComponent,
    canActivate: [AuthGuard],
    data: {
      role: UserRole.PRACTITIONER,
    },
  },

  {
    path: 'patient',
    children: [
      { path: 'all', component: AllPatientsComponent },
      { path: 'add', component: AddPatientComponent },
      { path: ':id/details', component: PatientDetailsComponent },
    ],
    canActivate: [AuthGuard],
    data: {
      role: UserRole.PRACTITIONER,
    },
  },

  {
    path: 'plan',
    children: [
      { path: 'add', component: AddPlanComponent },
      { path: ':id/details', component: PlanDetailsComponent },
    ],
    canActivate: [AuthGuard],
    data: {
      role: UserRole.PRACTITIONER,
    },
  },

  {
    path: 'completed-session',
    children: [{ path: '', component: WorkoutSessionListComponent }],
    canActivate: [AuthGuard],
    data: {
      role: UserRole.PRACTITIONER,
    },
  },

  { path: '404', component: NotFoundComponent },

  { path: '**', redirectTo: '/404' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
