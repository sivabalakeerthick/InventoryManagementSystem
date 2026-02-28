import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { OrderStatusCountDTO, StockInfoDTO } from '@services/report-analysis.service';
import { ChartData, ChartOptions } from 'chart.js';
import { ViewChild } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';





@Component({
  selector: 'app-dashboard-summary',
  templateUrl: './dashboard-summary.component.html',
  styleUrls: ['./dashboard-summary.component.css'],
})
export class DashboardSummaryComponent implements OnChanges , OnInit {
  @Input() inventoryValue!: number;
  @Input() totalSupplier!:number;
  @Input() totalProducts!: number;
  @Input() productStatus!: StockInfoDTO[];
  @Input() orderStatus!: OrderStatusCountDTO[];
  @Input() purchaseStatus!: OrderStatusCountDTO[];

  viewReady = false;

  @ViewChild("stockChart" , { static: true, read: BaseChartDirective }) stockChart!: BaseChartDirective;
  @ViewChild("orderChart" , { static: true, read: BaseChartDirective }) orderChart!: BaseChartDirective;
  @ViewChild("purchaseChart" , { static: true, read: BaseChartDirective }) purchaseChart!: BaseChartDirective;

  refreshCharts() {
    console.log(this.stockChart);
    this.stockChart?.update();
    this.orderChart?.update();
    this.purchaseChart?.update();
  }

  ngAfterViewInit(): void {
    this.viewReady = true;
    this.refreshCharts();
  }


  ngOnInit(): void {
    this.updateStockChart();
    this.updateOrderChart();
    this.updatePurchaseChart();
  }

  

  

  stockChartData: ChartData<'bar'> = {
    labels: [],
    datasets: [{
      label: 'Stock Status',
      data: [],
      backgroundColor: ['#4caf50', '#ff9800', '#f44336','#f44336']
    }]
  };

  barChartOptions: ChartOptions<'bar'> = {
    responsive: true,
    plugins: {
      legend: { display: false },
    },
    scales: {
      y: { beginAtZero: true }
    }
  };

  pieOptions: ChartOptions<'doughnut'> = {
    responsive: true,
    plugins: {
      legend: { position: 'bottom' }
    }
  };

  orderChartData: ChartData<'doughnut'> = {
    labels: [],
    datasets: [{
      label: 'Orders',
      data: [],
      backgroundColor: ['#42a5f5', '#66bb6a', '#ef5350', '#ab47bc']
    }]
  };

  purchaseChartData: ChartData<'doughnut'> = {
    labels: [],
    datasets: [{
      label: 'Purchases',
      data: [],
      backgroundColor: ['#29b6f6', '#66bb6a', '#ffa726', '#8d6e63']
    }]
  };

  

  

  ngOnChanges(changes: SimpleChanges): void {
    this.updateStockChart();
    this.updateOrderChart();
    this.updatePurchaseChart();
    this.refreshCharts();
  }

  private updateStockChart(): void {
    if (this.productStatus?.length) {
      
      
      this.stockChartData.labels = this.productStatus.map(s => s.status);
      this.stockChartData.datasets[0].data = this.productStatus.map(s => s.count);

      console.log(this.stockChartData);
      
    }
   
  }

  private updateOrderChart(): void {
    if (this.orderStatus?.length) {
      this.orderChartData.labels = this.orderStatus.map(s => s.status);
      this.orderChartData.datasets[0].data = this.orderStatus.map(s => s.count);
      console.log(this.orderChartData);
      
    }
  }

  private updatePurchaseChart(): void {
    if (this.purchaseStatus?.length) {
      this.purchaseChartData.labels = this.purchaseStatus.map(s => s.status);
      this.purchaseChartData.datasets[0].data = this.purchaseStatus.map(s => s.count);
      console.log(this.purchaseChartData);
      
    }
  }
}
