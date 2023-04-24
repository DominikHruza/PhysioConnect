import { Component } from '@angular/core';
import { Visit } from '../../models/Visit';
import { VisitListService } from './visit-list.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-calendar',
  templateUrl: './visit-list.component.html',
  styleUrls: ['./visit-lsit.component.css'],
})
export class VisitListComponent {
  visits$: Observable<Visit[]>;

  constructor(private calendarService: VisitListService) {
    this.visits$ = calendarService.getVisits(1);
  }
}
