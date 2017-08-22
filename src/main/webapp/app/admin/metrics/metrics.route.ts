import { Route } from '@angular/router';

import { CbMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
    path: 'cb-metrics',
    component: CbMetricsMonitoringComponent,
    data: {
        pageTitle: 'metrics.title'
    }
};
