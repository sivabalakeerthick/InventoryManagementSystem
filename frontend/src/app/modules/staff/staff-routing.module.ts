import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ProductListComponent } from "@components/product-list/product-list.component";
import { AuthGuard } from "src/app/guards/auth.guard";
import { LayoutComponent } from "src/app/layout/layout.component";
import { OrderFormComponent } from "@components/order-form/order-form.component";
import { OrderListComponent } from "@components/order-list/order-list.component";
import { StockOverviewComponent } from "@components/stock-overview/stock-overview.component";



const routes : Routes = [

]
    

@NgModule({
    imports : [RouterModule.forChild(routes)],
    exports : [RouterModule]
})

export class StaffRoutingModule {}