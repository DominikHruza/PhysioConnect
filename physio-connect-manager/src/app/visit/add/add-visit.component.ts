import { Component, OnInit, ViewChild } from '@angular/core';
import { AddVisitService } from './add-visit.service';
import { NgForm } from '@angular/forms';
import { NewVisit } from './visit.model';
import { ActivatedRoute } from '@angular/router';
import { AlertComponent } from '../../shared/alert/alert.component';
import * as fromApp from '../../store/app.reducer';
import { Store } from '@ngrx/store';

@Component({
  selector: 'app-add-visit-modal',
  templateUrl: './add-visit.component.html',
  styleUrls: ['./add-visit.component.css'],
})
export class AddVisitComponent implements OnInit {
  @ViewChild('visitForm', { static: false }) visitForm: NgForm;
  @ViewChild(AlertComponent) alertComponent: AlertComponent;
  resultMsg: string | null;
  type: string;
  patientId: number;
  practitionerId: number;

  constructor(
    private addVisitService: AddVisitService,
    private route: ActivatedRoute,
    private store: Store<fromApp.AppState>
  ) {}

  ngOnInit(): void {
    this.store
      .select((state) => state.auth.loggedInUser)
      .subscribe((practitioner) => {
        this.practitionerId = practitioner!.id;
      });

    this.route.params.subscribe((params) => (this.patientId = params['id']));
  }

  onAddVisit() {
    const visit: NewVisit = {
      practitionerId: 1,
      patientId: this.patientId,
      scheduledFor: this.visitForm.value.scheduledDateTime,
    };

    this.addVisitService.addVisits(visit).subscribe({
      next: (visit) => {
        this.resultMsg = 'New visit scheduled!';
        this.type = 'alert-success';
      },
      error: (err) => {
        this.resultMsg = 'Something went wrong during adding new visit!';
        this.type = 'alert-danger';
      },
    });
  }

  onHandleAlert() {
    this.resultMsg = null;
  }
}
