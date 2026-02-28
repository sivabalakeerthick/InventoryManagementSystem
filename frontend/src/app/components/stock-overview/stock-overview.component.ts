import { Component } from '@angular/core';
import { Stock } from '@models/stock.model';
import { StockService } from '@services/stock.service';
import { StockStatus } from '@models/stock.model';
import { Category } from '@models/category.model';
import { CategoryService } from '@services/category.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-stock-overview',
  templateUrl: './stock-overview.component.html',
  styleUrls: ['./stock-overview.component.css']
})
export class StockOverviewComponent {
  StockStatus :  StockStatus | undefined;
  stockStatusOptions: StockStatus[] = [
    "IN_STOCK",
    "OUT_OF_STOCK",
    "LOW_STOCK",
  ];
  stocks: Stock[] = [];
  categories: Category[] = [];
  inStockCount = 0;
  lowStockCount = 0;
  outOfStockCount = 0;
  totalProducts : number = 0;
  totalElements : number = 0;
  currentPage: number = 0;
  pageSize: number = 5;
  totalPages: number = 1;
  searchQuery : string = "";
  selectedCategory : string = "";
  selectedStatus : StockStatus | string = "" ;
  sortOrder : string = "asc";

  constructor(private stockService: StockService , private categoryService : CategoryService , private router : Router , private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.checkRouterStateForFilter();
    this.getAllStocks();
    this.getCategories();
    this.totalProducts = this.stocks.length;
  }

  private checkRouterStateForFilter(): void {
    console.log(this.router.getCurrentNavigation());

      const state = this.router.getCurrentNavigation()?.extras.state || this.activatedRoute.snapshot.paramMap.get('data') || history.state;

      if (state && state['filterBy'] === 'LOW_STOCK') {
        this.selectedStatus = 'LOW_STOCK';
        console.log('StockOverviewComponent: Applied LOW_STOCK filter from Router State.');
      }
  }

  getAllStocks(){
    this.stockService.getAllStocks(this.currentPage , this.pageSize , this.selectedCategory , this.searchQuery ,  this.selectedStatus as string , this.sortOrder).subscribe(
      (data : any) => {

        this.totalElements = data.totalElements;
        this.stocks = data.content;
        this.totalPages = Math.max(1 , data.totalPages);
        this.calculateSummary();
      },
      err => {
        console.log(err);
      }
    )
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

  calculateSummary(): void {


    this.stockService.getProductsCountByStatus("IN_STOCK").subscribe(
      (data) => {
        this.inStockCount = data.count;
      }
    );
    this.stockService.getProductsCountByStatus("OUT_OF_STOCK").subscribe(
      (data) => {
        this.outOfStockCount = data.count;
      }
    );
    this.stockService.getProductsCountByStatus("LOW_STOCK").subscribe(
      (data) => {
        console.log(data);
        this.lowStockCount = data.count;
      }
    );
    this.stockService.getProductsCount().subscribe(
      (data) => {
        this.totalProducts = data.totalProducts;
      }
    );
  }



  getStockStatusLabel(status: StockStatus): string {
    switch (status) {
      case "IN_STOCK":
        return 'In Stock';
      case "OUT_OF_STOCK":
        return 'Out of Stock';
      case "LOW_STOCK":
        return 'Low Stock';
      case "NOT_AVAILABLE":
        return 'Not Available';
      default:
        return 'Unknown';
    }
  }

  onPageChange(newPage: number): void {
    this.currentPage = newPage;
    this.getAllStocks();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }


  filterStockRecords(){
    this.currentPage = 0;
    this.getAllStocks();
  }

  sortStockRecords(event : Event){
    this.getAllStocks();
  }

}
