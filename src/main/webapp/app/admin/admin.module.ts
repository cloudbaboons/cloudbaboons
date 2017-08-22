import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CloudbaboonsSharedModule } from '../shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserDialogComponent,
    UserDeleteDialogComponent,
    UserMgmtDetailComponent,
    UserMgmtDialogComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    CbMetricsMonitoringModalComponent,
    CbMetricsMonitoringComponent,
    CbHealthModalComponent,
    CbHealthCheckComponent,
    CbConfigurationComponent,
    CbDocsComponent,
    AuditsService,
    CbConfigurationService,
    CbHealthService,
    CbMetricsService,
    LogsService,
    UserResolvePagingParams,
    UserResolve,
    UserModalService
} from './';

@NgModule({
    imports: [
        CloudbaboonsSharedModule,
        RouterModule.forRoot(adminState, { useHash: true }),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserDialogComponent,
        UserDeleteDialogComponent,
        UserMgmtDetailComponent,
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        CbConfigurationComponent,
        CbHealthCheckComponent,
        CbHealthModalComponent,
        CbDocsComponent,
        CbMetricsMonitoringComponent,
        CbMetricsMonitoringModalComponent
    ],
    entryComponents: [
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        CbHealthModalComponent,
        CbMetricsMonitoringModalComponent,
    ],
    providers: [
        AuditsService,
        CbConfigurationService,
        CbHealthService,
        CbMetricsService,
        LogsService,
        UserResolvePagingParams,
        UserResolve,
        UserModalService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CloudbaboonsAdminModule {}
