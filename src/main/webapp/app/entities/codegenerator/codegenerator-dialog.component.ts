import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Codegenerator } from './codegenerator.model';
import { CodegeneratorPopupService } from './codegenerator-popup.service';
import { CodegeneratorService } from './codegenerator.service';

@Component({
    selector: 'cb-codegenerator-dialog',
    templateUrl: './codegenerator-dialog.component.html'
})
export class CodegeneratorDialogComponent implements OnInit {

    codegenerator: Codegenerator;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private codegeneratorService: CodegeneratorService,
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
        if (this.codegenerator.id !== undefined) {
            this.subscribeToSaveResponse(
                this.codegeneratorService.update(this.codegenerator), false);
        } else {
            this.subscribeToSaveResponse(
                this.codegeneratorService.create(this.codegenerator), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Codegenerator>, isCreated: boolean) {
        result.subscribe((res: Codegenerator) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Codegenerator, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'cloudbaboonsApp.codegenerator.created'
            : 'cloudbaboonsApp.codegenerator.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'codegeneratorListModification', content: 'OK'});
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
    selector: 'cb-codegenerator-popup',
    template: ''
})
export class CodegeneratorPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private codegeneratorPopupService: CodegeneratorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.codegeneratorPopupService
                    .open(CodegeneratorDialogComponent, params['id']);
            } else {
                this.modalRef = this.codegeneratorPopupService
                    .open(CodegeneratorDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
