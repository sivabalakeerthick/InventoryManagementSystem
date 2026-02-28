import { Component, OnInit } from '@angular/core';
import { ProductService } from '@services/product.service';
import { CategoryService } from '@services/category.service';
import { Product } from '@models/product.model';
import { Category } from '@models/category.model';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms'; // Import reactive forms modules

import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit {
  
  productForm!: FormGroup;

  categories: Category[] = [];
  isEdit = false;
  showCategoryModal = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
    private categoryService: CategoryService,
    private toastrService : ToastrService,
    private fb: FormBuilder // Inject FormBuilder
  ) {}

  ngOnInit() {
    this.productForm = this.fb.group({
      productId: new FormControl(null),
      productName: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      unitPrice: new FormControl(0, [Validators.required, Validators.min(0.01)]),
      reOrderLevel: new FormControl(0, [Validators.required, Validators.min(0)]),
      categoryName: new FormControl('', Validators.required)
    });

    
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get("id"));
      if (id) {
        this.isEdit = true;
        this.productService.getById(id).subscribe(productData => {
          if (productData) {
            this.productForm.patchValue(productData);
          }
        }, error => {
          console.error('Error fetching product data:', error);
          this.router.navigate(['/admin/products']);
        });
      }
    });
    this.loadCategories();
  }
  loadCategories() {
    this.categoryService.getAll().subscribe(data => {
      this.categories = data;
    }, error => {
      console.error('Error loading categories:', error);
    });
  }

  openAddCategory() {
    this.showCategoryModal = true;
  }
  onCategoryAdded(newCategory: string) {
    this.showCategoryModal = false;
    this.loadCategories();
    setTimeout(() => {
      this.productForm.get('categoryName')?.setValue(newCategory);
    }, 100);
  }

  onSubmit() {
    if (this.productForm.valid) {
      const product: Product = this.productForm.value;

      if (this.isEdit) {
        this.productService.update(product.productId!, product).subscribe(() => {
          this.toastrService.success("Product Updated Successfully" , "Success !");
          
          setTimeout(() => this.router.navigate(['/admin/products']),2000);
        }, error => {
          this.toastrService.warning("Please fill the Product Details" , "Product Not Updated");
        });
      } else {
        this.productService.create(product).subscribe(() => {
              this.toastrService.success("Product Added Successfully" , "Success !");
              setTimeout(() => this.router.navigate(['/admin/products']),2000);
        }, error => {
          this.toastrService.warning("Please fill the Product Details" , "Failed Adding Product")
        });
      }
    } else {
      this.productForm.markAllAsTouched();
      this.toastrService.warning("Please fill the Product Details" , "Failed Adding Product")
    }
  }
}
