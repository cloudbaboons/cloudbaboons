import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ConfigurationsComponent } from './configurations.component';
import { ConfigurationsDetailComponent } from './configurations-detail.component';
import { ConfigurationsPopupComponent } from './configurations-dialog.component';
import { ConfigurationsDeletePopupComponent } from './configurations-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ConfigurationsResolvePagingParams implements Resolve<any> {

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

export const configurationsRoute: Routes = [
    {
        path: 'configurations',
        component: ConfigurationsComponent,
        resolve: {
            'pagingParams': ConfigurationsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.configurations.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'configurations/:id',
        component: ConfigurationsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.configurations.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const configurationsPopupRoute: Routes = [
    {
        path: 'configurations-new',
        component: ConfigurationsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.configurations.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'configurations/:id/edit',
        component: ConfigurationsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.configurations.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'configurations/:id/delete',
        component: ConfigurationsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.configurations.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
