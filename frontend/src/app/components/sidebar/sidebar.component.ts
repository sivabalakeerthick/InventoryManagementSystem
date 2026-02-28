import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { StockService } from '@services/stock.service';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit , OnDestroy{
  role : string | null = "";
  lowStockCount : number = 0;
  private stockSubscription: Subscription | undefined;
  
    constructor(private authService : AuthService , private router : Router , private stockService : StockService){
    }
  
    ngOnInit(): void {
      this.role = this.authService.getUserRole();
      this.stockSubscription = this.stockService.getLowStockProducts().subscribe(
        res => {
          console.log(res);
          
          this.lowStockCount = res.length;
        }
      )
    }

    ngOnDestroy(): void {
      if (this.stockSubscription) {
        this.stockSubscription.unsubscribe();
      }
    }
    
  
    logout(){
      this.authService.logout();
      this.router.navigate(["/login"]);
    }

    navigateToLowStock(){
      if (this.lowStockCount > 0) {
        const navigationExtras: NavigationExtras = {
          state: {
            filterBy: 'LOW_STOCK'
          }
        };
        this.router.navigate(['/admin/stock-overview'], navigationExtras);
      }
    }

}
