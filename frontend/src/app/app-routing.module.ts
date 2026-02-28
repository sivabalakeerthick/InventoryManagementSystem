import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
const routes: Routes = [
  {
    path : '',
    redirectTo : 'login',
    pathMatch : 'full'
  } ,
  {
    path : 'login',
    component : LoginComponent
  },
  {
    path : 'admin',
    canActivate : [AuthGuard],
    data : {expectedRole  : "ADMIN"},
    loadChildren : () =>
      import("./modules/admin/admin.module").then(m => m.AdminModule),
  },
  {
    path : 'staff',
    canActivate : [AuthGuard],
    data : {expectedRole  : "STAFF"},
    loadChildren : () =>
      import("./modules/staff/staff.module").then(m => m.StaffModule),
  },
  {
    path : 'unauthorized',
    component : UnauthorizedComponent,
  },
  {
    path : '**',
    component : PageNotFoundComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
