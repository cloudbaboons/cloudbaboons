import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { CloudbaboonsSharedModule, UserRouteAccessService } from './shared';
import { CloudbaboonsHomeModule } from './home/home.module';
import { CloudbaboonsAdminModule } from './admin/admin.module';
import { CloudbaboonsAccountModule } from './account/account.module';
import { CloudbaboonsEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import { CloudbaboonsDashboardModule } from './dashboard/dashboard.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    CbMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        CloudbaboonsSharedModule,
        CloudbaboonsHomeModule,
        CloudbaboonsAdminModule,
        CloudbaboonsAccountModule,
        CloudbaboonsEntityModule,
        CloudbaboonsDashboardModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        CbMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ CbMainComponent ]
})
export class CloudbaboonsAppModule {}
