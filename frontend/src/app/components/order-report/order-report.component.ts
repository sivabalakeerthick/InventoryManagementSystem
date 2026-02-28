import { Component, Input } from '@angular/core';
import { ReportAnalysisService } from 'src/app/services/report-analysis.service';

@Component({
  selector: 'app-order-report',
  templateUrl: './order-report.component.html',
  styleUrls: ['./order-report.component.css']
})
export class OrderReportComponent {
  @Input() startDate!: string;
  @Input() endDate!: string;
  topOrders: any[] = [];
  statusOrders: any[] = [];
  totalOrders: number = 0;
  totalAmount: number=0;
  totalOrderQuantity: number=0;

  constructor(private reportService: ReportAnalysisService) {}

  ngOnInit() {
    this.fetchOrderReport();
  }

  fetchOrderReport() {

    
    this.reportService.getTopExpensiveOrders(this.startDate, this.endDate).subscribe(data => {
      console.log(data);
      
      this.topOrders = data;
    });

    this.reportService.getTotalOrders(this.startDate, this.endDate).subscribe(count => {
      this.totalOrders = count;
    });

    this.reportService.getTotalAmount(this.startDate, this.endDate).subscribe(amount => {
      this.totalAmount = amount;
    });

    this.reportService.getTotalOrderQuantity(this.startDate, this.endDate).subscribe(count => {
      this.totalOrderQuantity = count;
    });
  }
}
