import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CloudbaboonsCodegeneratorModule } from './codegenerator/codegenerator.module';
import { CloudbaboonsGenappModule } from './genapp/genapp.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CloudbaboonsCodegeneratorModule,
        CloudbaboonsGenappModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CloudbaboonsEntityModule {}
