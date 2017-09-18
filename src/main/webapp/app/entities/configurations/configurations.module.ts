import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CloudbaboonsSharedModule } from '../../shared';
import {
    ConfigurationsService,
    ConfigurationsPopupService,
    ConfigurationsComponent,
    ConfigurationsDetailComponent,
    ConfigurationsDialogComponent,
    ConfigurationsPopupComponent,
    ConfigurationsDeletePopupComponent,
    ConfigurationsDeleteDialogComponent,
    configurationsRoute,
    configurationsPopupRoute,
    ConfigurationsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...configurationsRoute,
    ...configurationsPopupRoute,
];

@NgModule({
    imports: [
        CloudbaboonsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ConfigurationsComponent,
        ConfigurationsDetailComponent,
        ConfigurationsDialogComponent,
        ConfigurationsDeleteDialogComponent,
        ConfigurationsPopupComponent,
        ConfigurationsDeletePopupComponent,
    ],
    entryComponents: [
        ConfigurationsComponent,
        ConfigurationsDialogComponent,
        ConfigurationsPopupComponent,
        ConfigurationsDeleteDialogComponent,
        ConfigurationsDeletePopupComponent,
    ],
    providers: [
        ConfigurationsService,
        ConfigurationsPopupService,
        ConfigurationsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CloudbaboonsConfigurationsModule {}
