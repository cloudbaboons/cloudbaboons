import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CloudbaboonsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GenappDetailComponent } from '../../../../../../main/webapp/app/entities/genapp/genapp-detail.component';
import { GenappService } from '../../../../../../main/webapp/app/entities/genapp/genapp.service';
import { Genapp } from '../../../../../../main/webapp/app/entities/genapp/genapp.model';

describe('Component Tests', () => {

    describe('Genapp Management Detail Component', () => {
        let comp: GenappDetailComponent;
        let fixture: ComponentFixture<GenappDetailComponent>;
        let service: GenappService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CloudbaboonsTestModule],
                declarations: [GenappDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GenappService,
                    JhiEventManager
                ]
            }).overrideTemplate(GenappDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GenappDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GenappService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Genapp(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.genapp).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
