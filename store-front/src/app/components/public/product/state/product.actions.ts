import { ProductDto } from "src/api/models";

export class LoadProductPageAction {
  static readonly type = '[Product] LoadProductPageAction';
  constructor(public page: number, public size: number, public main: boolean) { }
}

export class LoadProductByIdAction {
  static readonly type = '[Product] LoadProductByIdAction';
  constructor(public id: number) { }
}

export class LoadProductsByMainAction {
  static readonly type = '[Product] LoadProductsByMainAction';
  constructor(public main: boolean) { }
}

export class UpdateProductAction {
  static readonly type = '[Product] UpdateProductAction';
  constructor(public id: number, public productDto: ProductDto) { }
}

export class SaveProductAction {
  static readonly type = '[Product] SaveProductAction';
  constructor(public productDto: ProductDto, public blob: Blob) { }
}

export class DeleteProductAction {
  static readonly type = '[Product] DeleteProductAction';
  constructor(public id: number) { }
}

export class AutoCompleteProductsAction {
  static readonly type = '[Product] AutoCompleteProductsAction';
  constructor(public value: string) { }
}








