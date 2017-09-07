import {Component, ViewEncapsulation} from '@angular/core';
import {Location} from "@angular/common";
import {ActivatedRoute, Router} from '@angular/router';

import {CommonHttpService} from "../../services/common-http.service";
import {CommonUtilityService} from "../../services/common-utility.service";
import {App} from "../../app.component";
import {AppConstants} from "../../app.constants";
import {BaseComponent} from "../../base-module/create/base-create.component";
import {BaseService} from "../../base-module/base.service";
import {BaseViewComponent} from "../../base-module/view/base-view.component";

declare var jQuery:any;

@Component({
    selector: '[heirarchy-level-view]',
    templateUrl: './heirarchy-level-view.component.html',
    encapsulation: ViewEncapsulation.None,
})
export class HeirarchyLevelViewComponent extends BaseViewComponent {
    constructor(private location:Location,
                private mainApp: App,
                private appConstants: AppConstants,
                private commonHttpService:CommonHttpService,
                private baseService: BaseService,
                private commonUtilityService:CommonUtilityService,
                private router:Router,
                private route:ActivatedRoute) {
        super(location, mainApp, appConstants, commonHttpService, baseService,
            commonUtilityService, router, route);

        let componentProperty : IViewComponentProperty = {
            componentPermissionKey: "PERMISSION_KEY",
            componentDeletePermissionKey: "DELETE_PERMISSION_KEY",
            componentEditPermissionKey: "EDIT_PERMISSION_KEY",
            componentIdRequestBodyKey: "level_code",
            componentDetailsResponseBodyKey: "${table}_details",
            viewComponentAPI: "http://192.168.150.18:8080/api-gateway/api/v1/getHeirarchyLevelDetails",
            deleteComponentAPI: "/delete",
            editComponentAPI: "/edit",
            entity: {
                            "levelName": {
                    value: '',
                    displayName: 'Level Name',
                    apiKey: 'level_name'
                },
                            "levelCode": {
                    value: '',
                    displayName: 'Level Code',
                    apiKey: 'level_code'
                },
                            "description": {
                    value: '',
                    displayName: 'Description',
                    apiKey: 'description'
                },
                            "departmentName": {
                    value: '',
                    displayName: 'Department Name',
                    apiKey: 'department_name'
                },
                            "heirarchyLevelType": {
                    value: '',
                    displayName: 'Type of Hierarchy Level',
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