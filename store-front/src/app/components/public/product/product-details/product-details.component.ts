import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { Subscription } from 'rxjs';
import { ProductDto } from 'src/api/models';
import { SecurityComponent } from 'src/app/components/private/admin/security/security.component';
import { AddProductToBasketAction } from 'src/app/components/private/basket/state/basket.actions';
import { LoadProductByIdAction } from '../state/product.actions';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.sass']
})
export class ProductDetailsComponent implements OnInit {

  sub: Subscription;
  id: number;
  formGroup: FormGroup;

  @Select(state => state.product.product)
  product$: Observable<ProductDto>;

  constructor(private activatedRoute: ActivatedRoute, private store: Store, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.sub = this.activatedRoute.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.store.dispatch(new LoadProductByIdAction(this.id));
      }
    });
    this.formGroup = this.formBuilder.group({
      quantity: [null, Validators.required]
    });
  }
  buy(idProduct: number, quantity: number): void {
    this.store.dispatch(new AddProductToBasketAction(idProduct, quantity));
  }

}
