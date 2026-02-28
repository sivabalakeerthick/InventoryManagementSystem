import { Injectable } from "@angular/core";
import { CanActivate , Router , ActivatedRouteSnapshot } from "@angular/router";
import { AuthService } from "../services/auth.service";

@Injectable({
  providedIn : "root"
})
export class AuthGuard implements CanActivate{
  constructor(private authService : AuthService , private router : Router){}

  canActivate(route : ActivatedRouteSnapshot) : boolean {
    const expectedRole = route.data['expectedRole'];
    const role = this.authService.getUserRole();

    if(!this.authService.isLoggedIn()){
      this.router.navigate(["/login"]);
      return false;
    }

    if(role !== expectedRole){
      this.router.navigate(["/unauthorized"]);
      return false;
    }

    return true;
  } 
}