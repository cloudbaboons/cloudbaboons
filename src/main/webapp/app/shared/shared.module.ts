import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    CloudbaboonsSharedLibsModule,
    CloudbaboonsSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    Principal,
    HasAnyAuthorityDirective,
    CbLoginModalComponent
} from './';

@NgModule({
    imports: [
        CloudbaboonsSharedLibsModule,
        CloudbaboonsSharedCommonModule
    ],
    declarations: [
        CbLoginModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe
    ],
    entryComponents: [CbLoginModalComponent],
    exports: [
        CloudbaboonsSharedCommonModule,
        CbLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class CloudbaboonsSharedModule {}
