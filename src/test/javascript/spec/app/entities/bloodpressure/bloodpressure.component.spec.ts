import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { TwentyOnePointsTestModule } from '../../../test.module';
import { BloodpressureComponent } from 'app/entities/bloodpressure/bloodpressure.component';
import { BloodpressureService } from 'app/entities/bloodpressure/bloodpressure.service';
import { Bloodpressure } from 'app/shared/model/bloodpressure.model';

describe('Component Tests', () => {
  describe('Bloodpressure Management Component', () => {
    let comp: BloodpressureComponent;
    let fixture: ComponentFixture<BloodpressureComponent>;
    let service: BloodpressureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TwentyOnePointsTestModule],
        declarations: [BloodpressureComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(BloodpressureComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BloodpressureComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BloodpressureService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Bloodpressure(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bloodpressures && comp.bloodpressures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Bloodpressure(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bloodpressures && comp.bloodpressures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
