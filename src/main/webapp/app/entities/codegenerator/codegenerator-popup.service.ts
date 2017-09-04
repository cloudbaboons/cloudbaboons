import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Codegenerator } from './codegenerator.model';
import { CodegeneratorService } from './codegenerator.service';

@Injectable()
export class CodegeneratorPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private codegeneratorService: CodegeneratorService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.codegeneratorService.find(id).subscribe((codegenerator) => {
                this.codegeneratorModalRef(component, codegenerator);
            });
        } else {
            return this.codegeneratorModalRef(component, new Codegenerator());
        }
    }

    codegeneratorModalRef(component: Component, codegenerator: Codegenerator): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.codegenerator = codegenerator;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
