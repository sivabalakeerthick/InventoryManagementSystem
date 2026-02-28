import { Component, OnInit } from '@angular/core';
import { PurchaseOrder, Page } from '../../models/purchase-order';
import { PurchaseOrderService } from '../../services/purchase-order.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { debounceTime, Subject } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-purchase-order-list',
  templateUrl: './purchase-order-list.component.html',
  styleUrls: ['./purchase-order-list.component.css']
})
export class PurchaseOrderListComponent implements OnInit {
  purchaseOrders: PurchaseOrder[] = [];
  errorMessage: string = '';
  showWarning: boolean = false;
  expandedOrders: Set<number> = new Set(); 

  currentPage: number = 0;
  itemsPerPage: number = 2;
  totalElements: number = 0;
  totalPages: number = 0;

  selectedStatusFilter: string = '';
  searchSupplierName: string = '';
  private searchSubject = new Subject<string>();

  constructor(private purchaseOrderService: PurchaseOrderService, private router: Router , private toastrService : ToastrService) {}

  ngOnInit(): void {
    this.loadPurchaseOrders();

    this.searchSubject.pipe(
      debounceTime(300)
    ).subscribe(() => {
      this.currentPage = 0;
      this.loadPurchaseOrders();
    });
  }

  loadPurchaseOrders(): void {
    this.errorMessage = '';

    this.purchaseOrderService.getAllPurchaseOrders(
      this.currentPage,
      this.itemsPerPage,
      this.selectedStatusFilter,
      this.searchSupplierName
    ).subscribe({
      next: (data: Page<PurchaseOrder>) => {
        this.purchaseOrders = data.content;
        this.totalElements = data.totalElements;
        this.totalPages = data.totalPages;
        this.currentPage = data.number;
      },
      error: (err: HttpErrorResponse) => {
        this.errorMessage = 'Failed to load purchase orders. Please try again later.';
        console.error('Error fetching purchase orders:', err);
        this.purchaseOrders = [];
        this.totalElements = 0;
        this.totalPages = 0;
      }
    });
  }

  applyStatusFilter(): void {
    this.currentPage = 0;
    this.loadPurchaseOrders();
  }

  onSearchInputChange(): void {
    this.searchSubject.next(this.searchSupplierName);
  }

  getDisplayedPurchaseOrders(): PurchaseOrder[] {
    return this.purchaseOrders;
  }

  clearSearch(): void {
    this.searchSupplierName = '';
    this.currentPage = 0;
    this.loadPurchaseOrders();
  }

  viewPurchaseOrder(id: number | undefined): void {
    if (id === undefined) return; 
    if (this.expandedOrders.has(id)) {
      this.expandedOrders.delete(id);
    } else {
      this.expandedOrders.add(id);
    }
  }

  editPurchaseOrder(id: number | undefined): void {
    if (id !== undefined) {
      this.router.navigate(['/admin/purchase-orders/edit', id]);
    }
  }

  deletePurchaseOrder(id: number | undefined): void {
    if (id === undefined) {
      this.errorMessage = 'Cannot delete: Purchase Order ID is missing.';
      return;
    }

    if (confirm('Are you sure you want to delete this purchase order?')) {
      this.purchaseOrderService.deletePurchaseOrder(id).subscribe({
        next: () => {
          this.toastrService.success("Purchase Order Deleted Successfully" , "Success !");
          setTimeout(() => {this.loadPurchaseOrders()}, 2000);
          console.log('Purchase order deleted successfully!');
          if (this.purchaseOrders.length === 1 && this.currentPage > 0) {
            this.currentPage--;
          }
        },
        error: (err: HttpErrorResponse) => {
          this.toastrService.error("Failed Deleting Purchase Order" , "Error !");
          console.error('Error deleting purchase order:', err);
        }
      });
    }
  }

  onPageChange(newPage: number): void {
    this.currentPage = newPage;
    this.loadPurchaseOrders();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
