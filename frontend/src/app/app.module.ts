import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { LoginComponent } from './components/login/login.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModuleModule } from './modules/material-module/material-module.module';
import { NgChartsModule } from 'ng2-charts';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from "ngx-toastr";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UnauthorizedComponent,
    PageNotFoundComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NoopAnimationsModule,
    MaterialModuleModule,
    NgChartsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({ 
        timeOut: 3000, 
        positionClass: 'toast-top-right',
        preventDuplicates: true, 
        progressBar: true,
      })
  ],
  providers: [ { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
