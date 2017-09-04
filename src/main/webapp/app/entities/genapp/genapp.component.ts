import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Genapp } from './genapp.model';
import { GenappService } from './genapp.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'cb-genapp',
    templateUrl: './genapp.component.html'
})
export class GenappComponent implements OnInit, OnDestroy {
genapps: Genapp[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private genappService: GenappService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.genappService.query().subscribe(
            (res: ResponseWrapper) => {
                this.genapps = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGenapps();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Genapp) {
        return item.id;
    }
    registerChangeInGenapps() {
        this.eventSubscriber = this.eventManager.subscribe('genappListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
