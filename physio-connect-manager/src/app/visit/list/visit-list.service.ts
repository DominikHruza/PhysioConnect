import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Visit } from '../../models/Visit';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class VisitListService {
  constructor(private httpClient: HttpClient) {}

  getVisits(practitionerId: number) {
    return this.httpClient
      .get<Visit[]>(`/api/visit/all/practitioner/${practitionerId}`)
      .pipe(
        map((visits) =>
          visits.sort(
            (visit1, visit2) =>
              new Date(visit1.scheduledFor).valueOf() -
              new Date(visit2.scheduledFor).valueOf()
          )
        )
      );
  }
}
