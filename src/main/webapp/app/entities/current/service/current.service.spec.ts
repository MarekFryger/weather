import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICurrent } from '../current.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../current.test-samples';

import { CurrentService } from './current.service';

const requireRestSample: ICurrent = {
  ...sampleWithRequiredData,
};

describe('Current Service', () => {
  let service: CurrentService;
  let httpMock: HttpTestingController;
  let expectedResult: ICurrent | ICurrent[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CurrentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Current', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const current = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(current).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Current', () => {
      const current = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(current).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Current', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Current', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Current', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCurrentToCollectionIfMissing', () => {
      it('should add a Current to an empty array', () => {
        const current: ICurrent = sampleWithRequiredData;
        expectedResult = service.addCurrentToCollectionIfMissing([], current);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(current);
      });

      it('should not add a Current to an array that contains it', () => {
        const current: ICurrent = sampleWithRequiredData;
        const currentCollection: ICurrent[] = [
          {
            ...current,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCurrentToCollectionIfMissing(currentCollection, current);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Current to an array that doesn't contain it", () => {
        const current: ICurrent = sampleWithRequiredData;
        const currentCollection: ICurrent[] = [sampleWithPartialData];
        expectedResult = service.addCurrentToCollectionIfMissing(currentCollection, current);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(current);
      });

      it('should add only unique Current to an array', () => {
        const currentArray: ICurrent[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const currentCollection: ICurrent[] = [sampleWithRequiredData];
        expectedResult = service.addCurrentToCollectionIfMissing(currentCollection, ...currentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const current: ICurrent = sampleWithRequiredData;
        const current2: ICurrent = sampleWithPartialData;
        expectedResult = service.addCurrentToCollectionIfMissing([], current, current2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(current);
        expect(expectedResult).toContain(current2);
      });

      it('should accept null and undefined values', () => {
        const current: ICurrent = sampleWithRequiredData;
        expectedResult = service.addCurrentToCollectionIfMissing([], null, current, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(current);
      });

      it('should return initial array if no Current is added', () => {
        const currentCollection: ICurrent[] = [sampleWithRequiredData];
        expectedResult = service.addCurrentToCollectionIfMissing(currentCollection, undefined, null);
        expect(expectedResult).toEqual(currentCollection);
      });
    });

    describe('compareCurrent', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCurrent(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCurrent(entity1, entity2);
        const compareResult2 = service.compareCurrent(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCurrent(entity1, entity2);
        const compareResult2 = service.compareCurrent(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCurrent(entity1, entity2);
        const compareResult2 = service.compareCurrent(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
