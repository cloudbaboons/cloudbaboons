import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GenappComponent } from './genapp.component';
import { GenappDetailComponent } from './genapp-detail.component';
import { GenappPopupComponent } from './genapp-dialog.component';
import { GenappDeletePopupComponent } from './genapp-delete-dialog.component';

import { Principal } from '../../shared';

export const genappRoute: Routes = [
    {
        path: 'genapp',
        component: GenappComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.genapp.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'genapp/:id',
        component: GenappDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.genapp.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const genappPopupRoute: Routes = [
    {
        path: 'genapp-new',
        component: GenappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.genapp.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'genapp/:id/edit',
        component: GenappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.genapp.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'genapp/:id/delete',
        component: GenappDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudbaboonsApp.genapp.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
