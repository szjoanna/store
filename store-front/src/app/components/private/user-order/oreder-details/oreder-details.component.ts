import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { ActivatedRoute } from '@angular/router';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { Subscription } from 'rxjs';
import { Order, ProductDto } from 'src/api/models';
import { GetOrderDetailsAction } from '../state/user-order.actions';

@Component({
  selector: 'app-oreder-details',
  templateUrl: './oreder-details.component.html',
  styleUrls: ['./oreder-details.component.sass']
})
export class OrederDetailsComponent implements OnInit {

  @Select(state => state.userOrder.products)
  products$: Observable<ProductDto[]>;

  orderNumber: string;
  sub: Subscription

  displayedColumns: string[] = ['id', 'name', 'description', 'price', 'quantity'];

  constructor(private activatedRoute: ActivatedRoute, private store: Store) { }

  ngOnInit(): void {
    this.sub = this.activatedRoute.params.subscribe(params => {
      this.orderNumber = params['orderNumber'];
      if (this.orderNumber) {
        this.store.dispatch(new GetOrderDetailsAction(this.orderNumber, 0, 10));
      }
    })
  }

  changePage(event: PageEvent) {
    this.store.dispatch(new GetOrderDetailsAction(this.orderNumber, event.pageIndex, event.pageSize));
  }

}
