import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Navigate } from '@ngxs/router-plugin';
import { State, Action, StateContext, Selector } from '@ngxs/store';
import { EMPTY } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { UserDto } from 'src/api/models';
import { UserControllerService } from 'src/api/services';
import { environment } from 'src/environments/environment';
import { GetCurrentUserAction, LoginAction, LogoutAction, RegisterAction, LoginFromCookiesAction } from './auth.actions';
import Cookies from 'js-cookie';

export class AuthStateModel {
  public token: string;
  public loginError: boolean;
  public currentUser: UserDto;
}

const defaults = {
  token: null,
  loginError: false,
  currentUser: null
};

@State<AuthStateModel>({
  name: 'auth',
  defaults
})

@Injectable()
export class AuthState {
  constructor(private userControllerService: UserControllerService, private httpClient: HttpClient) { }

  @Selector()
  static token(authStateModel: AuthStateModel) {
    return authStateModel.token;
  }

  @Selector()
  static currentUser(authStateModel: AuthStateModel) {
    return authStateModel.currentUser;
  }

  @Action(RegisterAction)
  register({ getState, setState }: StateContext<AuthStateModel>, { userDto }: RegisterAction) {
    return this.userControllerService.saveUserUsingPOST(userDto);
  }

  @Action(LoginAction)
  login({ patchState, dispatch }: StateContext<AuthStateModel>, { email, password }: LoginAction) {
    const formData = new FormData();
    formData.append("email", email);
    formData.append("password", password);
    return this.httpClient.post<{ token }>(`${environment.url}/login`, formData).pipe(
      tap(({ token }) => {
        patchState({ token, loginError: false });
        dispatch(new Navigate(["/product/list"]));
        dispatch(new GetCurrentUserAction());
        Cookies.set("token", token, { expires: 1 });
      }),
      catchError(({ err, caught }) => {
        console.log("błąd logowania");
        patchState({ loginError: true });
        return EMPTY;
      })
    )
  }

  @Action(LoginFromCookiesAction)
  loginFromCookies({ patchState, dispatch }: StateContext<AuthStateModel>) {
    const token = Cookies.get("token");
    if (token) {
      patchState({ token });
      dispatch(new GetCurrentUserAction());
    }
  }

  @Action(LogoutAction)
  logout({ patchState, dispatch }: StateContext<AuthStateModel>, { }: LogoutAction) {
    patchState({ token: null });
    dispatch(new Navigate(["/auth/login"]));
    patchState({ currentUser: null });
    Cookies.remove("token");
  }

  @Action(GetCurrentUserAction)
  getCurrentUser(ctx: StateContext<AuthStateModel>, { }: GetCurrentUserAction) {
    return this.userControllerService.getCurrentUserUsingGET().pipe(
      tap(response => ctx.patchState({ currentUser: response }))
    )
  }
}
