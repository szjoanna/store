import { UserDto } from "src/api/models";

export class RegisterAction {
  static readonly type = '[Auth] RegisterAction';
  constructor(public userDto: UserDto) {
  }
}

export class LoginAction {
  static readonly type = '[Auth] LoginAction';
  constructor(public email: string, public password: string) {
  }
}

export class LoginFromCookiesAction {
  static readonly type = '[Auth] LoginFromCookiesAction';
  constructor() {
  }
}

export class LogoutAction {
  static readonly type = '[Auth] LogoutAction';
  constructor() {
  }
}

export class GetCurrentUserAction {
  static readonly type = '[Auth] GetCurrentUserAction';
  constructor() {
  }
}
