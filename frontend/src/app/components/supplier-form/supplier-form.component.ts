import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SupplierService } from '../../services/supplier.service';
import { Supplier } from '../../models/supplier'; 
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-supplier-form',
  templateUrl: './supplier-form.component.html',
  styleUrls: ['./supplier-form.component.css']
})
export class SupplierFormComponent implements OnInit {
  supplierForm!: FormGroup; 

  isEditMode: boolean = false;
  supplierId: number | null = null;
  errorMessage: string = '';

  constructor(
    private supplierService: SupplierService,
    private route: ActivatedRoute,
    private router: Router,
    private toastrService : ToastrService
  ) { }

  ngOnInit(): void {
    this.supplierForm = new FormGroup({
      name: new FormControl('',[Validators.required,Validators.pattern('^[a-zA-Z ]+$')]),
      contact: new FormControl('', [
        Validators.required,
        Validators.pattern('[0-9]{10}'),
        Validators.minLength(10),
        Validators.maxLength(10)
      ])
    });

    this.supplierId = this.route.snapshot.params['id'];
    if (this.supplierId) {
      this.isEditMode = true;
      this.supplierService.getSupplierById(this.supplierId).subscribe({
        next: (data: Supplier) => { 
          this.supplierForm.patchValue({
            name: data.name,
            contact: data.contact
          });
        },
        error: (err) => {
          this.errorMessage = 'Failed to load supplier for editing.';
          console.error('Error fetching supplier:', err);
        }
      });
    }
  }

  get nameControl() {
    return this.supplierForm.get('name');
  }

  get contactControl() {
    return this.supplierForm.get('contact');
  }

  onSubmit(): void {
    this.supplierForm.markAllAsTouched(); 

    if (this.supplierForm.invalid) {
            this.toastrService.warning("Please fill the Supplier Details" , "Supplier Cannot be Created");
      return;
    }

    const supplierData: Supplier = this.supplierForm.value;

    if (this.isEditMode && this.supplierId) {
      this.supplierService.updateSupplier(this.supplierId, supplierData).subscribe({
        next: () => {
          this.toastrService.success("Supplier Updated Successfully" , "Success !");
          setTimeout(() => this.router.navigate(['/admin/suppliers']),2000);
        },
        error: (err) => {
          this.toastrService.error("Please fill the Supplier Details" , "Failed Updating Supplier!");
          console.error('Error updating supplier:', err);
        }
      });
    } else {
      this.supplierService.addSupplier(supplierData).subscribe({
        next: () => {
          this.toastrService.success("Supplier Added Successfully" , "Success !");
          setTimeout(() => this.router.navigate(['/admin/suppliers']),2000);
        },
        error: (err) => {
          this.toastrService.error("Contact already exists.","Failed");
          console.error('Error adding supplier:', err);
        }
      });
    }
  }
}
