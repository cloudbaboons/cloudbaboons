import { Route } from '@angular/router';

import { CbDocsComponent } from './docs.component';

export const docsRoute: Route = {
    path: 'docs',
    component: CbDocsComponent,
    data: {
        pageTitle: 'global.menu.admin.apidocs'
    }
};
