import { Component, Input, OnInit } from '@angular/core';
import { DocumentService } from '../document.service';
import { combineLatestWith, EMPTY, Observable, of } from "rxjs";
import { PatientDocument } from './document.model';
import { combineLatest } from "rxjs/internal/operators/combineLatest";
import { map } from "rxjs/operators";

@Component({
  selector: 'app-document-list',
  templateUrl: './document-list.component.html',
  styleUrls: ['./document-list.component.css'],
})
export class DocumentListComponent implements OnInit {
  @Input() patientId: number;
  @Input() documents$: Observable<PatientDocument[]>;

  constructor(private documentService: DocumentService) {}

  ngOnInit(): void {
  }

  onDownloadClick(documentId: number) {
    this.documentService.downloadDocument(documentId).subscribe((response) => {
      let filename = response.headers
        .get('content-disposition')
        ?.split(';')[1]
        .split('=')[1];

      let blob: Blob = response.body as Blob;
      let link = document.createElement('a');
      link.download = filename!;
      link.href = window.URL.createObjectURL(blob);
      link.click();
    });
  }
}
