import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IPreferences } from 'app/shared/model/preferences.model';

type EntityResponseType = HttpResponse<IPreferences>;
type EntityArrayResponseType = HttpResponse<IPreferences[]>;

@Injectable({ providedIn: 'root' })
export class PreferencesService {
  public resourceUrl = SERVER_API_URL + 'api/preferences';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/preferences';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPreferences>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPreferences[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPreferences[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
