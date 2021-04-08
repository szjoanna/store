import { Injectable } from '@angular/core';
import { Navigate } from '@ngxs/router-plugin';
import { State, Action, StateContext } from '@ngxs/store';
import { tap } from 'rxjs/operators';
import { CategoryDto, PageCategoryDto } from 'src/api/models';
import { CategoryControllerService } from 'src/api/services';
import { DeleteCategoryAction, LoadCategoriesAction, LoadCategoryByIdAction, LoadCategoryPageAction, SaveCategoryAction, UpdateCategoryAction } from './category.actions';

export class CategoryStateModel {
  public categoryPage: PageCategoryDto;
  public category: CategoryDto;
  public allCategories: CategoryDto[];
}

const defaults = {
  categoryPage: null,
  allCategories: [],
  category: {}
};

@State<CategoryStateModel>({
  name: 'category',
  defaults
})
@Injectable()
export class CategoryState {
  constructor(private categoryService: CategoryControllerService) {
  }
  @Action(LoadCategoryPageAction)
  loadCategories({ patchState }: StateContext<CategoryStateModel>, { page, size }: LoadCategoryPageAction) {
    return this.categoryService.getPageCategoryUsingGET({ page, size }).pipe(
      tap(response => patchState({
        categoryPage: response
      }))
    )
  }
  @Action(LoadCategoryByIdAction)
  loadCategoryById({ patchState }: StateContext<CategoryStateModel>, { id }: LoadCategoryByIdAction) {
    return this.categoryService.findByIdUsingGET(id).pipe(
      tap(response => patchState({
        category: response
      }))
    )
  }
  @Action(DeleteCategoryAction)
  deleteCategoryById({ dispatch }: StateContext<CategoryStateModel>, { id }: DeleteCategoryAction) {
    return this.categoryService.deleteCategoryUsingDELETE(id).pipe(
      tap(response => dispatch(new LoadCategoryPageAction(0, 20)))
    )
  }
  @Action(SaveCategoryAction)
  saveCategoryById({ dispatch }: StateContext<CategoryStateModel>, { categoryDto }: UpdateCategoryAction) {
    return this.categoryService.saveCategoryUsingPOST(categoryDto).pipe(
      tap(
        response => {
          dispatch(new Navigate(["/category/list"]));
          dispatch(new LoadCategoryPageAction(0, 20));
        })
    )
  }
  @Action(UpdateCategoryAction)
  updateProductById({ dispatch }: StateContext<CategoryStateModel>, { id, categoryDto }: UpdateCategoryAction) {
    return this.categoryService.updateCategoryUsingPUT({ id, categoryDto }).pipe(
      tap(
        response => {
          dispatch(new Navigate(["/category/list"]));
          dispatch(new LoadCategoryPageAction(0, 20));
        })
    )
  }
  @Action(LoadCategoriesAction)
  loadAllCategories(ctx: StateContext<CategoryStateModel>) {
    return this.categoryService.getAllCategoriesUsingGET().pipe(
      tap(response => ctx.patchState({ allCategories: response }))
    )
  }
}
