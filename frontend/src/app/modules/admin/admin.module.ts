import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { AdminRoutingModule } from "./admin-routing.module";
import { SharedModule } from "../shared/shared.module";
import { RouterModule } from "@angular/router";
import { SupplierListComponent } from "src/app/components/supplier-list/supplier-list.component";
import { SupplierFormComponent } from "src/app/components/supplier-form/supplier-form.component";
import { PurchaseOrderListComponent } from "src/app/components/purchase-order-list/purchase-order-list.component";
import { PurchaseOrderFormComponent } from "src/app/components/purchase-order-form/purchase-order-form.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ReportAnalysisComponent } from "@components/report-analysis/report-analysis.component";
import { MaterialModuleModule } from "../material-module/material-module.module";
import { ProductFormComponent } from "@components/product-form/product-form.component";
import { CategoryFormComponent } from "@components/category-form/category-form.component";
import { DashboardSummaryComponent } from "@components/dashboard-summary/dashboard-summary.component";
import { NgChartsModule } from "ng2-charts";
import { OrderReportComponent } from "@components/order-report/order-report.component";
import { InventoryReportComponent } from "@components/inventory-report/inventory-report.component";
import { SupplierReportComponent } from "@components/supplier-report/supplier-report.component";

@NgModule({
    declarations :[
        ProductFormComponent,
        CategoryFormComponent,
        SupplierListComponent,
        SupplierFormComponent,
        PurchaseOrderListComponent,
        PurchaseOrderFormComponent,
        ReportAnalysisComponent,
        DashboardSummaryComponent,
        OrderReportComponent,
        InventoryReportComponent,
        SupplierReportComponent


    ],
    imports : [CommonModule , RouterModule , AdminRoutingModule , SharedModule , FormsModule , MaterialModuleModule , NgChartsModule,ReactiveFormsModule]
})

export class AdminModule{}
