import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Codegenerator } from './codegenerator.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CodegeneratorService {

    private resourceUrl = 'api/codegenerators';

    constructor(private http: Http) { }

    create(codegenerator: Codegenerator): Observable<Codegenerator> {
        const copy = this.convert(codegenerator);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(codegenerator: Codegenerator): Observable<Codegenerator> {
        const copy = this.convert(codegenerator);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Codegenerator> {
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

    private convert(codegenerator: Codegenerator): Codegenerator {
        const copy: Codegenerator = Object.assign({}, codegenerator);
        return copy;
    }
}
