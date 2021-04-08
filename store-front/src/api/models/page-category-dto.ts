/* tslint:disable */
import { CategoryDto } from './category-dto';
import { Pageable } from './pageable';
import { Sort } from './sort';
export interface PageCategoryDto {
  content?: Array<CategoryDto>;
  empty?: boolean;
  first?: boolean;
  last?: boolean;
  number?: number;
  numberOfElements?: number;
  pageable?: Pageable;
  size?: number;
  sort?: Sort;
  totalElements?: number;
  totalPages?: number;
}
