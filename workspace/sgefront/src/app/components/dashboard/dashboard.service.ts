import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MS_ANALYTICS_URL } from '../../app.apis';

@Injectable()
export class DashboardService {

  constructor(private http: HttpClient) { }

  getMetrics(){
    return this.http.get(`${MS_ANALYTICS_URL}/metricas/contrato`);
  }

}
