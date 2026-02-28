import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '@models/category.model';
import { environment } from '../environments/environment';
 
@Injectable({ providedIn: 'root' })
export class CategoryService {
  private baseUrl = `${environment.apiUrl}/categories`;
 
  constructor(private http: HttpClient) {}
 
  getAll(): Observable<Category[]> {
    return this.http.get<Category[]>(this.baseUrl);
  }
 
  create(category: { categoryName: string }): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/add` , {
      categoryName : category.categoryName
    });
  }
}
 