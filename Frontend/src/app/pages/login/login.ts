import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

	loginForm = new FormGroup({
		login: new FormControl('', [Validators.required]),
		password: new FormControl('', [Validators.required])
	});

	errorMessage: string = '';

	constructor(
		private authService: Auth,
		private router: Router
	) {}

	onSubmit() {
		if (this.loginForm.valid) {
			const request = {
				login: this.loginForm.value.login!,
				password: this.loginForm.value.password!
			};

			this.authService.login(request).subscribe({
				next: (response) => {
					console.log('Sucess', response);
					this.router.navigate(['/']); 
				},
				error: (err) => {
					console.error('Error', err);
					this.errorMessage = 'Wrong login or password';
				}
			});
		}
	}
}
