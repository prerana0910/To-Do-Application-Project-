import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorcomponentComponent } from './errorcomponent.component';

describe('ErrorcomponentComponent', () => {
  let component: ErrorcomponentComponent;
  let fixture: ComponentFixture<ErrorcomponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ErrorcomponentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ErrorcomponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
