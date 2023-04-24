import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  loadingEnd,
  loadingStart,
} from '../shared/spinner/store/loading.actions';
import { map } from 'rxjs/operators';
import { Store } from '@ngrx/store';
import * as fromApp from '../store/app.reducer';
import { WorkoutSession } from './workout-session.model';

@Injectable({ providedIn: 'root' })
export class WorkoutSessionService {
  constructor(
    private httpClient: HttpClient,
    private store: Store<fromApp.AppState>
  ) {}

  getPlanWithExercises(planId: number) {
    this.store.dispatch(loadingStart());
    return this.httpClient
      .get<WorkoutSession[]>(`/api/exercise-session/all/plan/${planId}`)
      .pipe(
        map((response) => {
          this.store.dispatch(loadingEnd());
          return response
            .sort(
              (session1, session2) =>
                new Date(session1.completedAt).valueOf() -
                new Date(session2.completedAt).valueOf()
            )
            .reverse();
        })
      );
  }
}
