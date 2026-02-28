import { Component, EventEmitter, Output } from '@angular/core';
import { CategoryService } from '@services/category.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css']
})
export class CategoryFormComponent {
  category = { categoryName: '' };
  categories: any[] = [];

  @Output() categoryAdded = new EventEmitter<string>();
  
  constructor(private categoryService: CategoryService, private router: Router , private toastrService : ToastrService) {}

  onSubmitCategory() {
    this.categoryService.create(this.category).subscribe(() => {
      this.toastrService.success("Category Added Successfully" , "Success !")
      this.categoryService.getAll().subscribe(categories => {
        this.categories = categories;
        this.categoryAdded.emit(this.category.categoryName);
      })
    } , 
    (err : any) => {
      this.toastrService.error("Category Already Exists" , "Failed !");
      console.log("Hi", err);
      
    }
  );
  }
}

