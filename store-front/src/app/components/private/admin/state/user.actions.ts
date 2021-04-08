import { UserDto } from "src/api/models";

export class LoadUserPageAction {
  static readonly type = '[User] LoadUserPageAction';
  constructor(public page: number, public size: number) { }
}

export class LoadUserByIdAction {
  static readonly type = '[User] LoadUserByIdAction';
  constructor(public id: number) { }
}

export class UpdateUserAction {
  static readonly type = '[User] UpdateUserAction';
  constructor(public id: number, public userDto: UserDto) { }
}

export class DeleteUserAction {
  static readonly type = '[User] DeleteUserAction';
  constructor(public id: number) { }
}
