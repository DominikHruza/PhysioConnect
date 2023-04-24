import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Store} from "@ngrx/store";
import * as fromApp from "../store/app.reducer";
import {Router} from "@angular/router";
import {WorkoutPlan} from "./plan.model";
import {catchError, map} from "rxjs/operators";
import {loadingEnd, loadingStart} from "../shared/spinner/store/loading.actions";

@Injectable({providedIn: "root"})
export class PlanService {
  constructor(
    private http: HttpClient,
    private store: Store<fromApp.AppState>
  ) {}

  getAllActivePlans(patientId: number){
    this.store.dispatch(loadingStart());
    return this.http
      .get<WorkoutPlan[]>(`/api/workout-plan/active/${patientId}`).pipe(
        map((plan) => {
          this.store.dispatch(loadingEnd());
          return plan;
        })
      );
  }

  getWithPlannedExercises(planId: number){
    this.store.dispatch(loadingStart());
    return this.http.get<WorkoutPlan>(`/api/workout-plan/exercises/${planId}`).pipe(
      map((plan) => {
        this.store.dispatch(loadingEnd());
        return plan;
      })
    );
  }
}
