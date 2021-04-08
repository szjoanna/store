export class GetBasketByCurrentUserAction {
  static readonly type = '[Basket] GetBasketByCurrentUserAction';
  constructor() { }
}

export class AddProductToBasketAction {
  static readonly type = '[Basket] AddProductToBasketAction';
  constructor(public idProduct: number, public quantity: number) { }
}

export class DeleteProductFromBasketAction {
  static readonly type = '[Basket] DeleteProductFromBasketAction';
  constructor(public id: number) { }
}

export class DeleteAllCurrentUserProductsAction {
  static readonly type = '[Basket] DeleteAllCurrentUserProductsAction';
  constructor() { }
}

export class UpdateProductFromBasketAction {
  static readonly type = '[Basket] UpdateProductFromBasketAction';
  constructor(public quantity: number, public id: number) { }
}