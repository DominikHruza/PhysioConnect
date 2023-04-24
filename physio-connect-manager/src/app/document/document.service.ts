import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, scan, tap } from "rxjs/operators";
import { PatientDocument } from './list/document.model';
import { combineLatest, EMPTY, merge, Subject } from "rxjs";


@Injectable({ providedIn: 'root' })
export class DocumentService {
  private documentAddedSubject = new Subject<PatientDocument>()
  documentInsertedAction$ = this.documentAddedSubject.asObservable();
  constructor(private http: HttpClient) {}

  getDocuments(patientId: number) {
    return this.http
      .get<PatientDocument[]>(`/api/document/all/${patientId}`)
      .pipe(map((response) => response));
  }

  addDocument(file: File, patientId: number) {
    const formData = new FormData();
    formData.append('document', file);
    return this.http
      .post<PatientDocument>(`/api/document/upload/${patientId}`, formData)
      .pipe(
        map((response) => {
          this.documentAddedSubject.next(response)
          return response
        })
      );
  }

  downloadDocument(documentId: number) {
    return this.http.get(`/api/document/download/${documentId}`, {
      observe: 'response',
      responseType: 'blob',
    });
  }

  getDocumentsWithAddTracking(patientId: number){
    return merge(
      this.getDocuments(patientId),
      this.documentInsertedAction$
    ).pipe(
      scan((acc, value) => {
        console.log(value)
        return (value instanceof Array) ? [...value] : [...acc, value]
      }, [] as PatientDocument[]),
    )
  }
}
