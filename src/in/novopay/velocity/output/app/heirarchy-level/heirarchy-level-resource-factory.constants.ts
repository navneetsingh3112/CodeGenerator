import {Component, Injectable, Inject} from '@angular/core';
import {AppConfig} from "../app.config";
import {AppSettings} from '../services/app-settings';

@Injectable()
export class HeirarchyLevelResourceFactoryConstants {
    apiGateway = AppSettings.API_GATEWAY;
    documentHost = AppSettings.DOCUMENT_HOST;

    constants = {
        heirarchyLevel: {
            getList: this.apiGateway + '/api-gateway/api/v1/getHeirarchyLevelList',
            getDetails: this.apiGateway + '/api-gateway/api/v1/getHeirarchyLevelDetails',
            create: this.apiGateway + '/api-gateway/api/v1/createOrUpdateHeirarchyLevel',
            update: this.apiGateway + '/api-gateway/api/v1/createOrUpdateHeirarchyLevel',
            delete: this.apiGateway + '/api-gateway/api/v1/deleteHeirarchyLevel'
        }
    };
}
