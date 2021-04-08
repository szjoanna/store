import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { UserDto } from 'src/api/models';
import { LoadUserByIdAction, UpdateUserAction } from '../state/user.actions';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.sass']
})
export class UserFormComponent implements OnInit {

  @Select(state => state.user.user)
  user$: Observable<UserDto>;

  formGroup: FormGroup = new FormGroup({});
  id: number;
  fields: FormlyFieldConfig[] = [
    {
      key: "firstName",
      type: "input",
      templateOptions: {
        required: true,
        label: "Imię",
        placeholder: "Wpisz imię"
      }
    },
    {
      key: "secondName",
      type: "input",
      templateOptions: {
        required: true,
        label: "Nazwisko",
        placeholder: "Wpisz nazwisko"
      }
    },
    {
      key: "email",
      type: "input",
      templateOptions: {
        required: true,
        label: "Email",
        placeholder: "Wpisz email"
      }
    }
  ];

  constructor(private activatedRoute: ActivatedRoute, private store: Store) {
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.store.dispatch(new LoadUserByIdAction(this.id));
      }
    })
  }

  edit() {
    this.store.dispatch(new UpdateUserAction(this.id, this.formGroup.value));
  }
}
