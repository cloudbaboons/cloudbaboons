import { NgModule } from '@angular/core';
import { Title } from '@angular/platform-browser';

import {
    CloudbaboonsSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    CbAlertComponent,
    CbAlertErrorComponent
} from './';

@NgModule({
    imports: [
        CloudbaboonsSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        CbAlertComponent,
        CbAlertErrorComponent
    ],
    providers: [
        JhiLanguageHelper,
        Title
    ],
    exports: [
        CloudbaboonsSharedLibsModule,
        FindLanguageFromKeyPipe,
        CbAlertComponent,
        CbAlertErrorComponent
    ]
})
export class CloudbaboonsSharedCommonModule {}
