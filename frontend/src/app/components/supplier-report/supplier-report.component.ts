import { Component, Input } from '@angular/core';
import { ReportAnalysisService } from 'src/app/services/report-analysis.service';

@Component({
  selector: 'app-supplier-report',
  templateUrl: './supplier-report.component.html',
  styleUrls: ['./supplier-report.component.css']
})
export class SupplierReportComponent {
  @Input() startDate!: string;
  @Input() endDate!: string;
  topSuppliers: any[] = [];
  statusOrders: any[] = [];
  totalPurchaseQuantity: number = 0;
  totalPurchaseAmount: number = 0;
  totalPurchaseOrder: number = 0;

  constructor(private reportService: ReportAnalysisService) {}

  ngOnInit() {
    this.fetchSupplierReport();
  }

  fetchSupplierReport() {
    this.reportService.getTopSuppliers(this.startDate, this.endDate).subscribe(data => {
      this.topSuppliers = data;
    });

    this.reportService.getTotalPurchaseQuantity(this.startDate, this.endDate).subscribe(count => {
      this.totalPurchaseQuantity = count;
    });

    this.reportService.getTotalPurchaseAmount(this.startDate, this.endDate).subscribe(amount => {
      this.totalPurchaseAmount = amount;
    });

    this.reportService.getTotalPurchaseOrders(this.startDate, this.endDate).subscribe(count => {
      this.totalPurchaseOrder = count;
    });
  
  }
}
