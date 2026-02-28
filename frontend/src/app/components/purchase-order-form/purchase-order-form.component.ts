import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PurchaseOrderService } from '../../services/purchase-order.service';
import { SupplierService } from '../../services/supplier.service';
import { PurchaseOrder, PurchaseOrderItem, PurchaseOrderUpdate } from '../../models/purchase-order';
import { Supplier } from '../../models/supplier';
import { FormGroup, FormControl, FormArray, Validators, ValidatorFn, AbstractControl } from '@angular/forms';
import { Product } from '@models/product.model';
import { ProductService } from '@services/product.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-purchase-order-form',
  templateUrl: './purchase-order-form.component.html',
  styleUrls: ['./purchase-order-form.component.css']
})
export class PurchaseOrderFormComponent implements OnInit {
  purchaseOrderForm!: FormGroup;

  isEditMode: boolean = false;
  purchaseOrderId: number | null = null;
  errorMessage: string = '';

  suppliers: Supplier[] = [];
  availableOrderStatuses: string[] = ['PENDING', 'SHIPPED', 'DELIVERED', 'CANCELLED'];
  availablePaymentTypes: string[] = ['Cash', 'Bank Transfer'];
  products: Product[] = [];

  constructor(
    private purchaseOrderService: PurchaseOrderService,
    private supplierService: SupplierService,
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    private toastrService : ToastrService
  ) { }

  ngOnInit(): void {
    this.purchaseOrderId = this.route.snapshot.params['id'];
    this.isEditMode = !!this.purchaseOrderId; 

    if (this.isEditMode) {
      this.purchaseOrderForm = new FormGroup({
        orderStatus: new FormControl(null, Validators.required)
      });
    } else {
      this.purchaseOrderForm = new FormGroup({
        supplierId: new FormControl(null, Validators.required),
        paymentType: new FormControl(null, Validators.required),
        purchaseOrderItems: new FormArray([], this.minimumItemsValidator(1))
      });
      this.addItem(); 
    }

    this.loadSuppliers();
    this.fetchProducts();

    if (this.isEditMode && this.purchaseOrderId) {
      
      this.purchaseOrderService.getPurchaseOrderById(this.purchaseOrderId).subscribe({
        next: (data: PurchaseOrder) => {
          this.purchaseOrderForm.patchValue({
            orderStatus: data.orderStatus
          });
        },
        error: (err) => {
          this.toastrService.error("Failed Fetching Records" , "Error");
          console.error('Error fetching purchase order:', err);
        }
      });
    }
  }

  minimumItemsValidator(min: number): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const formArray = control as FormArray;
      return formArray.length >= min ? null : { 'minItems': { value: formArray.length, required: min } };
    };
  }

  fetchProducts(): void {
    this.productService.getAll().subscribe({
      next: (res: any) => { 
        this.products = res.content || res; 
      },
      error: (err: any) => {
        this.errorMessage = 'Failed to load products.';
        console.error('Failed to load products', err);
      }
    });
  }

  loadSuppliers(): void {
    this.supplierService.getAllSuppliersList().subscribe({
      next: (data: Supplier[]) => { 
        this.suppliers = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load suppliers.';
        console.error('Error fetching suppliers:', err);
      }
    });
  }

  get purchaseOrderItems(): FormArray {
    return this.purchaseOrderForm.get('purchaseOrderItems') as FormArray;
  }

  get supplierIdControl() {
    return this.purchaseOrderForm.get('supplierId');
  }

  get paymentTypeControl() {
    return this.purchaseOrderForm.get('paymentType');
  }

  get orderStatusControl() {
    return this.purchaseOrderForm.get('orderStatus');
  }

  private createPurchaseOrderItemFormGroup(): FormGroup {
    return new FormGroup({
      productId: new FormControl(null, Validators.required),
      quantity: new FormControl(null, [Validators.required, Validators.min(1)])
    });
  }

  addItem(): void {
    this.purchaseOrderItems.push(this.createPurchaseOrderItemFormGroup());
  }

  removeItem(index: number): void {
    this.purchaseOrderItems.removeAt(index);
    if (!this.isEditMode && this.purchaseOrderItems.length === 0) {
      this.addItem();
    }
  }

  onSubmit(): void {
    this.purchaseOrderForm.markAllAsTouched();

    if (this.purchaseOrderForm.invalid) {
      this.toastrService.warning("Please fill the Purchase Order Details" , "Failed Creating Purchase Order");
      return;
    }

    if (this.isEditMode && this.purchaseOrderId) {
      
      const updateData: PurchaseOrderUpdate = {
        orderStatus: this.purchaseOrderForm.get('orderStatus')?.value,
        
      };
      this.purchaseOrderService.updatePurchaseOrder(this.purchaseOrderId, updateData).subscribe({
        next: () => {
          this.toastrService.success("Purchase Order updated successfully!" , "Success !")
          setTimeout(() => this.router.navigate(['/admin/purchase-orders']),2000);
        },
        error: (err) => {
          this.toastrService.warning("Invalid Order Status" , "Failed Updating Purchase Order");
          console.error('Error updating purchase order:', err);
        }
      });
    } else {
    
      const formValue = this.purchaseOrderForm.value;

      const newPurchaseOrder: PurchaseOrder = {
        supplierId: formValue.supplierId,
        paymentType: formValue.paymentType,
        purchaseOrderItemList: formValue.purchaseOrderItems as PurchaseOrderItem[]
      };

      this.purchaseOrderService.createPurchaseOrder(newPurchaseOrder).subscribe({
        next: () => {
          this.toastrService.success("Purchase Order Created successfully!" , "Success !")
          setTimeout(() => this.router.navigate(['/admin/purchase-orders']),2000);
        },
        error: (err) => {
          this.toastrService.warning("Please fill the Purchase Order Details" , "Failed Creating Purchase Order");
          console.error('Error creating purchase order:', err);
        }
      });
    }
  }
}
