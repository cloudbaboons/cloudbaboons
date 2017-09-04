import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CloudbaboonsSharedModule } from '../../shared';
import {
    CodegeneratorService,
    CodegeneratorPopupService,
    CodegeneratorComponent,
    CodegeneratorDetailComponent,
    CodegeneratorDialogComponent,
    CodegeneratorPopupComponent,
    CodegeneratorDeletePopupComponent,
    CodegeneratorDeleteDialogComponent,
    codegeneratorRoute,
    codegeneratorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...codegeneratorRoute,
    ...codegeneratorPopupRoute,
];

@NgModule({
    imports: [
        CloudbaboonsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CodegeneratorComponent,
        CodegeneratorDetailComponent,
        CodegeneratorDialogComponent,
        CodegeneratorDeleteDialogComponent,
        CodegeneratorPopupComponent,
        CodegeneratorDeletePopupComponent,
    ],
    entryComponents: [
        CodegeneratorComponent,
        CodegeneratorDialogComponent,
        CodegeneratorPopupComponent,
        CodegeneratorDeleteDialogComponent,
        CodegeneratorDeletePopupComponent,
    ],
    providers: [
        CodegeneratorService,
        CodegeneratorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CloudbaboonsCodegeneratorModule {}
