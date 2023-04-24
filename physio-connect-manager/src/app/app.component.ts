import { Component, OnDestroy, OnInit } from '@angular/core';
import { LoginService } from './auth/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit, OnDestroy {
  constructor(private loginService: LoginService) {}

  ngOnInit(): void {
    //this.loginService.autoLogin();
  }

  ngOnDestroy(): void {}
}
