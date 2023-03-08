import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CurrentDetailComponent } from './current-detail.component';

describe('Current Management Detail Component', () => {
  let comp: CurrentDetailComponent;
  let fixture: ComponentFixture<CurrentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CurrentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ current: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CurrentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CurrentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load current on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.current).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
