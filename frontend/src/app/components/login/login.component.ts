import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/models/login-request.model';
import { AuthService } from 'src/app/services/auth.service';
import { ToastrService } from 'ngx-toastr';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email : string = '';
  password : string = '';

  constructor(private router : Router , private authService : AuthService , private toastrService : ToastrService){

  }

  ngOnInit(): void {
    if(this.authService.isLoggedIn()){
      this.redirectBasedOnRole(this.authService.getUserRole());
    }
    else{
      this.router.navigate(['/login']);
    }
  }

  onLogin(){

    const loginPayload : LoginRequest = {
      email : this.email,
      password : this.password
    };

    this.authService.login(loginPayload)
    .subscribe((response) => {
      this.authService.saveToken(response);
      this.toastrService.success("Login Successful !" , "Success");
      this.redirectBasedOnRole(response.userRole);

    } , error => {
      this.toastrService.error("Invalid Credentials" , "Login Failed !");
    }
  );
  }

  redirectBasedOnRole(role : String | null) {
    
    if(role === "ADMIN"){
      this.router.navigate(["/admin/stock-overview"]);
    }
    else if(role === "STAFF"){
      this.router.navigate(["/staff/products"])
    }
  }
}
