import { Component, Input, OnInit } from '@angular/core';
import { ExerciseDto } from '../../plan/workout-plan.model';
import { NgForm } from '@angular/forms';
import { planEditExerciseUpdated } from '../../plan/store/plan.actions';
import { Store } from '@ngrx/store';
import * as fromApp from '../../store/app.reducer';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-edit-exercise',
  templateUrl: './edit-exercise.component.html',
  styleUrls: ['./edit-exercise.component.css'],
})
export class EditExerciseComponent implements OnInit {
  @Input() exerciseNumber: number;
  @Input() exercise: ExerciseDto;
  @Input() exerciseIndex: number;

  constructor(
    private store: Store<fromApp.AppState>,
    public activeModal: NgbActiveModal
  ) {}

  ngOnInit(): void {}

  onAddExerciseFormSubmit(editExerciseForm: NgForm) {
    this.store.dispatch(
      planEditExerciseUpdated({
        id: this.exercise.id!,
        name: editExerciseForm.value.name,
        sets: editExerciseForm.value.setNum,
        reps: editExerciseForm.value.repNum,
        description: editExerciseForm.value.description,
        exerciseIndex: this.exerciseIndex,
      })
    );
    this.activeModal.close();
  }
}
