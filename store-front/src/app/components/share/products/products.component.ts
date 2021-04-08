import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Store } from '@ngxs/store';
import { PageProductDto } from 'src/api/models';
import { SecurityComponent } from '../../private/admin/security/security.component';
import { DeleteProductAction } from '../../public/product/state/product.actions';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.sass']
})
export class ProductsComponent extends SecurityComponent implements OnInit {
  @Input()
  products: PageProductDto;

  @Output()
  page = new EventEmitter<PageEvent>();

  displayedColumns: string[] = ['id', 'name', 'description', 'image', 'price', 'details'];

  constructor(private store: Store) {
    super();
  }

  ngOnInit(): void {
    this.currentUser$.subscribe(currentUser => {
      if (this.hasRole(currentUser, ["ROLE_ADMIN"])) {
        this.displayedColumns.push('edit', 'delete');
      }
    });
  }

  changePage(event: PageEvent) {
    this.page.emit(event);
  }

  delete(id: number) {
    this.store.dispatch(new DeleteProductAction(id));
  }
}
