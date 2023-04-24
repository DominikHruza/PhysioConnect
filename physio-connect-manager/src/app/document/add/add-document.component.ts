import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { NgForm } from '@angular/forms';
import { DocumentService } from '../document.service';
import { Observable, Subscription } from 'rxjs';
import * as fromApp from '../../store/app.reducer';
import { Store } from '@ngrx/store';
import {
  loadingEnd,
  loadingStart,
} from '../../shared/spinner/store/loading.actions';
import * as LoadingState from '../../shared/spinner/store/loading.reducer';

@Component({
  selector: 'app-add-document',
  templateUrl: './add-document.component.html',
  styleUrls: ['./add-document.component.css'],
})
export class AddDocumentComponent implements OnInit, OnDestroy {
  @Output() documentAdded = new EventEmitter<void>();
  subscriptions: Subscription[] = [];
  isLoading$: Observable<LoadingState.State>;
  file: File;
  @Input() patientId: number;
  resultMsg: string | null;
  type: any;

  constructor(
    private documentService: DocumentService,
    private store: Store<fromApp.AppState>
  ) {}

  ngOnInit(): void {
    this.isLoading$ = this.store.select((state) => state.isLoading);
  }

  onAddDocumentFormSubmit(addDocumentForm: NgForm) {
    this.store.dispatch(loadingStart());
    let subscription = this.documentService
      .addDocument(this.file, this.patientId)
      .subscribe((result) => {
        this.resultMsg = 'Document saved!';
        this.type = 'alert-success';
        this.store.dispatch(loadingEnd());
      });
    this.subscriptions.push(subscription);
    this.documentAdded.emit();
  }

  // @ts-ignore
  onFileSelected(event) {
    this.file = event.target.files[0];
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => {
      sub.unsubscribe();
    });
  }

  onHandleAlert() {
    this.resultMsg = null;
  }
}
