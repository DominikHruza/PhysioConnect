import { Component, OnInit } from '@angular/core';
import {PlanService} from "./plan.service";
import {Observable} from "rxjs";
import {WorkoutPlan} from "./plan.model";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {Store} from "@ngrx/store";
import * as fromApp from "../store/app.reducer";
import * as LoadingState from "../shared/spinner/store/loading.reducer";


@Component({
  selector: 'app-plan',
  templateUrl: './plan.component.html',
  styleUrls: ['./plan.component.css']
})
export class PlanComponent implements OnInit {
  allActivePlans$: Observable<WorkoutPlan[]>
  isLoading$: Observable<LoadingState.State>;
  patientId: number

  constructor(private workoutPlanService: PlanService,
              private store: Store<fromApp.AppState>,
              private router: Router) {}

  ngOnInit(): void {
    this.isLoading$ = this.store.select((state) => state.isLoading);
    this.store
      .select(state => state.auth.loggedInUser?.id)
      .subscribe(patientId => this.patientId = patientId!)
    this.allActivePlans$ = this.workoutPlanService.getAllActivePlans(this.patientId);
  }

  handleStartExerciseBtnClick(planId: number) {
    this.router.navigate(['/exercise-session'], {queryParams: {planId}})
  }
}
