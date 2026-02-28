import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Supplier, Page } from '../models/supplier';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {
  private apiUrl = `${environment.apiUrl}/supplier`;

  constructor(private http: HttpClient) { }

  getAllSuppliers(
    page: number,
    size: number,
    sortBy: string,
    sortOrder: string,
    searchQuery: string
  ): Observable<Page<Supplier>> { 
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy)
      .set('sortOrder', sortOrder);

    if (searchQuery && searchQuery.trim().length > 0) {
      params = params.set('searchQuery', searchQuery.trim());
    }

    return this.http.get<Page<Supplier>>(this.apiUrl, { params });
  }

  getAllSuppliersList(): Observable<Supplier[]> {
    const largePageSize = 1000;
    let params = new HttpParams()
      .set('page', '0')
      .set('size', largePageSize.toString())
      .set('sortBy', 'name')
      .set('sortOrder', 'asc');

    return this.http.get<Page<Supplier>>(this.apiUrl, { params }).pipe(map(page => page.content));
  }

  getSupplierById(id: number): Observable<Supplier> {
    return this.http.get<Supplier>(`${this.apiUrl}/${id}`);
  }

  addSupplier(supplier: Supplier): Observable<Supplier> { 
    return this.http.post<Supplier>(this.apiUrl, supplier);
  }

  updateSupplier(id: number, supplier: Supplier): Observable<Supplier> {
    return this.http.put<Supplier>(`${this.apiUrl}/${id}`, supplier);
  }

  deleteSupplier(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }
}