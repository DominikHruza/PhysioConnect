import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { WorkoutSession } from '../workout-session.model';
import { WorkoutSessionService } from '../workout-session.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-workout-session-list',
  templateUrl: './workout-session-list.component.html',
  styleUrls: ['./workout-session-list.component.css'],
})
export class WorkoutSessionListComponent implements OnInit {
  workoutSessions$: Observable<WorkoutSession[]>;
  private planId: number;

  constructor(
    private workoutSessionService: WorkoutSessionService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.planId = this.route.snapshot.queryParams['planId'];
    this.workoutSessions$ = this.workoutSessionService.getPlanWithExercises(
      this.planId
    );
  }
}
