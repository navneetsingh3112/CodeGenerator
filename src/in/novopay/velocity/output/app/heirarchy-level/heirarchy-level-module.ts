import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AlertModule, TooltipModule } from 'ng2-bootstrap';
import { ButtonsModule, DropdownModule, PaginationModule  } from 'ng2-bootstrap';
import { DataTableModule } from 'angular2-datatable';
import { Ng2TableModule } from 'ng2-table';
import {FormWizardModule} from "../components/wizards/form-wizards/form-wizards.module";

declare var global:any;
/* tslint:disable */
var markdown = require('markdown').markdown;
/* tslint:enable */
global.markdown = markdown;
import 'bootstrap-markdown/js/bootstrap-markdown.js';
import 'bootstrap-select/dist/js/bootstrap-select.js';
import 'parsleyjs';
import 'bootstrap-application-wizard/src/bootstrap-wizard.js';
import 'twitter-bootstrap-wizard/jquery.bootstrap.wizard.js';
import 'jasny-bootstrap/docs/assets/js/vendor/holder.js';
import 'jasny-bootstrap/js/fileinput.js';
import 'ng2-datetime/src/vendor/bootstrap-datepicker/bootstrap-datepicker.min.js';
import 'ng2-datetime/src/vendor/bootstrap-timepicker/bootstrap-timepicker.min.js';
import 'bootstrap-colorpicker';
import 'bootstrap-slider/dist/bootstrap-slider.js';
import 'jasny-bootstrap/docs/assets/js/vendor/holder.js';
import 'jasny-bootstrap/js/fileinput.js';
import 'jasny-bootstrap/js/inputmask.js';

import { Select2Module } from 'ng2-select2';
import { WidgetModule } from '../layout/widget/widget.module';
import { TextMaskModule } from 'angular2-text-mask';

import { UtilsModule } from '../layout/utils/utils.module';
import { JqSparklineModule } from '../components/sparkline/sparkline.module';
/* tslint:disable */
import { BootstrapWizardModule } from '../components/wizard/wizard.module';
import { DropzoneDemo } from '../components/dropzone/dropzone.directive';
import { NKDatetimeModule } from 'ng2-datetime/ng2-datetime';
/* tslint:enable */
import {TranslateModule} from "../translate/translate.module";
import {ResourceFactoryConstants} from "../services/resource-factory.constants";
import {BusyModule} from 'angular2-busy';
import {CommonUtilityService} from "../services/common-utility.service";
import {CommonHttpService} from "../services/common-http.service";
import {BaseService} from "../base-module/base.service";

import {HeirarchyLevelCreateComponent} from "./create/heirarchy-level-create.component";
import {HeirarchyLevelListComponent} from "./list/heirarchy-level-list.component";
import {HeirarchyLevelViewComponent} from "./view/heirarchy-level-view.component";
import {HeirarchyLevelEditComponent} from "./view/heirarchy-level-view.component";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'create', component: HeirarchyLevelCreateComponent},
    {path: 'list', component: HeirarchyLevelListComponent},
    {path: 'view/:id', component: HeirarchyLevelViewComponent},
    {path: 'edit/:id', component: HeirarchyLevelEditComponent}
];

@NgModule({
    declarations: [
        HeirarchyLevelCreateComponent,
        HeirarchyLevelListComponent,
        HeirarchyLevelViewComponent,
        HeirarchyLevelEditComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        TextMaskModule,
        BusyModule,
        TooltipModule.forRoot(),
        AlertModule.forRoot(),
        DropdownModule.forRoot(),
        WidgetModule,
        BootstrapWizardModule,
        NKDatetimeModule,
        Select2Module,
        RouterModule.forChild(routes),
        JqSparklineModule,
        AlertModule.forRoot(),
        ButtonsModule.forRoot(),
        PaginationModule.forRoot(),
        UtilsModule,
        Ng2TableModule,
        DataTableModule,
        TranslateModule,
        FormWizardModule

    ],
    providers: [CommonHttpService, CommonUtilityService, ResourceFactoryConstants, BaseService]
})
export class HeirarchyLevelModule {
    static routes = routes;
}
