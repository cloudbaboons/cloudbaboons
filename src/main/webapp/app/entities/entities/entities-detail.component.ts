import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Entities } from './entities.model';
import { EntitiesService } from './entities.service';

@Component({
    selector: 'cb-entities-detail',
    templateUrl: './entities-detail.component.html'
})
export class EntitiesDetailComponent implements OnInit, OnDestroy {

    entities: Entities;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private entitiesService: EntitiesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEntities();
    }

    load(id) {
        this.entitiesService.find(id).subscribe((entities) => {
            this.entities = entities;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'entitiesListModification',
            (response) => this.load(this.entities.id)
        );
    }
}
