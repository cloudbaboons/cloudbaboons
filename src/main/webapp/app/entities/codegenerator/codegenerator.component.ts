import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Codegenerator } from './codegenerator.model';
import { CodegeneratorService } from './codegenerator.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'cb-codegenerator',
    templateUrl: './codegenerator.component.html'
})
export class CodegeneratorComponent implements OnInit, OnDestroy {
codegenerators: Codegenerator[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private codegeneratorService: CodegeneratorService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.codegeneratorService.query().subscribe(
            (res: ResponseWrapper) => {
                this.codegenerators = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCodegenerators();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Codegenerator) {
        return item.id;
    }
    registerChangeInCodegenerators() {
        this.eventSubscriber = this.eventManager.subscribe('codegeneratorListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
