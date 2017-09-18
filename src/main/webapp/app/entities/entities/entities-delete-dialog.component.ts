import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Entities } from './entities.model';
import { EntitiesPopupService } from './entities-popup.service';
import { EntitiesService } from './entities.service';

@Component({
    selector: 'cb-entities-delete-dialog',
    templateUrl: './entities-delete-dialog.component.html'
})
export class EntitiesDeleteDialogComponent {

    entities: Entities;

    constructor(
        private entitiesService: EntitiesService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entitiesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'entitiesListModification',
                content: 'Deleted an entities'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('cloudbaboonsApp.entities.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'cb-entities-delete-popup',
    template: ''
})
export class EntitiesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entitiesPopupService: EntitiesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.entitiesPopupService
                .open(EntitiesDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
