import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs';
import { environment } from '../../app/environments/environment';

export interface OrderStatusCountDTO {
  status: string;
  count: number;
}  

export interface StockInfoDTO {
  status: string;
  count: number;
}  

@Injectable({
  providedIn: 'root'
})
export class ReportAnalysisService {
  private apiUrl = `${environment.apiUrl}/analysis`;
  private stockUrl = `${environment.apiUrl}/stock`;

  constructor(private http: HttpClient) {}
  
  private formatDate(date: string): string {
    return new Date(date).toISOString().split('T')[0];
  }

  getTopExpensiveOrders(startDate: string, endDate: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/toporders?startDate=${this.formatDate(startDate)}&endDate=${this.formatDate(endDate)}`);
  }

  getTotalAmount(startDate: string, endDate: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/totalamount?startDate=${this.formatDate(startDate)}&endDate=${this.formatDate(endDate)}`);
  }

  getTotalOrders(startDate: string, endDate: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/totalorders?startDate=${this.formatDate(startDate)}&endDate=${this.formatDate(endDate)}`);
  }

  getTopExpensivePurchaseOrders(startDate: string, endDate: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/toppurchaseorders?startDate=${this.formatDate(startDate)}&endDate=${this.formatDate(endDate)}`);
  }

  getTotalPurchaseAmount(startDate: string, endDate: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/totalpurchaseamount?startDate=${this.formatDate(startDate)}&endDate=${this.formatDate(endDate)}`);
  }

  getTotalPurchaseOrders(startDate: string, endDate: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/totalpurchaseorders?startDate=${this.formatDate(startDate)}&endDate=${this.formatDate(endDate)}`);
  }

  getOrderProductQuantity(startDate: string, endDate: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/orderproductquantity?startDate=${this.formatDate(startDate)}&endDate=${this.formatDate(endDate)}`);
  }

  getPurchaseProductQuantity(startDate: string, endDate: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/purchaseproductquantity?startDate=${this.formatDate(startDate)}&endDate=${this.formatDate(endDate)}`);
  }

  getTotalPurchaseQuantity(startDate: string, endDate: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/totalpurchasequantity?startDate=${this.formatDate(startDate)}&endDate=${this.formatDate(endDate)}`);
  }

  getTotalOrderQuantity(startDate: string, endDate: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/totalorderquantity?startDate=${this.formatDate(startDate)}&endDate=${this.formatDate(endDate)}`);
  }

  getTopSuppliers(startDate: string, endDate: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/topsuppliers?startDate=${this.formatDate(startDate)}&endDate=${this.formatDate(endDate)}`);
  }


  getInventoryValue(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/inventoryvalue`);
  }

  getOrderCountByStatus(): Observable<OrderStatusCountDTO[]> {
    
    return this.http.get<OrderStatusCountDTO[]>(`${this.apiUrl}/countorderbystatus`);
  }

  getPurchaseOrderCountByStatus(): Observable<OrderStatusCountDTO[]> {
    return this.http.get<OrderStatusCountDTO[]>(`${this.apiUrl}/countpurchaseorderbystatus`);
  }

  getTotalProductsCount(): Observable<number> {
    return this.http.get<{totalProducts: number}>(`${this.stockUrl}/totalProducts`).pipe(map(res => res.totalProducts));
  }

  getProductsCountByStatus(): Observable<any> {
    return this.http.get<StockInfoDTO[]>(`${this.stockUrl}/getProductsCountByStatus`);
  }

  getTotalSuppliersCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/totalsuppliers`)
  }
  

  
  
}
