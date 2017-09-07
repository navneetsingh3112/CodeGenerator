import {Component, ViewEncapsulation} from '@angular/core';
import {Location} from "@angular/common";
import {ActivatedRoute, Router} from '@angular/router';

import {CommonHttpService} from "../../services/common-http.service";
import {CommonUtilityService} from "../../services/common-utility.service";
import {App} from "../../app.component";
import {AppConstants} from "../../app.constants";
import {BaseComponent} from "../../base-module/create/base-create.component";
import {BaseService} from "../../base-module/base.service";

declare var jQuery:any;

@Component({
    selector: '[heirarchy-level-edit]',
    templateUrl: './heirarchy-level-edit.component.html',
    encapsulation: ViewEncapsulation.None,
})
export class HeirarchyLevelEditComponent extends BaseComponent {
    protected componentIdRequestBodyKey: string;
    protected componentDetailsAPI: string;
    protected componentDetailsResponseBodyKey: string;

    constructor(private location:Location,
                private mainApp: App,
                private appConstants: AppConstants,
                private commonHttpService:CommonHttpService,
                private commonUtilityService:CommonUtilityService,
                private baseService: BaseService,
                private router:Router,
                private route:ActivatedRoute) {
        super(location, mainApp, appConstants, commonHttpService, commonUtilityService,
            baseService, router, route);

        this.componentIdRequestBodyKey = "level_code";
        this.componentDetailsAPI = "http://192.168.150.18:8080/api-gateway/api/v1/getHeirarchyLevelDetails";
        this.componentDetailsResponseBodyKey = "${table}_details";

        let componentProperty : ICreateComponentProperty = {
            componentPermissionKey: 'EDIT_PERMISSION_KEY',
            formHtmlId: 'product-scheme-edit-form',
            createComponentAPI: '/edit',
            listComponentUrl: '/list',
            entity: {
                firstName: {
                    value: '',
                    type: 'TEXT',
                    apiKey: 'first_name'
                },
                lastName: {
                    value: '',
                    type: 'TEXT',
                    apiKey: 'last_name'
                },
                dob: {
                    value: '',
                    type: 'DATE',
                    apiKey: 'date_of_birth'
                }
            }
        };
        super.setComponentProperties(componentProperty);
    }
    ngOnInit(): void {
        super.ngOnInit();
        this.populateComponentData();
    }

    /**
     * Create request body and make API call to populate component data
     * */
    protected populateComponentData(): void {
        let componentId = this.route.snapshot.params['id'];

        let request = {};
        request[this.componentIdRequestBodyKey] = componentId.toString();

        this.busy = this.baseService.getComponentData(request,this.componentDetailsAPI)
            .then(resp => this.handleGetComponentData(resp));
    }

    protected handleGetComponentData(response: any): void {
        if(response.response_status.code!=='000' || !response[this.componentDetailsResponseBodyKey]){
            return;
        }

        let parsedResponse = response[this.componentDetailsResponseBodyKey];

        for(let item in parsedResponse) {
            for(let et in this.entity) {
                if(this.entity[et].apiKey === item) {
                    this.entity[et].value = parsedResponse[item];
                }
            }
        }
    }
}