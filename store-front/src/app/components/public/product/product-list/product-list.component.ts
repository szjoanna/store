import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Select, Store } from '@ngxs/store';
import { Observable, Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { PageProductDto } from 'src/api/models';
import { SecurityComponent } from 'src/app/components/private/admin/security/security.component';
import { AutoCompleteProductsAction, DeleteProductAction, LoadProductPageAction } from '../state/product.actions';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.sass']
})
export class ProductListComponent extends SecurityComponent implements OnInit {

  @Select(state => state.product.productPage)
  product$: Observable<PageProductDto>;

  @Select(state => state.product.autoComplete)
  autoComplete$: Observable<string[]>;

  subjectAutoComplete: Subject<string> = new Subject();

  displayedColumns: string[] = ['id', 'name', 'description', 'price', 'details'];

  constructor(private store: Store) {
    super();
  }

  ngOnInit(): void {
    this.store.dispatch(new LoadProductPageAction(0, 20, true));
    this.subjectAutoComplete.pipe(debounceTime(500)).subscribe(valueInput => {
      if (valueInput !== "") {
        this.store.dispatch(new AutoCompleteProductsAction(valueInput));
      }
    })
  }

  changePage(event: PageEvent) {
    this.store.dispatch(new LoadProductPageAction(event.pageIndex, event.pageSize, true));
  }

  delete(id: number) {
    this.store.dispatch(new DeleteProductAction(id));
  }

}
