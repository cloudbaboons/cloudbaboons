import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Genapp } from './genapp.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GenappService {

    private resourceUrl = 'api/genapps';

    constructor(private http: Http) { }

    create(genapp: Genapp): Observable<Genapp> {
        const copy = this.convert(genapp);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(genapp: Genapp): Observable<Genapp> {
        const copy = this.convert(genapp);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Genapp> {
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

    private convert(genapp: Genapp): Genapp {
        const copy: Genapp = Object.assign({}, genapp);
        return copy;
    }
}
