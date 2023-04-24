import { Component, Input, OnInit } from '@angular/core';
import { WorkoutPlanDto } from '../workout-plan.model';

@Component({
  selector: 'app-all-plan',
  templateUrl: './all-plan.component.html',
  styleUrls: ['./all-plan.component.css'],
})
export class AllPlanComponent implements OnInit {
  @Input() workoutPlans: WorkoutPlanDto[] = [];

  constructor() {}

  ngOnInit(): void {}
}
