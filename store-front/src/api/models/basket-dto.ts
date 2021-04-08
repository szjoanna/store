/* tslint:disable */
import { Product } from './product';
import { User } from './user';
export interface BasketDto {
  id?: number;
  product?: Product;
  quantity?: number;
  user?: User;
}
