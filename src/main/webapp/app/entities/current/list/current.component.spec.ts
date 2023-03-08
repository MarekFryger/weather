import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CurrentService } from '../service/current.service';

import { CurrentComponent } from './current.component';

describe('Current Management Component', () => {
  let comp: CurrentComponent;
  let fixture: ComponentFixture<CurrentComponent>;
  let service: CurrentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'current', component: CurrentComponent }]), HttpClientTestingModule],
      declarations: [CurrentComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(CurrentComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CurrentComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CurrentService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.currents?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to currentService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getCurrentIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getCurrentIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
