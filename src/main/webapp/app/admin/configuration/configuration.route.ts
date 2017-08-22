import { Route } from '@angular/router';

import { CbConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
    path: 'cb-configuration',
    component: CbConfigurationComponent,
    data: {
        pageTitle: 'configuration.title'
    }
};
