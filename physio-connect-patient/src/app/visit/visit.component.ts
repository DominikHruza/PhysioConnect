import { Component, OnInit } from '@angular/core';

import {Observable} from "rxjs";
import {VisitService} from "./visit.service";
import {Store} from "@ngrx/store";
import * as fromApp from "../store/app.reducer";
import { Visit } from './visit.model';

@Component({
  selector: 'app-visit',
  templateUrl: './visit.component.html',
  styleUrls: ['./visit.component.css']
})
export class VisitComponent implements OnInit {
  visits$: Observable<Visit[]>;
  patientId: number

  constructor(
    private calendarService: VisitService,
    private store: Store<fromApp.AppState>
  ) {}

  ngOnInit(): void {
    this.store
      .select((state) => state.auth)
      .subscribe((auth) => {
        this.patientId = auth.loggedInUser?.id!
      });
    this.visits$ = this.calendarService.getVisits(this.patientId);
  }

}
