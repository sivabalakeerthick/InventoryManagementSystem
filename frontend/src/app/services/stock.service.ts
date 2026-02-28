import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { delay, Observable } from 'rxjs';
import { Stock, StockStatus } from '@models/stock.model';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  private apiUrl : string = '';

  constructor(private http : HttpClient) {
    this.apiUrl = environment.apiUrl + "/stock";
  }

  getAllStocks(page : number = 0, size : number = 10 , categoryName : string , searchQuery : string , stockLevelStatus : string , sortOrder: string = "asc") : Observable<Stock[]>{

    const params: any = {
      page: page.toString(),
      size: size.toString(),
      sortOrder : sortOrder.toString()
    };

    if(categoryName.length > 0){
      params.categoryName = categoryName;
    }
    if(searchQuery.length > 0){
      params.searchQuery = searchQuery;
    }
    if (stockLevelStatus !== null && stockLevelStatus !== undefined) {
      params.stockLevelStatus = stockLevelStatus.toString();
    }

    return this.http.get<Stock[]>(this.apiUrl , {
      params
    });
  }

  getProductsInInventory():Observable<any[]>{
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  getProductsCount():Observable<any>{
    return this.http.get<any>(`${this.apiUrl}/totalProducts`);
  }

  getProductsCountByStatus(status : string) : Observable<any>{
    return this.http.get<any>(`${this.apiUrl}/getCountByStatus` , {
      params : {
        status
      }
    })
  }

    getLowStockProducts() : Observable<any[]>{
    return this.http.get<any[]>(`${this.apiUrl}/lowStockProducts`);
  }

}
