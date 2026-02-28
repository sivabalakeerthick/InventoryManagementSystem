import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../models/login-request.model';
import { Observable } from 'rxjs';
import { AuthResponse } from '../models/auth-response.model';

import {environment} from "../environments/environment";
import { Injectable } from '@angular/core';

@Injectable({
    providedIn : "root"
})

export class AuthService{

    private apiUrl : string;

    constructor(private http : HttpClient){
        this.apiUrl = environment.apiUrl + "/api/auth/login" ;
    }

    login(loginPayload : LoginRequest) : Observable<AuthResponse>{
        return this.http.post<AuthResponse>(`${this.apiUrl}` , loginPayload ,
            {
              headers: { 'Content-Type': 'application/json' }
            }
          );
    }

    saveToken(response : AuthResponse){
        localStorage.setItem("jwtToken" , response.token);
        localStorage.setItem("role" , response.userRole);
    }

    getToken() : string | null{
        return localStorage.getItem("jwtToken");
    }

    getUserRole() : string | null{
        return localStorage.getItem("role");
    }

    logout() {
        localStorage.removeItem("jwtToken");
        localStorage.removeItem("role");
    }

    isLoggedIn() : boolean{
        return !!this.getToken();
    } 
}