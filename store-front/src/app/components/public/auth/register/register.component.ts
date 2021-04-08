import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { Store } from '@ngxs/store';
import { RegisterAction } from '../state/auth.actions';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass']
})
export class RegisterComponent implements OnInit {

  formGroup = new FormGroup({});
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
      key: "firstName",
      type: "input",
      templateOptions: {
        required: true,
        label: "imię",
        placeholder: "Wpisz imię"
      }
    },
    {
      key: "secondName",
      type: "input",
      templateOptions: {
        required: true,
        label: "nazwisko",
        placeholder: "Wpisz nazwisko"
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

  save() {
    this.store.dispatch(new RegisterAction(
      this.formGroup.value
    ));
  }

}
