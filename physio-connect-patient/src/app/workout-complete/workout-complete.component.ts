import { Component, OnInit } from '@angular/core';
import {Store} from "@ngrx/store";
import * as fromApp from "../store/app.reducer";
import {Observable} from "rxjs";
import {WorkoutSessionService} from "../workout-session/workout-session.service";
import {WorkoutSession} from "../workout-session/workout-session.model";
import * as moment from "moment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-workout-complete',
  templateUrl: './workout-complete.component.html',
  styleUrls: ['./workout-complete.component.css']
})
export class WorkoutCompleteComponent implements OnInit {
  planId$: Observable<number>
  currentWorkoutSession: WorkoutSession
  constructor(private store: Store<fromApp.AppState>,
              private workoutSessionService: WorkoutSessionService, private router: Router) { }

  ngOnInit(): void {
    this.planId$ = this.store.select(
      state => state.workoutSession.workoutSession!.planId);
    this.store.select(state => state.workoutSession.workoutSession!)
      .subscribe(workoutSession => {
        this.currentWorkoutSession = workoutSession
      });
  }

  handleFinishBtnClick(){
    this.workoutSessionService.saveWorkoutSession({
      ...this.currentWorkoutSession,
      completedAt:moment().format("yyyy-MM-DDTHH:mm")
    }).subscribe(() => {
      this.router.navigate(['/plan'])
    })
  }
}
