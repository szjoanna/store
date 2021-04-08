import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrederDetailsComponent } from './oreder-details.component';

describe('OrederDetailsComponent', () => {
  let component: OrederDetailsComponent;
  let fixture: ComponentFixture<OrederDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrederDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrederDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
