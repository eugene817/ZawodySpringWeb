import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ContestService } from '../../services/contest.service';
import { ContestResponse } from '../../models/contest.models';

@Component({
	selector: 'app-contest-view',
	standalone: true,
	imports: [CommonModule, RouterModule],
	template: `
    <div class="layout">
      <aside class="sidebar">
        <div class="back-nav">
          <a routerLink="/contests" class="back-link">
            <span class="arrow">←</span> All Contests
          </a>
        </div>
        <div class="sidebar-title">Other Contests</div>
        <nav class="mini-list">
          @for (c of otherContests; track c.id) {
            <a [routerLink]="['/contests', c.id]" 
               [class.active]="c.id === contest?.id"
               class="mini-item">
              {{ c.name }}
            </a>
          }
        </nav>
      </aside>

      <main class="main-content">
        @if (contest) {
          <div class="header">
            <div class="title-group">
              <h1>{{ contest.name }}</h1>
              <p class="desc">{{ contest.description }}</p>
            </div>
            <button class="btn-primary" [routerLink]="['/contests', contest.id, 'add-problem']">
              + Add Problem
            </button>
          </div>

          <table class="problem-table">
            <thead>
              <tr>
                <th style="width: 50px">#</th>
                <th>Task Name</th>
                <th>Limits</th>
                <th>Max Points</th>
              </tr>
            </thead>
            <tbody>
              @for (p of contest.problems; track p.id) {
                <tr>
                  <td class="code-cell">{{ p.shortCode }}</td>
                  <td>
                    <a [routerLink]="['/problems', p.id]" class="problem-link">{{ p.name }}</a>
                  </td>
                  <td class="limits-cell">
                    <span class="badge">{{ p.timeLimitMs }}ms</span>
                    <span class="badge">{{ p.memoryLimitMb }}MB</span>
                  </td>
                  <td class="points-cell">{{ p.maxPoints }}</td>
                </tr>
              } @empty {
                <tr>
                  <td colspan="4" class="empty-msg">No problems added yet.</td>
                </tr>
              }
            </tbody>
          </table>
        } @else {
          <div class="loading">Loading contest details...</div>
        }
      </main>
    </div>
  `,
	styles: [`
    .layout { display: flex; min-height: 100vh; background: #f9f9f9; }

    /* Sidebar Styles */
    .sidebar { 
      width: 260px; 
      background: white; 
      border-right: 1px solid #e0e0e0; 
      padding: 20px 0; 
      display: flex; 
      flex-direction: column;
    }
    .back-nav { padding: 0 20px 20px; border-bottom: 1px solid #f0f0f0; }
    .back-link { 
      text-decoration: none; 
      color: #007bff; 
      font-weight: 600; 
      display: flex; 
      align-items: center; 
      gap: 8px;
    }
    .sidebar-title { padding: 20px 20px 10px; font-size: 12px; text-transform: uppercase; color: #999; letter-spacing: 1px; }
    .mini-list { display: flex; flex-direction: column; }
    .mini-item { 
      padding: 12px 20px; 
      text-decoration: none; 
      color: #444; 
      font-size: 14px; 
      border-left: 3px solid transparent;
      transition: 0.2s;
    }
    .mini-item:hover { background: #f0f7ff; color: #007bff; }
    .mini-item.active { background: #eef6ff; border-left-color: #007bff; color: #007bff; font-weight: 600; }

    /* Main Content Styles */
    .main-content { flex: 1; padding: 40px; }
    .header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 30px; }
    h1 { margin: 0 0 8px 0; font-size: 28px; }
    .desc { color: #666; margin: 0; }
    
    .problem-table { 
      width: 100%; 
      border-collapse: collapse; 
      background: white; 
      box-shadow: 0 1px 3px rgba(0,0,0,0.1);
      border-radius: 4px;
    }
    .problem-table th { text-align: left; padding: 15px; background: #fff; border-bottom: 2px solid #eee; color: #666; font-size: 13px; }
    .problem-table td { padding: 15px; border-bottom: 1px solid #f5f5f5; }
    
    .code-cell { font-weight: bold; color: #007bff; }
    .problem-link { text-decoration: none; color: #222; font-weight: 500; }
    .problem-link:hover { text-decoration: underline; color: #007bff; }
    
    .badge { 
      background: #f0f0f0; 
      padding: 3px 8px; 
      border-radius: 3px; 
      font-size: 12px; 
      margin-right: 6px; 
      color: #555;
    }
    .btn-primary { 
      background: #28a745; 
      color: white; 
      border: none; 
      padding: 10px 20px; 
      border-radius: 4px; 
      cursor: pointer; 
      font-weight: 600;
    }
    .empty-msg { text-align: center; color: #999; padding: 40px !important; }
  `]
})
export class ContestView implements OnInit {
	contest?: ContestResponse;
	otherContests: ContestResponse[] = [];

	constructor(
		private route: ActivatedRoute,
		private contestService: ContestService,
		private cdr: ChangeDetectorRef
	) { }

	ngOnInit() {
		this.route.paramMap.subscribe(params => {
			const id = Number(params.get('id'));
			if (id) {
				this.loadContest(id);
			}
		});

		this.contestService.getAllContests().subscribe(data => {
			this.otherContests = data;
			this.cdr.detectChanges();
		});
	}

	loadContest(id: number) {
		this.contestService.getContest(id).subscribe({
			next: (data) => {
				this.contest = data;
				this.cdr.detectChanges();
			}
		});
	}
}
