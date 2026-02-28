import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { PurchaseOrderFormComponent } from "src/app/components/purchase-order-form/purchase-order-form.component";
import { PurchaseOrderListComponent } from "src/app/components/purchase-order-list/purchase-order-list.component";
import { SupplierFormComponent } from "src/app/components/supplier-form/supplier-form.component";
import { SupplierListComponent } from "src/app/components/supplier-list/supplier-list.component";
import { CategoryFormComponent } from "@components/category-form/category-form.component";
import { ProductFormComponent } from "@components/product-form/product-form.component";
import { ProductListComponent } from "@components/product-list/product-list.component";
import { LayoutComponent } from "src/app/layout/layout.component";
import { OrderFormComponent } from "@components/order-form/order-form.component";
import { OrderListComponent } from "@components/order-list/order-list.component";
import { OrderReportComponent } from "@components/order-report/order-report.component";
import { ReportAnalysisComponent } from "@components/report-analysis/report-analysis.component";
import { SupplierReportComponent } from "@components/supplier-report/supplier-report.component";
import { StockOverviewComponent } from "@components/stock-overview/stock-overview.component";
import { DashboardSummaryComponent } from "@components/dashboard-summary/dashboard-summary.component";



const routes : Routes = [
    {
        path : '' , 
        component : LayoutComponent,
        children : [
            {
                path : 'reports',
                component : ReportAnalysisComponent
            },
            {
                path : 'suppliers',
                component : SupplierListComponent,
            },
            {
                path : 'suppliers/edit/:id',
                component : SupplierFormComponent,
                
            },
            {
                path : 'suppliers/add',
                component : SupplierFormComponent
            },
            {
                path : 'purchase-orders',
                component : PurchaseOrderListComponent,
            },
            {
                path : 'purchase-orders/edit/:id',
                component : PurchaseOrderFormComponent
            },
            {
                path : 'purchase-orders/view/id',
                component : PurchaseOrderFormComponent
            },
            {
                path : 'purchase-orders/add',
                component : PurchaseOrderFormComponent
            },
            {
                path: 'products/add',
                component: ProductFormComponent
            },
            {
                path: 'products/edit/:id',
                component: ProductFormComponent
            },
            {
                path: 'categories/add',
                component: CategoryFormComponent
            },
            {
                path : 'orders/create',
                component : OrderFormComponent
            },
        ],
        
    },

]

@NgModule({
    imports : [RouterModule.forChild(routes)],
    exports : [RouterModule]
})

export class AdminRoutingModule {}