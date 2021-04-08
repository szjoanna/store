/* tslint:disable */
import { Category } from './category';
export interface Product {
  category?: Category;
  classId?: number;
  createdDate?: string;
  deleted?: boolean;
  description?: string;
  factoryId?: number;
  id?: number;
  imageUrl?: string;
  lastModifiedDate?: string;
  listOfProducts?: Array<Product>;
  main?: boolean;
  name?: string;
  price?: number;
  quantity?: number;
  version?: number;
}
