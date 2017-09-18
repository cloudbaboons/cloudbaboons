import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Configurations } from './configurations.model';
import { ConfigurationsPopupService } from './configurations-popup.service';
import { ConfigurationsService } from './configurations.service';

@Component({
    selector: 'cb-configurations-delete-dialog',
    templateUrl: './configurations-delete-dialog.component.html'
})
export class ConfigurationsDeleteDialogComponent {

    configurations: Configurations;

    constructor(
        private configurationsService: ConfigurationsService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.configurationsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'configurationsListModification',
                content: 'Deleted an configurations'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('cloudbaboonsApp.configurations.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'cb-configurations-delete-popup',
    template: ''
})
export class ConfigurationsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private configurationsPopupService: ConfigurationsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.configurationsPopupService
                .open(ConfigurationsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
