import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { OrderService } from 'src/app/services/order.service';
import { Product } from 'src/app/models/product.model';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CustomValidators } from 'src/app/validators/custom-validators';
import { Router } from '@angular/router';
import { StockService } from '@services/stock.service';
@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.css']
})
export class OrderFormComponent implements OnInit {

  orderForm !: FormGroup;
  products: Product[] = []; // This should be your full list of all available products

  constructor(
    private productService: ProductService,
    private stockService: StockService,
    private orderService: OrderService,
    private fb: FormBuilder,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.orderForm = this.fb.group({
      customerName: ['', [Validators.required]],
      customerEmail: ['', [Validators.required, Validators.email]],
      orderItems: this.fb.array(
        [this.createOrderItemFormGroup()]
      )
    });
    this.fetchProducts();
  }

  get orderItemsFormArray(): FormArray {
    return this.orderForm.get('orderItems') as FormArray;
  }

  getItemControl(itemControl: AbstractControl, controlName: string): FormControl {
    const group = itemControl as FormGroup;
    const control = group.get(controlName);
    return control as FormControl;
  }

  createOrderItemFormGroup(): FormGroup {
    const validators = [Validators.required, CustomValidators.selectionRequired(0)];
    return this.fb.group({
      productId: [null, validators],
      quantity: [1, [Validators.required, Validators.min(1)]]
    });
  }

  fetchProducts() {
    this.stockService.getProductsInInventory().subscribe({
      next: (inventoryProducts) => {
        this.productService.getAll().subscribe({
          next: (allProductsData: Product[]) => {
            const productsInInventoryIds = new Set(inventoryProducts.map(p => p.productId));
            this.products = allProductsData.filter(p => productsInInventoryIds.has(p.productId));
          },
          error: (err: any) => console.error('Failed to load all products', err)
        });
      },
      error: (err) => {
        console.error('Failed to load products in inventory', err);
      }
    });
  }

  addOrderItem() {
    this.orderItemsFormArray.push(this.createOrderItemFormGroup());
  }

  removeOrderItem(index: number) {
    this.orderItemsFormArray.removeAt(index);

    if (this.orderItemsFormArray.length === 0) {
      this.addOrderItem();
    }
  }

  getAvailableProductsForItem(currentItemControl: AbstractControl): Product[] {
    const selectedProductIds = new Set<number>();

    this.orderItemsFormArray.controls.forEach(itemControl => {
      const productId = itemControl.get('productId')?.value;
        selectedProductIds.add(Number(productId));
    });

    console.log(selectedProductIds);
    

    const currentProductId = currentItemControl.get('productId')?.value;

    let filteredProducts = this.products.filter(product =>
      typeof product.productId === 'number' && !selectedProductIds.has(product.productId)
    );


      const selectedProductInThisItem = this.products.find(p => p.productId === Number(currentProductId));
      if (selectedProductInThisItem && !filteredProducts.some(p => p.productId === Number(currentProductId))) {
        filteredProducts.push(selectedProductInThisItem);
      }


    return filteredProducts.sort((a, b) => a.productName.localeCompare(b.productName));
  }

  decreaseQuantity(index: number): void {
    const itemGroup = this.orderItemsFormArray.at(index) as FormGroup;
    const currentQuantity = itemGroup.get('quantity')?.value;
    if (currentQuantity && currentQuantity > 1) {
      itemGroup.get('quantity')?.setValue(currentQuantity - 1);
    }
  }

  increaseQuantity(index: number): void {
    const itemGroup = this.orderItemsFormArray.at(index) as FormGroup;
    const currentQuantity = itemGroup.get('quantity')?.value;
    if (currentQuantity !== null && currentQuantity !== undefined) {
      itemGroup.get('quantity')?.setValue(currentQuantity + 1);
    } else {
      itemGroup.get('quantity')?.setValue(1);
    }
  }

  getOrderItemGroup(index: number): FormGroup {
    return this.orderItemsFormArray.at(index) as FormGroup;
  }

  getFormGroupControl(abstractControl: AbstractControl): FormGroup {
    return abstractControl as FormGroup;
  }

  getProductName(productId: number): string {
    const product = this.products.find(p => p.productId === +productId);
    return product ? product.productName : '';
  }

  private markAllAsTouched(formGroup: FormGroup | FormArray): void {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup || control instanceof FormArray) {
        this.markAllAsTouched(control);
      }
    });
  }

  submitOrder() {
    if (this.orderForm.invalid) {
      this.markAllAsTouched(this.orderForm);
      this.toastrService.warning("Please fill the Order details", "Order Cannot be Created !");
      return;
    }

    this.orderService.createOrder(this.orderForm.value).subscribe({
      next: () => {
        this.toastrService.success("Order Created Successfully", "Success!");
        this.orderForm.reset();
        setTimeout(() => this.router.navigate(['/admin/orders']), 2000);
        this.resetForm();
      },
      error: (err) => {
        console.log(err);
        this.toastrService.error("Failed to create order.", "Error!");
      }
    });
  }

  itemControlHasError(itemGroup: AbstractControl, controlName: string, errorName: string): boolean {
    const control = itemGroup.get(controlName);
    return !!(control && control.hasError(errorName) && (control.touched || control.dirty));
  }

  itemControlIsInvalid(itemGroup: AbstractControl, controlName: string): boolean {
    const control = itemGroup.get(controlName);
    return !!(control && control.invalid && (control.touched || control.dirty));
  }

  resetForm() {
    this.orderForm.reset();
    this.orderItemsFormArray.clear();
    this.addOrderItem();
  }
}