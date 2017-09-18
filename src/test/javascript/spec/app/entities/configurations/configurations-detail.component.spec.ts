import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CloudbaboonsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConfigurationsDetailComponent } from '../../../../../../main/webapp/app/entities/configurations/configurations-detail.component';
import { ConfigurationsService } from '../../../../../../main/webapp/app/entities/configurations/configurations.service';
import { Configurations } from '../../../../../../main/webapp/app/entities/configurations/configurations.model';

describe('Component Tests', () => {

    describe('Configurations Management Detail Component', () => {
        let comp: ConfigurationsDetailComponent;
        let fixture: ComponentFixture<ConfigurationsDetailComponent>;
        let service: ConfigurationsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CloudbaboonsTestModule],
                declarations: [ConfigurationsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConfigurationsService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConfigurationsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConfigurationsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfigurationsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Configurations(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.configurations).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
