import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Codegenerator } from './codegenerator.model';
import { CodegeneratorService } from './codegenerator.service';

@Component({
    selector: 'cb-codegenerator-detail',
    templateUrl: './codegenerator-detail.component.html'
})
export class CodegeneratorDetailComponent implements OnInit, OnDestroy {

    codegenerator: Codegenerator;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private codegeneratorService: CodegeneratorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCodegenerators();
    }

    load(id) {
        this.codegeneratorService.find(id).subscribe((codegenerator) => {
            this.codegenerator = codegenerator;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCodegenerators() {
        this.eventSubscriber = this.eventManager.subscribe(
            'codegeneratorListModification',
            (response) => this.load(this.codegenerator.id)
        );
    }
}
