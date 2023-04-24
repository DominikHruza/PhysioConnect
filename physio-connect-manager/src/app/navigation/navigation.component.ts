import { Component, OnInit } from '@angular/core';
import * as fromApp from '../store/app.reducer';
import * as fromAuth from '../auth/store/auth.reducer';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { UserRole } from '../auth/model/user.model';
import { logout } from '../auth/store/auth.actions';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
})
export class NavigationComponent implements OnInit {
  auth$: Observable<fromAuth.State>;
  readonly UserRole = UserRole;

  constructor(private store: Store<fromApp.AppState>, private router: Router) {}

  ngOnInit(): void {
    this.auth$ = this.store.select((state) => state.auth);
  }

  onLogoutClick() {
    this.store.dispatch(logout());
    localStorage.removeItem('refreshToken');
    this.router.navigate(['/login']);
  }
}
