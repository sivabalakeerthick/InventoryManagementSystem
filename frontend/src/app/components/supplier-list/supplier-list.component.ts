import { Component, OnInit } from '@angular/core';
import { Supplier, Page } from '../../models/supplier'; 
import { SupplierService } from '../../services/supplier.service';
import { NavigationExtras, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-supplier-list',
  templateUrl: './supplier-list.component.html',
  styleUrls: ['./supplier-list.component.css']
})
export class SupplierListComponent implements OnInit {
  suppliers: Supplier[] = []; 
  errorMessage: string = '';

  currentPage: number = 0;
  itemsPerPage: number = 5;
  totalElements: number = 0;
  totalPages: number = 0;
  sortBy: string = 'supplierId';
  sortOrder: string = 'asc';

  searchQuery: string = '';
  private searchSubject: Subject<string> = new Subject<string>();

  displayedPageNumbers: number[] = [];

  constructor(private supplierService: SupplierService, private router: Router , private toastrService : ToastrService) { }

  ngOnInit(): void {
    this.loadSuppliers();

    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras?.state as { message?: string };

    if (state?.message) {
      this.errorMessage = state.message;
      setTimeout(() => this.errorMessage = '', 3000);
    }

    this.searchSubject.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(() => {
      this.currentPage = 0;
      this.loadSuppliers();
    });
  }

  loadSuppliers(): void {
    this.errorMessage = '';

    this.supplierService.getAllSuppliers(
      this.currentPage,
      this.itemsPerPage,
      this.sortBy,
      this.sortOrder,
      this.searchQuery
    ).subscribe({
      next: (data: Page<Supplier>) => { 
        console.log(data); 
        this.suppliers = data.content;
        this.totalElements = data.totalElements;
        this.totalPages = data.totalPages;
        this.currentPage = data.number;

      },
      error: (err: HttpErrorResponse) => {
        this.errorMessage = 'Failed to load suppliers. Please try again later.';
        console.error('Error fetching suppliers:', err);
        this.suppliers = [];
        this.totalElements = 0;
        this.totalPages = 0;
        this.displayedPageNumbers = [];
      }
    });
  }

  onSearchInput(): void {
    this.searchSubject.next(this.searchQuery);
  }

  clearSearch(): void {
    this.searchQuery = '';
    this.errorMessage = '';
    this.currentPage = 0;
    this.loadSuppliers();
  }

  onPageChange(newPage: number): void {
    this.currentPage = newPage;
    this.loadSuppliers();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  editSupplier(id: number | undefined): void { 
    if (id !== undefined) {
      this.router.navigate(['/admin/suppliers/edit', id]);
    }
  }

  deleteSupplier(id: number | undefined): void {
    if (id === undefined) {
      this.errorMessage = 'Cannot delete: Supplier ID is missing.';
      return;
    }

    if (confirm('Are you sure you want to delete this supplier? This action cannot be undone.')) {
      this.supplierService.deleteSupplier(id).subscribe({
        next: () => {
            this.toastrService.success("Supplier Deleted Successfully" , "Success !");
            setTimeout(() => {this.loadSuppliers()}, 2000);

          console.log('Supplier deleted successfully!');

          if (this.suppliers.length === 1 && this.currentPage > 0) {
            this.currentPage--;
          }
          setTimeout(() => {
            this.loadSuppliers();
            this.errorMessage = '';
          }, 2000);
        },
        error: (err) => {
          this.toastrService.error("Failed Deleting Supplier" , "Error !");
          console.error('Error deleting supplier:', err);
        }
      });
    }
  }
}
