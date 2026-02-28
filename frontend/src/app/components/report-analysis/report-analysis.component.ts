import { Component, ComponentRef, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { forkJoin } from 'rxjs';
import { OrderReportComponent } from '../order-report/order-report.component';
import { InventoryReportComponent } from '../inventory-report/inventory-report.component';
import { SupplierReportComponent } from '../supplier-report/supplier-report.component';
import { ReportAnalysisService } from '@services/report-analysis.service';
import { OrderStatusCountDTO } from '@services/report-analysis.service';

@Component({
  selector: 'app-report-analysis',
  templateUrl: './report-analysis.component.html',
  styleUrls: ['./report-analysis.component.css']
})
export class ReportAnalysisComponent implements OnInit {
  startDate: string = '';
  endDate: string = '';
  selectedReport: string = '';
  reportTypes: string[] = ['Order Report', 'Inventory Report', 'Supplier Report'];

  reportGenerated: boolean = false;

  reportGenerationSelected : boolean = false;

  inventoryValue!: number;
  totalProducts!:number;
  totalSupplier!:number;
  productStatus!: any;
  orderStatus!: OrderStatusCountDTO[];
  purchaseStatus!: OrderStatusCountDTO[];

  @ViewChild('reportContainer', { read: ViewContainerRef }) reportContainer!: ViewContainerRef;

  constructor(private api: ReportAnalysisService) {}

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard() {
    forkJoin({
      inventoryValue: this.api.getInventoryValue(),
      totalSupplier:this.api.getTotalSuppliersCount(),
      totalProducts:this.api.getTotalProductsCount(),
      productStatus: this.api.getProductsCountByStatus(),
      orderStatus: this.api.getOrderCountByStatus(),
      purchaseStatus: this.api.getPurchaseOrderCountByStatus()
    }).subscribe(({ inventoryValue,totalSupplier, totalProducts,productStatus, orderStatus, purchaseStatus }) => {
      console.log({ inventoryValue, totalProducts,productStatus, orderStatus, purchaseStatus })
      this.inventoryValue = inventoryValue;
      this.totalSupplier= totalSupplier;
      this.totalProducts=totalProducts;
      this.productStatus = productStatus;
      this.orderStatus = orderStatus;
      this.purchaseStatus = purchaseStatus;
    });
  }

  generateReport() {
    if (!this.selectedReport || !this.startDate || !this.endDate) {
      alert('Please select a report type and date range.');
      return;
    }

    this.reportGenerated = true;
    this.reportContainer.clear();

    let componentType: any;
    switch (this.selectedReport) {
      case 'Order Report':
        componentType = OrderReportComponent;
        break;
      case 'Inventory Report':
        componentType = InventoryReportComponent;
        break;
      case 'Supplier Report':
        componentType = SupplierReportComponent;
        break;
    }

    if (componentType) {
      const componentRef: ComponentRef<any> = this.reportContainer.createComponent(componentType);
      componentRef.instance.startDate = this.startDate;
      componentRef.instance.endDate = this.endDate;
    }
  }
}
