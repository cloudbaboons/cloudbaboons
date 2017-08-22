import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CloudbaboonsBarchartModule } from './barchart/barchart.module';
import { CloudbaboonsDoughnutchartModule } from './doughnutchart/doughnutchart.module';
import { CloudbaboonsLinechartModule } from './linechart/linechart.module';
import { CloudbaboonsPiechartModule } from './piechart/piechart.module';
import { CloudbaboonsPolarareachartModule } from './polarareachart/polarareachart.module';
import { CloudbaboonsRadarchartModule } from './radarchart/radarchart.module';

@NgModule({
    imports: [
        CloudbaboonsBarchartModule,
        CloudbaboonsDoughnutchartModule,
        CloudbaboonsLinechartModule,
        CloudbaboonsPiechartModule,
        CloudbaboonsPolarareachartModule,
        CloudbaboonsRadarchartModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CloudbaboonsDashboardModule {}
