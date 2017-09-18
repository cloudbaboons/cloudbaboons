import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Configurations } from './configurations.model';
import { ConfigurationsPopupService } from './configurations-popup.service';
import { ConfigurationsService } from './configurations.service';

@Component({
    selector: 'cb-configurations-dialog',
    templateUrl: './configurations-dialog.component.html'
})
export class ConfigurationsDialogComponent implements OnInit {

    configurations: Configurations;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private configurationsService: ConfigurationsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.configurations.id !== undefined) {
            this.subscribeToSaveResponse(
                this.configurationsService.update(this.configurations), false);
        } else {
            this.subscribeToSaveResponse(
                this.configurationsService.create(this.configurations), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Configurations>, isCreated: boolean) {
        result.subscribe((res: Configurations) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Configurations, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'cloudbaboonsApp.configurations.created'
            : 'cloudbaboonsApp.configurations.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'configurationsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'cb-configurations-popup',
    template: ''
})
export class ConfigurationsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private configurationsPopupService: ConfigurationsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.configurationsPopupService
                    .open(ConfigurationsDialogComponent, params['id']);
            } else {
                this.modalRef = this.configurationsPopupService
                    .open(ConfigurationsDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
