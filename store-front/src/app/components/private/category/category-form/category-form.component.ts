import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { Select, Store } from '@ngxs/store';
import { Subscription } from 'rxjs';
import { Observable } from 'rxjs';
import { CategoryDto } from 'src/api/models';
import { LoadCategoryByIdAction, SaveCategoryAction, UpdateCategoryAction } from '../state/category.actions';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.sass']
})
export class CategoryFormComponent implements OnInit {

  @Select(state => state.category.category)
  category$: Observable<CategoryDto>;

  formGroup: FormGroup = new FormGroup({});
  id: number;
  sub: Subscription;
  fields: FormlyFieldConfig[] = [
    {
      key: "name",
      type: "input",
      templateOptions: {
        required: true,
        label: "Nazwa kategorii",
        placeholder: "Wpisz nazwę kategorii"
      }
    }
  ];

  constructor(private activatedRoute: ActivatedRoute, private store: Store) {
  }

  ngOnInit(): void {
    this.sub = this.activatedRoute.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.store.dispatch(new LoadCategoryByIdAction(this.id));
      }
    })
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  save() {
    if (this.id) {
      this.store.dispatch(new UpdateCategoryAction(this.id, this.formGroup.value));
    } else {
      this.store.dispatch(new SaveCategoryAction(this.formGroup.value));
    }
  }

}
