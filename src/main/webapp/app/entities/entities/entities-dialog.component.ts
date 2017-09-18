import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Entities } from './entities.model';
import { EntitiesPopupService } from './entities-popup.service';
import { EntitiesService } from './entities.service';

@Component({
    selector: 'cb-entities-dialog',
    templateUrl: './entities-dialog.component.html'
})
export class EntitiesDialogComponent implements OnInit {

    entities: Entities;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private entitiesService: EntitiesService,
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
        if (this.entities.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entitiesService.update(this.entities), false);
        } else {
            this.subscribeToSaveResponse(
                this.entitiesService.create(this.entities), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Entities>, isCreated: boolean) {
        result.subscribe((res: Entities) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Entities, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'cloudbaboonsApp.entities.created'
            : 'cloudbaboonsApp.entities.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'entitiesListModification', content: 'OK'});
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
    selector: 'cb-entities-popup',
    template: ''
})
export class EntitiesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entitiesPopupService: EntitiesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.entitiesPopupService
                    .open(EntitiesDialogComponent, params['id']);
            } else {
                this.modalRef = this.entitiesPopupService
                    .open(EntitiesDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
