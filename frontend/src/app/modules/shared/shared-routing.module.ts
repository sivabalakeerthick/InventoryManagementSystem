import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ProductFormComponent } from "@components/product-form/product-form.component";
import { OrderFormComponent } from "@components/order-form/order-form.component";
import { OrderListComponent } from "@components/order-list/order-list.component";
import { LayoutComponent } from "src/app/layout/layout.component";
import { ProductListComponent } from "@components/product-list/product-list.component";
import { StockOverviewComponent } from "@components/stock-overview/stock-overview.component";


const routes : Routes = [
    {
        path : '',
        component : LayoutComponent,
        children : [
            {
                path : 'orders',
                component : OrderListComponent
            },
            {
                path : 'products',
                component : ProductListComponent
            },
            {
                path : 'stock-overview',
                component : StockOverviewComponent
            },
            {
                path : 'orders/create',
                component : OrderFormComponent
            }
        ]
    }
]

@NgModule({
    imports : [RouterModule.forChild(routes)],
    exports : [RouterModule]
})

export class SharedRoutingModule{}