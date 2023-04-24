import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { WorkoutPlanService } from '../workout-plan.service';
import { exhaustMap, map } from 'rxjs/operators';
import { loadEditPlan, startLoadEditPlan } from './plan.actions';

@Injectable({ providedIn: 'root' })
export class PlanEffects {
  getPlanForEdit = createEffect(() =>
    this.actions$.pipe(
      ofType(startLoadEditPlan),
      exhaustMap((payload) =>
        this.planService
          .getPlanWithExercises(payload.planId)
          .pipe(map((workoutPlan) => loadEditPlan(workoutPlan)))
      )
    )
  );

  constructor(
    private actions$: Actions,
    private planService: WorkoutPlanService
  ) {}
}
