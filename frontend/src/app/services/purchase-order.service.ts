import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PurchaseOrder, PurchaseOrderUpdate, Page } from '../models/purchase-order';
import { environment } from '../../app/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PurchaseOrderService {
  private apiUrl = `${environment.apiUrl}/purchaseOrder`; 

  constructor(private http: HttpClient) { }

  getAllPurchaseOrders(
    page: number,
    size: number,
    statusFilter?: string,
    searchSupplierName?: string
  ): Observable<Page<PurchaseOrder>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (statusFilter) {
      params = params.set('orderStatus', statusFilter);
    }

    if (searchSupplierName && searchSupplierName.trim().length > 0) {
      params = params.set('searchQuery', searchSupplierName.trim());
    }

    return this.http.get<Page<PurchaseOrder>>(this.apiUrl, { params: params });
  }

  getPurchaseOrderById(id: number): Observable<PurchaseOrder> { 
    return this.http.get<PurchaseOrder>(`${this.apiUrl}/${id}`);
  }

  createPurchaseOrder(purchaseOrder: PurchaseOrder): Observable<PurchaseOrder> { 
    return this.http.post<PurchaseOrder>(this.apiUrl, purchaseOrder);
  }

  updatePurchaseOrder(id: number, updateData: PurchaseOrderUpdate): Observable<PurchaseOrder> { 
    return this.http.put<PurchaseOrder>(`${this.apiUrl}/${id}`, updateData);
  }

  deletePurchaseOrder(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }
}