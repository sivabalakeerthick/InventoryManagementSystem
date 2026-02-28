import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '@models/product.model';
import { environment } from '../environments/environment';
 
@Injectable({ providedIn: 'root' })
export class ProductService {
private baseUrl = `${environment.apiUrl}/product`;
 
  constructor(private http: HttpClient) {}
 
  getProducts(page: number = 0, size: number = 10, sortBy: string = "productName", sortOrder: string = "asc" , categoryName : string, searchQuery : string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/filterProducts?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}&categoryName=${categoryName}&searchQuery=${searchQuery}`);
  }

  getAll(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/getAll`);
  }


  getByCategory(categoryName: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/${categoryName}`);
  }
 
  getById(productId: number): Observable<Product> {
    return this.http.get<Product>(`${this.baseUrl}/get/${productId}`);
  }
 
  create(product: Product): Observable<Product> {
return this.http.post<Product>(`${this.baseUrl}/add`, product);
  }
 
  update(productId: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.baseUrl}/update/${productId}`, product);
  }
  delete(productId: number | undefined): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${productId}`);
  }
  getProductsByCategory(categoryName : string) : Observable<Product>{
    return this.http.get<Product>(`${this.baseUrl}/by-category?categoryName=${categoryName}`);
  }
}