import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from '@models/category.model';
import { CategoryService } from '@services/category.service';
import { Order, OrderStatus } from 'src/app/models/order.model';
import { AuthService } from 'src/app/services/auth.service';
import { OrderService } from 'src/app/services/order.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  OrderStatus = OrderStatus;
  orders : Order[] = [];
  expandedOrders: Set<number> = new Set();
  categories: Category[] = [];
  selectedStatus : string = "";
  searchQuery : string = "";
  orderStatusValues: OrderStatus[] = [];
  totalOrders : number = 0;
  currentPage: number = 0;
  pageSize: number = 2;
  totalPages: number = 1;


  constructor(private orderService : OrderService , private router : Router , private authService : AuthService , private categoryService : CategoryService , private toastrService : ToastrService){}

  ngOnInit(): void {
    this.getAllOrders();
    this.orderStatusValues = Object.values(OrderStatus).slice(0,3);
  }

  getCategories(){
    this.categoryService.getAll().subscribe(
      (data) => {
        this.categories = data;
      },
      (err) => {
        console.log(err);
        
      }
    )
  }

  getAllOrders(){
    this.orderService.getAllOrders(this.currentPage , this.pageSize , this.selectedStatus , this.searchQuery).subscribe(
      (data : any) => {
        this.totalOrders = data.totalElements;
        this.orders = data.content;
        this.totalPages = Math.max(1 , data.totalPages);
      }
    );
  }

  getOrderStatusLabel(status: OrderStatus): string {
    switch (status) {
      case OrderStatus.Pending:
        return 'Pending';
      case OrderStatus.Shipped:
        return 'Shipped';
      case OrderStatus.Delivered:
        return 'Delivered';
      case OrderStatus.Cancelled:
        return 'Cancelled';
      default:
        return 'Unknown Status';
    }
  }

  updateOrder(order : Order , updatedStatus : string): void{
    this.orderService.updateOrder(order.orderId , updatedStatus).subscribe({
      next : (updatedOrder) => {
        this.toastrService.success("Order Status Updated Successfully" , "Success !");
        order.orderStatus = updatedOrder.orderStatus;
      },
      error : err => {
        this.toastrService.error("Invalid Order Status" , "Failed Updating Order");
        console.log(err);
      }
  });
  }

  deleteOrder(id : number){
    console.log(id);
    this.orderService.deleteOrder(id).subscribe({
      next: (res) => {
        this.toastrService.success("Order Deleted Successfully" , "Success !");
        this.orders = this.orders.filter(order => order.orderId !== id);
      },
      error: (err) => {
        this.toastrService.error("Failed Deleting Order" , "Error !");
        console.error(`Delete failed:`, err);
      }
    });
  }

  toggle(index: number): void {
    if (this.expandedOrders.has(index)) {
      this.expandedOrders.delete(index);
    } else {
      this.expandedOrders.add(index);
    }
  }
  goToCreateOrder() {
    this.router.navigate([`${this.authService.getUserRole()?.toLocaleLowerCase()}/orders/create`]);
  }

  
  onPageChange(newPage: number): void {
    this.currentPage = newPage;
    this.getAllOrders();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  filterOrders(){
    this.currentPage = 0;
    this.getAllOrders();
  }
}
