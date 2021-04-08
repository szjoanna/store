export class GetUserOrdersAction {
  static readonly type = '[UserOrder] GetUserOrdersAction';
  constructor() { }
}

export class GetOrderDetailsAction {
  static readonly type = '[UserOrder] GetOrderDetailsAction';
  constructor(public orderNumber: string, public page: number, public size: number) { }
}

export class CreateOrderAction {
  static readonly type = '[UserOrder] CreateOrderAction';
  constructor() { }
}
