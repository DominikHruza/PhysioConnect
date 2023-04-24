import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Store } from '@ngrx/store';
import * as fromApp from '../../store/app.reducer';

@Component({
  selector: 'app-add-exercise',
  templateUrl: './add-exercise.component.html',
  styleUrls: ['./add-exercise.component.css'],
})
export class AddExerciseComponent implements OnInit {
  @Output() onSubmit: EventEmitter<{
    addExerciseForm: NgForm;
    file: File | null;
  }> = new EventEmitter();
  @Input() exerciseNumber: number;
  fileName = '';
  file: File | null;

  constructor(private store: Store<fromApp.AppState>) {}

  ngOnInit(): void {}

  onAddExerciseFormSubmit(addExerciseForm: NgForm) {
    this.onSubmit.emit({ addExerciseForm, file: this.file });
  }

  // @ts-ignore
  onFileSelected(event) {
    this.file = event.target.files[0];
  }
}
