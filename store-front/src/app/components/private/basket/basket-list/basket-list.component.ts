import { Component, OnInit } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { BasketDto } from 'src/api/models';
import { CreateOrderAction } from '../../user-order/state/user-order.actions';
import { DeleteProductFromBasketAction, GetBasketByCurrentUserAction } from '../state/basket.actions';

@Component({
  selector: 'app-basket-list',
  templateUrl: './basket-list.component.html',
  styleUrls: ['./basket-list.component.sass']
})
export class BasketListComponent implements OnInit {
  @Select(state => state.basket.baskets)
  basket$: Observable<BasketDto[]>;

  displayedColumns: string[] = ['id', 'quantity', 'product', 'price', 'delete'];

  constructor(private store: Store) { }

  ngOnInit(): void {
    this.store.dispatch(new GetBasketByCurrentUserAction());
  }

  delete(id: number) {
    this.store.dispatch(new DeleteProductFromBasketAction(id));
  }

  createOrder() {
    this.store.dispatch(new CreateOrderAction());
  }
}
