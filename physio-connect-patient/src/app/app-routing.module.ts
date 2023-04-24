import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./auth/login/login.component";
import {VisitComponent} from "./visit/visit.component";
import {PlanComponent} from "./plan/plan.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {ExerciseComponent} from "./exercise/exercise.component";
import {UserRole} from "./auth/model/user.model";
import {AuthGuard} from "./auth/auth.guard";
import {WorkoutSessionComponent} from "./workout-session/workout-session.component";
import {WorkoutCompleteComponent} from "./workout-complete/workout-complete.component";
import {RegisterComponent} from "./auth/register/register.component";

const routes: Routes = [
  {path: '', redirectTo: 'plan', pathMatch: 'full'},
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'visits',
    component: VisitComponent,
    canActivate: [AuthGuard],
    data: {
      role: UserRole.PATIENT,
    },},

  {
    path:'plan',
    canActivate: [AuthGuard],
    data: {
      role: UserRole.PATIENT,
    },
    children: [
      {path: '', component: PlanComponent},
      //{path: 'exercise-session', component: ExerciseComponent}
    ]},

  {
    path:'exercise-session',
    canActivate: [AuthGuard],
    data: {
      role: UserRole.PATIENT,
    },

    children: [
      {path: '', component: WorkoutSessionComponent},
      {path:'complete', component: WorkoutCompleteComponent}
    ]},

  {path: '404', component: NotFoundComponent},
  {path: '**', redirectTo: '/404'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
