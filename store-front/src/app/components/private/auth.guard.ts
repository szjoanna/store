import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AuthState } from '../public/auth/state/auth.state';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private store: Store, private router: Router) {

  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const token = this.store.selectSnapshot(AuthState.token);
    if (token) {
      const roles: string[] = route.data.roles;
      if (roles) {
        const currentUser = this.store.selectSnapshot(AuthState.currentUser);
        console.log(currentUser);
        return currentUser.roles.some(role => roles.some(r => r === role));
      }
      return true;
    }
    this.router.navigate(["/auth/login"])
    return false;
  }
}
