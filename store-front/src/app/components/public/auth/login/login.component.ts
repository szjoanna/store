import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { LoginAction } from '../state/auth.actions';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {
  formGroup = new FormGroup({});

  @Select(state => state.auth.loginError)
  loginError$: Observable<boolean>;
  
  fields: FormlyFieldConfig[] = [
    {
      key: "email",
      type: "input",
      templateOptions: {
        required: true,
        label: "email",
        placeholder: "Wpisz email"
      }
    },
    {
      key: "password",
      type: "input",
      templateOptions: {
        required: true,
        label: "hasło",
        placeholder: "Wpisz hasło"
      }
    }
  ];

  constructor(private store: Store) { }

  ngOnInit(): void {
  }

  login() {
    this.store.dispatch(new LoginAction(
      this.formGroup.value.email,
      this.formGroup.value.password
    ))
  }

}
