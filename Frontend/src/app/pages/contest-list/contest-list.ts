import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ContestService } from '../../services/contest.service';
import { ContestResponse } from '../../models/contest.models';
import { Auth } from '../../services/auth';

@Component({
	selector: 'app-contest-list',
	standalone: true,
	imports: [CommonModule, RouterModule],
	template: `
    <div class="container">
      <header class="main-header">
        <h1>ZawodySpringWeb</h1>
        @if (authService.isLoggedIn()) {
          <button class="create-btn" routerLink="/contests/create">
            + Create Contest
          </button>
        }
      </header>

      <section class="contest-section">
        <h2>Active and Past Contests</h2>
        
        <div class="list-container">
          <div class="list-header">
            <div class="col-name">Name</div>
            <div class="col-date">Start Time</div>
            <div class="col-status">Status</div>
          </div>

          @for (contest of contests; track contest.id) {
            <div class="contest-row" [routerLink]="['/contests', contest.id]">
              <div class="col-name">
                <span class="contest-title">{{ contest.name }}</span>
                <p class="contest-desc">{{ contest.description }}</p>
              </div>
              <div class="col-date">
                {{ contest.startTime | date:'dd.MM.yyyy HH:mm' }}
              </div>
              <div class="col-status">
                <span class="status-badge">Ready</span>
              </div>
            </div>
          } @empty {
            <div class="empty-state">
              No contests available at the moment.
            </div>
          }
        </div>
      </section>
    </div>
  `,
	styles: [`
    .container {
      max-width: 1000px;
      margin: 0 auto;
      padding: 20px;
      font-family: 'Inter', system-ui, sans-serif;
    }

    .main-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 40px;
      border-bottom: 2px solid #333;
      padding-bottom: 10px;
    }

    .main-header h1 {
      font-size: 2rem;
      font-weight: 800;
      color: #1a1a1a;
      margin: 0;
    }

    .create-btn {
      padding: 10px 20px;
      background-color: #28a745;
      color: white;
      border: none;
      border-radius: 6px;
      font-weight: 600;
      cursor: pointer;
      transition: 0.2s;
    }

    .create-btn:hover {
      background-color: #218838;
      transform: translateY(-1px);
    }

    .contest-section h2 {
      font-size: 1.25rem;
      color: #666;
      margin-bottom: 20px;
    }

    /* Табличный список */
    .list-container {
      background: white;
      border: 1px solid #e1e4e8;
      border-radius: 8px;
      overflow: hidden;
    }

    .list-header {
      display: grid;
      grid-template-columns: 1fr 200px 100px;
      padding: 12px 20px;
      background: #f6f8fa;
      font-weight: 600;
      font-size: 0.85rem;
      color: #586069;
      border-bottom: 1px solid #e1e4e8;
    }

    .contest-row {
      display: grid;
      grid-template-columns: 1fr 200px 100px;
      padding: 16px 20px;
      border-bottom: 1px solid #e1e4e8;
      cursor: pointer;
      transition: background 0.1s;
      align-items: center;
    }

    .contest-row:last-child {
      border-bottom: none;
    }

    .contest-row:hover {
      background-color: #f1faff;
    }

    .contest-title {
      font-weight: 600;
      color: #0366d6;
      font-size: 1.1rem;
    }

    .contest-desc {
      margin: 4px 0 0;
      font-size: 0.9rem;
      color: #586069;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }

    .col-date {
      font-size: 0.9rem;
      color: #444;
    }

    .status-badge {
      font-size: 0.75rem;
      padding: 4px 8px;
      background: #e1f5fe;
      color: #0288d1;
      border-radius: 12px;
      font-weight: 600;
    }

    .empty-state {
      padding: 40px;
      text-align: center;
      color: #888;
    }
  `]
})
export class ContestList implements OnInit {
	contests: ContestResponse[] = [];

	constructor(
		private contestService: ContestService,
		public authService: Auth,
		private cdr: ChangeDetectorRef
	) { }

	ngOnInit() {
		this.contestService.getAllContests().subscribe({
			next: (data) => {
				this.contests = [...data];
				this.cdr.detectChanges();
			},
			error: (err) => console.error('Error:', err)
		});
	}
}
