import {Component, ViewEncapsulation} from '@angular/core';
import {Location} from "@angular/common";
import {ActivatedRoute, Router} from '@angular/router';

import {CommonHttpService} from "../../services/common-http.service";
import {CommonUtilityService} from "../../services/common-utility.service";
import {App} from "../../app.component";
import {AppConstants} from "../../app.constants";
import {BaseComponent} from "../../base-module/create/base-create.component";
import {BaseService} from "../../base-module/base.service";
import {BaseListComponent} from "../../base-module/list/base-list.component";

declare var jQuery:any;

@Component({
    selector: '[heirarchy-level-list]',
    templateUrl: './heirarchy-level-list.component.html',
    encapsulation: ViewEncapsulation.None,
})
export class HeirarchyLevelListComponent extends BaseListComponent {
    constructor(private router:Router,
                private location:Location,
                private mainApp: App,
                private commonUtilityService: CommonUtilityService,
                private appConstants: AppConstants,
                private baseService: BaseService,
                private commonHttpService:CommonHttpService) {
        super(router, location, mainApp, commonUtilityService, appConstants,
            baseService, commonHttpService);

        let searchModel = [
                            {
                id: '1',
                type: 'levelName',
                field: 'level_name',
                value: '',
                placeholder: 'eg. Country',
                isSelected: false,
                isSorted: false,
                currentSortingOrder: 'DESC'
            },
                                  {
                id: '2',
                type: 'levelCode',
                field: 'level_code',
                value: '',
                placeholder: 'eg. GIS10',
                isSelected: false,
                isSorted: false,
                currentSortingOrder: 'DESC'
            },
                                  {
                id: '3',
                type: 'description',
                field: 'description',
                value: '',
                placeholder: 'eg. Country',
                isSelected: false,
                isSorted: false,
                currentSortingOrder: 'DESC'
            },
                                  {
                id: '4',
                type: 'departmentName',
                field: 'department_name',
                value: '',
                placeholder: 'eg. Business',
                isSelected: false,
                isSorted: false,
                currentSortingOrder: 'DESC'
            },
                                  {
                id: '5',
                type: 'heirarchyLevelType',
                field: 'heirarchy_level_type',
                value: '',
                placeholder: 'eg. Administrative',
                isSelected: false,
                isSorted: false,
                currentSortingOrder: 'DESC'
            },
                              ];
        
        super.setComponentProperties("heirarchy-level",searchModel,"heirarchy_level_list","heirarchy_level_details","http://192.168.150.18:8080/api-gateway/api/v1/getHeirarchyLevelList");
    }
    ngOnInit(): void {
        super.ngOnInit();
    }
}