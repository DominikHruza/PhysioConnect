import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Patient } from './patient.model';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs';
import * as fromStore from '../store/app.reducer';
import { Store } from '@ngrx/store';
import {
  loadingEnd,
  loadingStart,
} from '../shared/spinner/store/loading.actions';

@Injectable({ providedIn: 'root' })
export class PatientService {
  constructor(
    private http: HttpClient,
    private store: Store<fromStore.AppState>
  ) {}

  getAll(practitionerId: number) {
    this.store.dispatch(loadingStart());
    return this.http
      .get<Patient[]>(
        `/api/patient/all/next-visit-and-plan-count?practitionerId=${practitionerId}`
      )
      .pipe(
        catchError((error) => {
          this.store.dispatch(loadingEnd());
          return this.handleError(error);
        }),
        map((patients) => {
          this.store.dispatch(loadingEnd());
          return patients;
        })
      );
  }

  getPatientDetails(patientId: number) {
    this.store.dispatch(loadingStart());
    return this.http
      .get<Patient>(
        `/api/patient/${patientId}`
      )
      .pipe(
        catchError((error) => {
          this.store.dispatch(loadingEnd());
          return this.handleError(error);
        }),
        map((patient) => {
          this.store.dispatch(loadingEnd());
          return patient
        })
      );
  }

  addNew(newPatient: Patient) {
    this.store.dispatch(loadingStart());
    return this.http.post<Patient>(`/api/patient`, newPatient).pipe(
      catchError((error) => {
        this.store.dispatch(loadingEnd());
        return this.handleError(error);
      }),
      map((patient) => {
        this.store.dispatch(loadingEnd());
        return patient;
      })
    );
  }

  private handleError(httpError: HttpErrorResponse) {
    let errorMessage = this.createErrorMessage(httpError);
    return throwError(() => errorMessage);
  }

  private createErrorMessage(httpError: HttpErrorResponse) {
    let errorMsg: string;
    switch (httpError.status) {
      case 400:
        errorMsg = httpError.error.message;
        return errorMsg;
      case 500:
        errorMsg = 'Server Error. Contact administrator';
        return errorMsg;
      default:
        errorMsg = 'Something went wrong! Contact Admin';
        return errorMsg;
    }
  }
}
