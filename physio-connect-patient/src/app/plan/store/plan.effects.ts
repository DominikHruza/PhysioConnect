import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {mergeMap} from "rxjs";
import {map} from "rxjs/operators";
import {PlanService} from "../plan.service";
import {startPlanLoading, loadPlannedExercises} from "./plan.actions";

@Injectable()
export class WorkoutPlanEffects {
  loadPlannedExercises = createEffect(() =>
    this.actions$.pipe(
      ofType(startPlanLoading),
      mergeMap((payload) =>
        this.workoutPlanService.getWithPlannedExercises(payload.planId).pipe(
          map((plan) => loadPlannedExercises({exercises: plan.exercises})))
      )
    )
  );

  constructor(private actions$: Actions, private workoutPlanService: PlanService) {
  }
}
