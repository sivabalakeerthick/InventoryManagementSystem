import { NgModule } from "@angular/core";
import { SidebarComponent } from "../../components/sidebar/sidebar.component";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { SharedRoutingModule } from "./shared-routing.module";
import { OrderListComponent } from "@components/order-list/order-list.component";
import { LayoutComponent } from "src/app/layout/layout.component";
import { ProductListComponent } from "@components/product-list/product-list.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { OrderFormComponent } from "@components/order-form/order-form.component";
import { PaginationComponent } from "@components/pagination/pagination.component";
import { StockOverviewComponent } from "@components/stock-overview/stock-overview.component";
import { NoRecordsFoundComponent } from "@components/no-records-found/no-records-found.component";



@NgModule({
    declarations :
     [
        SidebarComponent ,
      OrderListComponent ,
      LayoutComponent,
      ProductListComponent
     ,OrderFormComponent,
     StockOverviewComponent,
    PaginationComponent,
    NoRecordsFoundComponent],
    imports:[
        CommonModule,
        RouterModule,
        SharedRoutingModule,
        FormsModule,
        ReactiveFormsModule,

    ],
    exports:[SidebarComponent , OrderListComponent , LayoutComponent , OrderFormComponent , CommonModule , PaginationComponent]
})
export class SharedModule{}
