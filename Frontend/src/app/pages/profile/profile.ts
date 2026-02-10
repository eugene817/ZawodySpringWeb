import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';

@Component({
	selector: 'app-profile',
	standalone: true,
	imports: [CommonModule],
	template: `
    <div class="profile-container">
      <div class="profile-card">
        <div class="avatar-circle">
          {{ (authService.getUsername() || 'U')[0].toUpperCase() }}
        </div>
        
        <h2 class="username">{{ authService.getUsername() }}</h2>
        <p class="role-badge">Participant</p>

        <div class="info-grid">
          <div class="info-item">
            <span class="label">Status</span>
            <span class="value active">Online</span>
          </div>
          <div class="info-item">
            <span class="label">Organization</span>
            <span class="value">ZawodySpringWeb User</span>
          </div>
        </div>

        <div class="actions">
          <button class="logout-btn" (click)="onLogout()">Logout</button>
        </div>
      </div>
    </div>
  `,
	styles: [`
    .profile-container { 
      padding: 60px 20px; 
      display: flex; 
      justify-content: center; 
      background: #f9f9f9; 
      min-height: 100vh; 
    }
    .profile-card { 
      background: white; 
      width: 100%; 
      max-width: 400px; 
      padding: 40px; 
      border-radius: 12px; 
      box-shadow: 0 10px 25px rgba(0,0,0,0.05); 
      text-align: center;
      border: 1px solid #eee;
    }
    .avatar-circle {
      width: 80px; height: 80px; 
      background: #007bff; 
      color: white; 
      border-radius: 50%; 
      display: flex; 
      align-items: center; 
      justify-content: center; 
      font-size: 32px; font-weight: bold; 
      margin: 0 auto 20px;
    }
    .username { margin: 0; font-size: 24px; color: #333; }
    .role-badge { 
      display: inline-block; 
      background: #eef6ff; color: #007bff; 
      padding: 4px 12px; border-radius: 20px; 
      font-size: 12px; font-weight: 600; margin: 10px 0 30px;
    }
    .info-grid { border-top: 1px solid #eee; padding-top: 20px; margin-bottom: 30px; }
    .info-item { display: flex; justify-content: space-between; margin-bottom: 12px; font-size: 14px; }
    .label { color: #888; }
    .value { color: #333; font-weight: 500; }
    .value.active { color: #28a745; }
    
    .logout-btn { 
      width: 100%; 
      padding: 12px; 
      background: white; 
      border: 1px solid #dc3545; 
      color: #dc3545; 
      border-radius: 6px; 
      font-weight: 600; 
      cursor: pointer; 
      transition: 0.2s;
    }
    .logout-btn:hover { background: #dc3545; color: white; }
  `]
})
export class Profile {
	constructor(public authService: Auth, private router: Router) { }

	onLogout() {
		this.authService.logout();
		this.router.navigate(['/login']);
	}
}
