import { Component, OnInit } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Select, Store } from '@ngxs/store';
import { LoginFromCookiesAction, LogoutAction } from '../public/auth/state/auth.actions';
import { UserDto } from 'src/api/models';
import { TranslateService } from '@ngx-translate/core';
import { MatButtonToggleChange } from '@angular/material/button-toggle';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent implements OnInit {

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  @Select(state => state.auth.currentUser)
  currentUser$: Observable<UserDto>;

  constructor(private breakpointObserver: BreakpointObserver, private store: Store, private translateService: TranslateService) { }
  ngOnInit(): void {
    this.store.dispatch(new LoginFromCookiesAction());
    this.translateService.use(this.translateService.getBrowserLang());
  }

  logout() {
    this.store.dispatch(new LogoutAction());
  }

  hasRole(currentUser: UserDto, roles: string[]) {
    return currentUser && currentUser.roles.some(role => roles.some(r => r === role));
  }

  changeLanguage(event: MatButtonToggleChange) {
    this.translateService.use(event.value);
  }

}
