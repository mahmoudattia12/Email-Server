import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InnerFolderComponent } from './inner-folder.component';

describe('InnerFolderComponent', () => {
  let component: InnerFolderComponent;
  let fixture: ComponentFixture<InnerFolderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InnerFolderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InnerFolderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
