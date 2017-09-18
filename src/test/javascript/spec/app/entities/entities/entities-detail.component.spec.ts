import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CloudbaboonsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EntitiesDetailComponent } from '../../../../../../main/webapp/app/entities/entities/entities-detail.component';
import { EntitiesService } from '../../../../../../main/webapp/app/entities/entities/entities.service';
import { Entities } from '../../../../../../main/webapp/app/entities/entities/entities.model';

describe('Component Tests', () => {

    describe('Entities Management Detail Component', () => {
        let comp: EntitiesDetailComponent;
        let fixture: ComponentFixture<EntitiesDetailComponent>;
        let service: EntitiesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CloudbaboonsTestModule],
                declarations: [EntitiesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EntitiesService,
                    JhiEventManager
                ]
            }).overrideTemplate(EntitiesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntitiesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntitiesService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Entities(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.entities).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
