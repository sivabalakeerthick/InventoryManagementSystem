import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { Observable } from 'rxjs';
import { Order, OrderRequest, OrderStatus } from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrl : string = '';

  constructor(private http : HttpClient) {
    this.apiUrl = environment.apiUrl + "/order";
  }

  getAllOrders(page : number = 0, size : number = 10 , orderStatus : string , searchQuery : string) : Observable<Order[]>{
  
    
    return this.http.get<Order[]>(this.apiUrl , {
      params : {
        page,
        size,
        orderStatus,
        searchQuery
      }
    });
  }

  createOrder(order: Partial<OrderRequest>): Observable<Order> {
    console.log(order);
  
    return this.http.post<Order>(this.apiUrl, order);
  }

  updateOrder(id : number , updatedStatus : string) : Observable<Order>{
    return this.http.put<Order>(`${this.apiUrl}/${id}`, 
    {
      orderStatus : updatedStatus
    });
  }

  deleteOrder(id:number) : Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
