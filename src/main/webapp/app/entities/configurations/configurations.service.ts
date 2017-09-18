import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Configurations } from './configurations.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ConfigurationsService {

    private resourceUrl = 'api/configurations';

    constructor(private http: Http) { }

    create(configurations: Configurations): Observable<Configurations> {
        const copy = this.convert(configurations);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(configurations: Configurations): Observable<Configurations> {
        const copy = this.convert(configurations);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Configurations> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(configurations: Configurations): Configurations {
        const copy: Configurations = Object.assign({}, configurations);
        return copy;
    }
}
