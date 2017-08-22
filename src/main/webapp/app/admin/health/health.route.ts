import { Route } from '@angular/router';

import { CbHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
    path: 'cb-health',
    component: CbHealthCheckComponent,
    data: {
        pageTitle: 'health.title'
    }
};
