import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Configurations } from './configurations.model';
import { ConfigurationsService } from './configurations.service';

@Component({
    selector: 'cb-configurations-detail',
    templateUrl: './configurations-detail.component.html'
})
export class ConfigurationsDetailComponent implements OnInit, OnDestroy {

    configurations: Configurations;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private configurationsService: ConfigurationsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConfigurations();
    }

    load(id) {
        this.configurationsService.find(id).subscribe((configurations) => {
            this.configurations = configurations;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConfigurations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'configurationsListModification',
            (response) => this.load(this.configurations.id)
        );
    }
}
