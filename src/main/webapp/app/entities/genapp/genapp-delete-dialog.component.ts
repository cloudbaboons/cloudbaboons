import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Genapp } from './genapp.model';
import { GenappPopupService } from './genapp-popup.service';
import { GenappService } from './genapp.service';

@Component({
    selector: 'cb-genapp-delete-dialog',
    templateUrl: './genapp-delete-dialog.component.html'
})
export class GenappDeleteDialogComponent {

    genapp: Genapp;

    constructor(
        private genappService: GenappService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.genappService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'genappListModification',
                content: 'Deleted an genapp'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('cloudbaboonsApp.genapp.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'cb-genapp-delete-popup',
    template: ''
})
export class GenappDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private genappPopupService: GenappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.genappPopupService
                .open(GenappDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
