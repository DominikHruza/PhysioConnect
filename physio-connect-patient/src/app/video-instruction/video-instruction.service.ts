import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class VideoInstructionService {
  constructor(private http: HttpClient) {}

  downloadVideoInstruction(fileName: string) {
    return this.http
      .get(`/api/video-instructions/${fileName}`, {
        observe: 'response',
        responseType: 'blob',
      })
      .pipe(
        map((response) => {
          return response.body;
        })
      );
  }
}

