import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CloudbaboonsSharedModule } from '../../shared';
import {
    EntitiesService,
    EntitiesPopupService,
    EntitiesComponent,
    EntitiesDetailComponent,
    EntitiesDialogComponent,
    EntitiesPopupComponent,
    EntitiesDeletePopupComponent,
    EntitiesDeleteDialogComponent,
    entitiesRoute,
    entitiesPopupRoute,
    EntitiesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...entitiesRoute,
    ...entitiesPopupRoute,
];

@NgModule({
    imports: [
        CloudbaboonsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EntitiesComponent,
        EntitiesDetailComponent,
        EntitiesDialogComponent,
        EntitiesDeleteDialogComponent,
        EntitiesPopupComponent,
        EntitiesDeletePopupComponent,
    ],
    entryComponents: [
        EntitiesComponent,
        EntitiesDialogComponent,
        EntitiesPopupComponent,
        EntitiesDeleteDialogComponent,
        EntitiesDeletePopupComponent,
    ],
    providers: [
        EntitiesService,
        EntitiesPopupService,
        EntitiesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CloudbaboonsEntitiesModule {}
