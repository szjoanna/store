import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { Subscription } from 'rxjs';
import { CategoryDto, ProductDto } from 'src/api/models';
import { LoadCategoriesAction } from 'src/app/components/private/category/state/category.actions';
import { LoadProductByIdAction, LoadProductsByMainAction, SaveProductAction, UpdateProductAction } from '../state/product.actions';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.sass']
})
export class ProductFormComponent implements OnInit, OnDestroy {

  @Select(state => state.category.allCategories)
  allCategories$: Observable<CategoryDto[]>;

  @Select(state => state.product.products)
  products$: Observable<ProductDto[]>;

  formGroup: FormGroup = new FormGroup({});
  id: number;
  sub: Subscription;
  fields: FormlyFieldConfig[] = [
    {
      key: "name",
      type: "input",
      templateOptions: {
        required: true,
        label: "Nazwa produktu",
        placeholder: "Wpisz nazwę produktu"
      }
    },
    {
      key: "description",
      type: "input",
      templateOptions: {
        required: true,
        label: "Opis produktu",
        placeholder: "Wpisz opis produktu"
      }
    },
    {
      key: "price",
      type: "input",
      templateOptions: {
        required: true,
        label: "Cena",
        placeholder: "Wpisz cenę"
      }
    },
    {
      key: "quantity",
      type: "input",
      templateOptions: {
        required: true,
        label: "Ilość",
        placeholder: "Wpisz ilość"
      }
    },
    {
      key: "file",
      type: "file",
      templateOptions: {
        required: true
      }
    },
    {
      key: "isMain",
      type: "checkbox",
      templateOptions: {
        required: true,
        label: "Produkt główny"
      }
    },
    {
      key: 'categoryId',
      type: 'select',
      templateOptions: {
        label: 'Kategoria',
        placeholder: 'Kategoria',
        required: true,
      },
    },
    {
      key: 'subProducts',
      type: 'select',
      templateOptions: {
        label: 'Podprodukty',
        placeholder: 'Podprodukty',
        multiple: true
      },
    }
  ];

  constructor(private activatedRoute: ActivatedRoute, private store: Store) { }

  ngOnInit(): void {
    this.store.dispatch(new LoadCategoriesAction());
    this.store.dispatch(new LoadProductsByMainAction(false));
    this.sub = this.activatedRoute.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.store.dispatch(new LoadProductByIdAction(this.id));
      }
    })
    this.allCategories$.subscribe(categories => {
      if (categories) {
        this.fields = this.fields.map(field => {
          if (field.key === "categoryId") {
            field.templateOptions.options = categories.map(category => {
              return {
                label: category.name,
                value: category.id
              }
            })
          }
          return field;
        })
      }
    })
    this.products$.subscribe(products => {
      if (products) {
        this.fields = this.fields.map(field => {
          if (field.key === "subProducts") {
            field.templateOptions.options = products.map(product => {
              return {
                label: product.name,
                value: product.id
              }
            })
          }
          return field;
        })
      }
    })
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  save() {
    if (this.id) {
      this.store.dispatch(new UpdateProductAction(this.id, this.formGroup.value));
    } else {
      this.store.dispatch(new SaveProductAction(this.formGroup.value, this.formGroup.value.file[0]));
    }
  }
}
