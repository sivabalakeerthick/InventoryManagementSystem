import { Component, OnDestroy, OnInit } from '@angular/core';
import { ProductService } from '@services/product.service';
import { CategoryService } from '@services/category.service';
import { Product } from '@models/product.model';
import { Category } from '@models/category.model';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '@services/auth.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  filteredProducts: Product[] = [];
  categories: Category[] = [];
  searchQuery: string = '';
  selectedCategory: string = '';
  totalProducts : number = 0;
  page: number = 0;
  size: number = 5;
  sortBy: string = "productName";
  sortOrder: string = "asc";
  hasMore: boolean = true;

  // New property to hold the product whose details are currently visible
  selectedProductForDetails: Product | null = null;


  constructor(private productService: ProductService, private categoryService: CategoryService,private router:Router , private toastrService : ToastrService , private authService : AuthService) {}

  ngOnInit(): void {
    this.getProducts();
    this.getCategories();
  }

  getProducts(): void {
    this.selectedProductForDetails = null;

    this.productService.getProducts(this.page, this.size, this.sortBy, this.sortOrder, this.selectedCategory, this.searchQuery).subscribe({
      next: data => {
        this.products = data.content;
        this.totalProducts = data.totalElements;
        this.filteredProducts = this.products;
        this.hasMore = !data.last;
      },
      error: err => {
        console.error('Error fetching products:', err);
      }
    });
  }

  getCategories(): void {
    this.categoryService.getAll().subscribe({
      next: data => {
        this.categories = data;
      },
      error: err => {
        console.error('Error fetching categories:', err);
      }
    });
  }

  isAdmin():boolean{
    return this.authService.getUserRole() === 'ADMIN';
  }

  filterProducts(): void {
    this.page = 0;
    this.getProducts();
  }
  openCreateForm(): void {
    this.router.navigate(['/admin/products/add']);
  }

  editProduct(product: Product): void {
    if (product.productId) {
      this.router.navigate(['/admin/products/edit', product.productId]);
    } else {
      console.warn('Product ID is undefined, cannot navigate to edit form.');
    }
  }

  deleteProduct(productId: number | undefined): void {
    if (productId === undefined || productId === null) {
      console.warn('Cannot delete product: Product ID is undefined or null.');
      return;
    }

    if (confirm("Are you sure you want to delete this product?")) {
      this.productService.delete(productId).subscribe({
        next: () => {
          this.toastrService.success("Product Deleted Successfully" , "Success !");
          this.filteredProducts = this.filteredProducts.filter(product => product.productId !== productId);
          if (this.selectedProductForDetails?.productId === productId) {
            this.selectedProductForDetails = null;
          }
          console.log(`Product with ID ${productId} deleted successfully.`);
        },
        error:(err)=>
          {
            this.toastrService.error("Failed Deleting Product" , "Error !");
            console.error('Error deleting product:',err);
          }
      });
    }
  }

  onPageChange(newPage: number): void {
    this.page = newPage;
    this.getProducts();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  sortProducts(event: Event): void {
    const target = event.target as HTMLSelectElement;
    this.sortBy = target.value;
    this.sortOrder = (this.sortBy === target.value && this.sortOrder === "asc") ? "desc" : "asc";
    this.page = 0;
    this.getProducts();
  }

  toggleDetails(product: Product): void {
    if (this.selectedProductForDetails?.productId === product.productId) {
      this.selectedProductForDetails = null;
    } else {
      this.selectedProductForDetails = product;
    }
  }
}
