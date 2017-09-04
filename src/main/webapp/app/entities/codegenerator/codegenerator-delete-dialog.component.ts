import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Codegenerator } from './codegenerator.model';
import { CodegeneratorPopupService } from './codegenerator-popup.service';
import { CodegeneratorService } from './codegenerator.service';

@Component({
    selector: 'cb-codegenerator-delete-dialog',
    templateUrl: './codegenerator-delete-dialog.component.html'
})
export class CodegeneratorDeleteDialogComponent {

    codegenerator: Codegenerator;

    constructor(
        private codegeneratorService: CodegeneratorService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.codegeneratorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'codegeneratorListModification',
                content: 'Deleted an codegenerator'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('cloudbaboonsApp.codegenerator.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'cb-codegenerator-delete-popup',
    template: ''
})
export class CodegeneratorDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private codegeneratorPopupService: CodegeneratorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.codegeneratorPopupService
                .open(CodegeneratorDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
