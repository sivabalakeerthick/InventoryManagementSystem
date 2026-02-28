import { Component, Input } from '@angular/core';
import { ReportAnalysisService } from 'src/app/services/report-analysis.service';

@Component({
  selector: 'app-inventory-report',
  templateUrl: './inventory-report.component.html',
  styleUrls: ['./inventory-report.component.css']
})
export class InventoryReportComponent {
  @Input() startDate!: string;
  @Input() endDate!: string;
  totalAmount: number = 0;
  totalInventoryValue: number = 0;
  orderProductQuantity: any[] = [];
  purchaseProductQuantity: any[] = [];
  totalOrderQuantity: number=0;
  totalPurchaseQuantity: number = 0;

  constructor(private reportService: ReportAnalysisService) {}

  ngOnInit() {
    this.fetchInventoryReport();
  }

  fetchInventoryReport() {

    
    this.reportService.getTotalAmount(this.startDate, this.endDate).subscribe(amount => {
      this.totalAmount = amount;
    });


    this.reportService.getOrderProductQuantity(this.startDate, this.endDate).subscribe(data => {
      this.orderProductQuantity = data;
    });

    this.reportService.getPurchaseProductQuantity(this.startDate, this.endDate).subscribe(data => {
      this.purchaseProductQuantity = data;
    });

    this.reportService.getTotalOrderQuantity(this.startDate, this.endDate).subscribe(count => {
      this.totalOrderQuantity = count;
    });

    this.reportService.getTotalPurchaseQuantity(this.startDate, this.endDate).subscribe(count => {
      this.totalPurchaseQuantity = count;
    });


  }
}
