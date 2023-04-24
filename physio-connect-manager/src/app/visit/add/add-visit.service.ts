import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NewVisit } from './visit.model';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AddVisitService {
  constructor(private httpClient: HttpClient) {}

  addVisits(visit: NewVisit) {
    return this.httpClient.post<NewVisit>(`/api/visit`, visit).pipe(
      catchError((errorRes) => {
        return throwError(errorRes);
      })
    );
  }
}
