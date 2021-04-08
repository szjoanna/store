/* tslint:disable */
import { Role } from './role';
export interface User {
  createdDate?: string;
  email?: string;
  firstName?: string;
  id?: number;
  lastModifiedDate?: string;
  password?: string;
  roles?: Array<Role>;
  secondName?: string;
  version?: number;
}
