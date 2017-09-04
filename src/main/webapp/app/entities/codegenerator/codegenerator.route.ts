import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CodegeneratorComponent } from './codegenerator.component';
import { CodegeneratorDetailComponent } from './codegenerator-detail.component';
import { CodegeneratorPopupComponent } from './codegenerator-dialog.component';
import { CodegeneratorDeletePopupComponent } from './codegenerator-delete-dialog.component';

import { Principal } from '../../shared';

export const codegeneratorRoute: Routes = [
    {
        path: 'codegenerator',
        component: CodegeneratorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.codegenerator.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'codegenerator/:id',
        component: CodegeneratorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.codegenerator.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const codegeneratorPopupRoute: Routes = [
    {
        path: 'codegenerator-new',
        component: CodegeneratorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.codegenerator.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'codegenerator/:id/edit',
        component: CodegeneratorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.codegenerator.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'codegenerator/:id/delete',
        component: CodegeneratorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.codegenerator.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
