import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { UserDto } from 'src/api/models';

export class SecurityComponent {

  @Select(state => state.auth.currentUser)
  currentUser$: Observable<UserDto>;

  hasRole(currentUser: UserDto, roles: string[]) {
    return currentUser && currentUser.roles.some(role => roles.some(r => r === role));
  }

}
