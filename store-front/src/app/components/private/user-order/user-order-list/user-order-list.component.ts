import { Component, OnInit } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { Order } from 'src/api/models';
import { GetUserOrdersAction } from '../state/user-order.actions';

@Component({
  selector: 'app-user-order-list',
  templateUrl: './user-order-list.component.html',
  styleUrls: ['./user-order-list.component.sass']
})
export class UserOrderListComponent implements OnInit {
  @Select(state => state.userOrder.orders)
  orders$: Observable<Order[]>;

  displayedColumns: string[] = ['orderNumber', 'createdDate', 'totalPrice'];

  constructor(private store: Store) { }

  ngOnInit(): void {
    this.store.dispatch(new GetUserOrdersAction());
  }
}
