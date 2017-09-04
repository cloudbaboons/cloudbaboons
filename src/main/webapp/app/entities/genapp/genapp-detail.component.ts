import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Genapp } from './genapp.model';
import { GenappService } from './genapp.service';

@Component({
    selector: 'cb-genapp-detail',
    templateUrl: './genapp-detail.component.html'
})
export class GenappDetailComponent implements OnInit, OnDestroy {

    genapp: Genapp;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private genappService: GenappService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGenapps();
    }

    load(id) {
        this.genappService.find(id).subscribe((genapp) => {
            this.genapp = genapp;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGenapps() {
        this.eventSubscriber = this.eventManager.subscribe(
            'genappListModification',
            (response) => this.load(this.genapp.id)
        );
    }
}
