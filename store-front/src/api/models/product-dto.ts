/* tslint:disable */
export interface ProductDto {
  categoryId?: number;
  deleted?: boolean;
  description?: string;
  id?: number;
  imageUrl?: string;
  main?: boolean;
  name?: string;
  price?: number;
  quantity?: number;
  subProducts?: Array<ProductDto>;
  version?: number;
}
