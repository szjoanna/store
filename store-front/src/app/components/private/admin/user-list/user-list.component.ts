import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { PageUserDto } from 'src/api/models';
import { DeleteUserAction, LoadUserPageAction } from '../state/user.actions';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.sass']
})
export class UserListComponent implements OnInit {

  @Select(state => state.user.userPage)
  user$: Observable<PageUserDto>;

  displayedColumns: string[] = ['id', 'firstName', 'secondName', 'email', 'edit', 'delete'];

  constructor(private store: Store) { }

  ngOnInit(): void {
    this.store.dispatch(new LoadUserPageAction(0, 20));
  }

  changePage(event: PageEvent) {
    this.store.dispatch(new LoadUserPageAction(event.pageIndex, event.pageSize));
  }

  delete(id: number) {
    this.store.dispatch(new DeleteUserAction(id));
  }

}
