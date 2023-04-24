import { Component, OnInit } from '@angular/core';
import {Store} from "@ngrx/store";
import * as fromApp from "../store/app.reducer";
import {Observable} from "rxjs";
import * as LoadingState from "../shared/spinner/store/loading.reducer";

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.css']
})
export class ExerciseComponent implements OnInit {
  isLoading$: Observable<LoadingState.State>;

  constructor(private store: Store<fromApp.AppState>) { }

  ngOnInit(): void {
    this.isLoading$ = this.store.select((state) => state.isLoading);
  }

}
