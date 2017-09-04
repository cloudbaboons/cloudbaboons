import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CloudbaboonsSharedModule } from '../../shared';
import {
    GenappService,
    GenappPopupService,
    GenappComponent,
    GenappDetailComponent,
    GenappDialogComponent,
    GenappPopupComponent,
    GenappDeletePopupComponent,
    GenappDeleteDialogComponent,
    genappRoute,
    genappPopupRoute,
} from './';

const ENTITY_STATES = [
    ...genappRoute,
    ...genappPopupRoute,
];

@NgModule({
    imports: [
        CloudbaboonsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GenappComponent,
        GenappDetailComponent,
        GenappDialogComponent,
        GenappDeleteDialogComponent,
        GenappPopupComponent,
        GenappDeletePopupComponent,
    ],
    entryComponents: [
        GenappComponent,
        GenappDialogComponent,
        GenappPopupComponent,
        GenappDeleteDialogComponent,
        GenappDeletePopupComponent,
    ],
    providers: [
        GenappService,
        GenappPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CloudbaboonsGenappModule {}
