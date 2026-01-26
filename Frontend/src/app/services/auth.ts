import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest, AuthResponse } from '../models/auth.models';
import { tap } from 'rxjs/operators';

@Injectable({
	providedIn: 'root',
})
export class Auth {
	private apiUrl = '/api/auth';

	constructor(private http: HttpClient) { }

	login(request: LoginRequest) {
		return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request).pipe(
			tap(response => {
				localStorage.setItem('token', response.token);
				localStorage.setItem('user', response.login);
			})
		);
	}

	logout() {
		localStorage.removeItem('token');
		localStorage.removeItem('user');
	}

	isLoggedIn(): boolean {
		return !!localStorage.getItem('token');
	}
}
