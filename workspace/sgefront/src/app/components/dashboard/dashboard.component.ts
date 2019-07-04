import { Component, OnInit } from '@angular/core';
import { DashboardService } from './dashboard.service';
import { ContratoMetric } from './dashboard.model';
import { ResponseDashboard } from './response.dashboard';
import { Chart } from 'chart.js';
import 'chartjs-plugin-colorschemes';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { CursoMetric } from './curso.dashboard.model';

@Component({
  selector: 'sge-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  chart = [];
  contratoMetric:ContratoMetric;
  cursoMetrics:CursoMetric[];
  constructor(private dashboardService: DashboardService) {
    this.getMetrics();
   }

  ngOnInit() {

    this.getMetrics();

}

funcaoX(metric: ContratoMetric){
 var names = metric.cursos.map(c => c.nome);

 var quantidades =  metric.cursos.map(c => c.total);

  var myDoughnutChart = new Chart('canvas', {
    type: 'doughnut',
    data: {
      labels: names,
      datasets: [{
          label: 'Cursos',
          data: quantidades
      }]
  },options: {
    plugins: {
      colorschemes: {
        scheme: 'brewer.Paired12'

      }
    }

  }
});

console.log(metric);

var myDoughnutChart = new Chart('canvas2', {
  type: 'bar',
  data: {
    labels: ['Ativos', 'Finalizados'],
    datasets: [{
        label: 'Contratos',
        backgroundColor:['#58FA28','#0EA3F2'],
        data: [metric.contratosAtivos, metric.contratosFinalizados]
    }]
},options:{
  scales: {
    yAxes: [{
        ticks: {
            beginAtZero: true}
        }]
    }
 }
});
}

   getMetrics(){
   this.dashboardService.getMetrics().subscribe((responseDashboard:ResponseDashboard) => {
      this.funcaoX(responseDashboard['contratoMetricInfoDto']); 
      this.contratoMetric = responseDashboard['contratoMetricInfoDto'];
      
    });
  }

}
