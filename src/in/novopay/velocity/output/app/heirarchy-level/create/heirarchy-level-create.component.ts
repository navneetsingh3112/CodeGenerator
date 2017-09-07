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
/**
* heirarchy-level Create component
* @author Manoj
*/
@Component({
    selector: '[heirarchy-level-create]',
    templateUrl: './heirarchy-level-create.component.html',
    encapsulation: ViewEncapsulation.None,
})
export class HeirarchyLevelCreateComponent extends BaseComponent {
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

        let componentProperty : ICreateComponentProperty = {
            componentPermissionKey: 'PERMISSION_KEY',
            formHtmlId: 'heirarchy-level-create-form',
            createComponentAPI: 'http://192.168.150.18:8080/api-gateway/api/v1/createOrUpdateHeirarchyLevel',
            listComponentUrl: '/app/heirarchy-level/list',
            entity: {
                            levelName: {
                    value: '',
                    type: 'Textbox',
                    apiKey: 'level_name'
                },
                            levelCode: {
                    value: '',
                    type: 'Textbox',
                    apiKey: 'level_code'
                },
                            description: {
                    value: '',
                    type: 'Textarea',
                    apiKey: 'description'
                },
                            departmentName: {
                    value: '',
                    type: 'Dropdown',
                    apiKey: 'department_name'
                },
                            heirarchyLevelType: {
                    value: '',
                    type: 'Dropdown',
                    apiKey: 'heirarchy_level_type'
                },
                        }
        };
        super.setComponentProperties(componentProperty);
    }
    ngOnInit(): void {
        super.ngOnInit();
    }
}