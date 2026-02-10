import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest, AuthResponse } from '../models/auth.models';
import { tap } from 'rxjs/operators';

@Injectable({
	providedIn: 'root',
})
export class Auth {
	private apiUrl = '/api/auth';
	private readonly TOKEN_KEY = 'token';
	private readonly USER_KEY = 'user';

	constructor(private http: HttpClient) { }

	login(request: LoginRequest) {
		return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request).pipe(
			tap(response => {
				localStorage.setItem(this.TOKEN_KEY, response.token);
				localStorage.setItem(this.USER_KEY, response.login);
			})
		);
	}

	logout() {
		localStorage.removeItem(this.TOKEN_KEY);
		localStorage.removeItem(this.USER_KEY);
	}

	isLoggedIn(): boolean {
		return !!localStorage.getItem(this.TOKEN_KEY);
	}

	getUsername(): string | null {
		return localStorage.getItem(this.USER_KEY);
	}

	getToken(): string | null {
		return localStorage.getItem(this.TOKEN_KEY);
	}
}
