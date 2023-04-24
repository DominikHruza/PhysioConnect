import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class VideoInstructionService {
  constructor(private http: HttpClient) {}

  uploadVideoInstruction(videoFile: File) {
    const formData = new FormData();
    formData.append('videoInstructions', videoFile);

    return this.http
      .post<{ videoUrl: string }>('/api/video-instructions/upload', formData)
      .pipe(
        catchError(this.handleVideoUploadError),
        map((videoUrl) => videoUrl)
      );
  }

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

  private handleVideoUploadError(httpError: HttpErrorResponse) {
    return of({});
  }
}

