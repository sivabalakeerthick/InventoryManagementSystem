import { NgModule } from "@angular/core";
import { OrderListComponent } from "@components/order-list/order-list.component";
import { AdminRoutingModule } from "../admin/admin-routing.module";
import { CommonModule } from "@angular/common";
import { SharedModule } from "../shared/shared.module";
import { RouterModule } from "@angular/router";
import { StaffRoutingModule } from "./staff-routing.module";
import { FormsModule } from "@angular/forms";


@NgModule({
    declarations :[
        
    ],
    imports : [CommonModule , RouterModule , StaffRoutingModule , SharedModule , FormsModule]
})

export class StaffModule{}