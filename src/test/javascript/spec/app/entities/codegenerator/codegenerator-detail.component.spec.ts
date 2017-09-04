import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CloudbaboonsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CodegeneratorDetailComponent } from '../../../../../../main/webapp/app/entities/codegenerator/codegenerator-detail.component';
import { CodegeneratorService } from '../../../../../../main/webapp/app/entities/codegenerator/codegenerator.service';
import { Codegenerator } from '../../../../../../main/webapp/app/entities/codegenerator/codegenerator.model';

describe('Component Tests', () => {

    describe('Codegenerator Management Detail Component', () => {
        let comp: CodegeneratorDetailComponent;
        let fixture: ComponentFixture<CodegeneratorDetailComponent>;
        let service: CodegeneratorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CloudbaboonsTestModule],
                declarations: [CodegeneratorDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CodegeneratorService,
                    JhiEventManager
                ]
            }).overrideTemplate(CodegeneratorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CodegeneratorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CodegeneratorService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Codegenerator(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.codegenerator).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
