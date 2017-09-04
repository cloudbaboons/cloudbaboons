import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Genapp } from './genapp.model';
import { GenappPopupService } from './genapp-popup.service';
import { GenappService } from './genapp.service';

@Component({
    selector: 'cb-genapp-dialog',
    templateUrl: './genapp-dialog.component.html'
})
export class GenappDialogComponent implements OnInit {

    genapp: Genapp;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private genappService: GenappService,
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
        if (this.genapp.id !== undefined) {
            this.subscribeToSaveResponse(
                this.genappService.update(this.genapp), false);
        } else {
            this.subscribeToSaveResponse(
                this.genappService.create(this.genapp), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Genapp>, isCreated: boolean) {
        result.subscribe((res: Genapp) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Genapp, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'cloudbaboonsApp.genapp.created'
            : 'cloudbaboonsApp.genapp.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'genappListModification', content: 'OK'});
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
    selector: 'cb-genapp-popup',
    template: ''
})
export class GenappPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private genappPopupService: GenappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.genappPopupService
                    .open(GenappDialogComponent, params['id']);
            } else {
                this.modalRef = this.genappPopupService
                    .open(GenappDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
