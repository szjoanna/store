import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { PageCategoryDto } from 'src/api/models';
import { DeleteCategoryAction, LoadCategoryPageAction } from '../state/category.actions';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.sass']
})
export class CategoryListComponent implements OnInit {

  @Select(state => state.category.categoryPage)
  category$: Observable<PageCategoryDto>;

  displayedColumns: string[] = ['id', 'name', 'edit', 'delete'];

  constructor(private store: Store) { }

  ngOnInit(): void {
    this.store.dispatch(new LoadCategoryPageAction(0, 20));
  }

  changePage(event: PageEvent) {
    this.store.dispatch(new LoadCategoryPageAction(event.pageIndex, event.pageSize));
  }

  delete(id: number) {
    this.store.dispatch(new DeleteCategoryAction(id));
  }

}
