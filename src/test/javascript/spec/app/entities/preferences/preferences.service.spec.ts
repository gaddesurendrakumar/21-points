import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PreferencesService } from 'app/entities/preferences/preferences.service';
import { IPreferences, Preferences } from 'app/shared/model/preferences.model';
import { Units } from 'app/shared/model/enumerations/units.model';

describe('Service Tests', () => {
  describe('Preferences Service', () => {
    let injector: TestBed;
    let service: PreferencesService;
    let httpMock: HttpTestingController;
    let elemDefault: IPreferences;
    let expectedResult: IPreferences | IPreferences[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PreferencesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Preferences(0, 0, Units.KG);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should return a list of Preferences', () => {
        const returnedFromService = Object.assign(
          {
            weeklyGoal: 1,
            weightUnits: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
