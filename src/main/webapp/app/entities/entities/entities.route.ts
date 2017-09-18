import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EntitiesComponent } from './entities.component';
import { EntitiesDetailComponent } from './entities-detail.component';
import { EntitiesPopupComponent } from './entities-dialog.component';
import { EntitiesDeletePopupComponent } from './entities-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class EntitiesResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const entitiesRoute: Routes = [
    {
        path: 'entities',
        component: EntitiesComponent,
        resolve: {
            'pagingParams': EntitiesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.entities.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'entities/:id',
        component: EntitiesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.entities.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entitiesPopupRoute: Routes = [
    {
        path: 'entities-new',
        component: EntitiesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.entities.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entities/:id/edit',
        component: EntitiesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.entities.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entities/:id/delete',
        component: EntitiesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.entities.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
