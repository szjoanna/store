import { CategoryDto } from "src/api/models";

export class LoadCategoryPageAction {
  static readonly type = '[Category] LoadCategoryPageAction';
  constructor(public page: number, public size: number) { }
}

export class LoadCategoryByIdAction {
  static readonly type = '[Category] LoadCategoryByIdAction';
  constructor(public id: number) { }
}

export class UpdateCategoryAction {
  static readonly type = '[Category] UpdateCategoryAction';
  constructor(public id: number, public categoryDto: CategoryDto) { }
}

export class SaveCategoryAction {
  static readonly type = '[Category] SaveCategoryAction';
  constructor(public categoryDto: CategoryDto) { }
}

export class DeleteCategoryAction {
  static readonly type = '[Category] DeleteCategoryAction';
  constructor(public id: number) { }
}

export class LoadCategoriesAction {
  static readonly type = '[Category] LoadCategoriesAction';
  constructor() { }
}