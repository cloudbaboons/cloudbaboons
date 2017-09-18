import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CloudbaboonsCodegeneratorModule } from './codegenerator/codegenerator.module';
import { CloudbaboonsGenappModule } from './genapp/genapp.module';
import { CloudbaboonsConfigurationsModule } from './configurations/configurations.module';
import { CloudbaboonsEntitiesModule } from './entities/entities.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CloudbaboonsCodegeneratorModule,
        CloudbaboonsGenappModule,
        CloudbaboonsConfigurationsModule,
        CloudbaboonsEntitiesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CloudbaboonsEntityModule {}
