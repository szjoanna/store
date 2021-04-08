import { Injectable } from '@angular/core';
import { Navigate } from '@ngxs/router-plugin';
import { State, Action, StateContext } from '@ngxs/store';
import { tap } from 'rxjs/operators';
import { PageUserDto, UserDto } from 'src/api/models';
import { UserControllerService } from 'src/api/services';
import { DeleteUserAction, LoadUserByIdAction, LoadUserPageAction, UpdateUserAction } from './user.actions';

export class UserStateModel {
  public userPage: PageUserDto;
  public user: UserDto;
}

const defaults = {
  userPage: null,
  user: {}
};

@State<UserStateModel>({
  name: 'user',
  defaults
})
@Injectable()
export class UserState {
  constructor(private userService: UserControllerService) {
  }
  @Action(LoadUserPageAction)
  loadUsers({ patchState }: StateContext<UserStateModel>, { page, size }: LoadUserPageAction) {
    return this.userService.getPageUserUsingGET({ page, size }).pipe(
      tap(response => patchState({
        userPage: response
      }))
    )
  }
  @Action(LoadUserByIdAction)
  loadUserById({ patchState }: StateContext<UserStateModel>, { id }: LoadUserByIdAction) {
    return this.userService.findUserByIdUsingGET(id).pipe(
      tap(response => patchState({
        user: response
      }))
    )
  }
  @Action(UpdateUserAction)
  updateUser(ctx: StateContext<UserStateModel>, { id, userDto }: UpdateUserAction) {
    return this.userService.updateUserUsingPUT({ id, userDto }).pipe(tap(() => {
      ctx.dispatch(new Navigate(["/user/list"]))
      ctx.patchState({ user: {} })
    }))
  }
  @Action(DeleteUserAction)
  deleteProductById({ dispatch }: StateContext<UserStateModel>, { id }: DeleteUserAction) {
    return this.userService.removeUserByIdUsingDELETE(id).pipe(
      tap(response => dispatch(new LoadUserPageAction(0, 20)))
    )
  }
}
