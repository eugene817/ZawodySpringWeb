import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ContestService } from '../../services/contest.service';
import { ProblemResponse, ContestResponse } from '../../models/contest.models';

@Component({
	selector: 'app-problem-view',
	standalone: true,
	imports: [CommonModule, RouterModule],
	template: `
    <div class="layout">
      <aside class="sidebar">
        @if (contest) {
          <div class="back-nav">
            <a [routerLink]="['/contests', contest.id]" class="back-link">
              ← {{ contest.name }}
            </a>
          </div>
          <div class="sidebar-title">Tasks</div>
          <nav class="mini-list">
            @for (p of contest.problems; track p.id) {
              <a [routerLink]="['/problems', p.id]" 
                 [class.active]="p.id === problem?.id"
                 class="mini-item">
                <span class="p-code">{{ p.shortCode }}</span>
                <span class="p-name">{{ p.name }}</span>
              </a>
            }
          </nav>
        }
      </aside>

      <main class="main-content">
        @if (problem) {
          <header class="problem-header">
            <div class="title-wrap">
              <h1>{{ problem.shortCode }}. {{ problem.name }}</h1>
              <div class="problem-meta">
                <span class="meta-item">Time limit: <b>{{ problem.timeLimitMs }} ms</b></span>
                <span class="meta-item">Memory limit: <b>{{ problem.memoryLimitMb }} MB</b></span>
                <span class="meta-item">Max points: <b>{{ problem.maxPoints }}</b></span>
              </div>
            </div>
            <button class="submit-btn">Submit Solution</button>
          </header>

          <article class="statement-box">
            <div class="section-label">Statement</div>
            <div class="text-content">
              {{ problem.statement }}
            </div>
          </article>
        } @else {
          <div class="loading">Loading task details...</div>
        }
      </main>
    </div>
  `,
	styles: [`
    .layout { display: flex; min-height: 100vh; background: #fff; }

    /* Sidebar */
    .sidebar { width: 280px; background: #f8f9fa; border-right: 1px solid #eee; padding: 20px 0; }
    .back-nav { padding: 0 20px 20px; border-bottom: 1px solid #eee; }
    .back-link { text-decoration: none; color: #666; font-size: 14px; font-weight: 500; }
    .back-link:hover { color: #007bff; }
    .sidebar-title { padding: 20px 20px 10px; font-size: 11px; text-transform: uppercase; color: #999; letter-spacing: 1px; }
    .mini-list { display: flex; flex-direction: column; }
	.mini-item { 
	  padding: 12px 20px; 
	  text-decoration: none; 
	  color: #444; 
	  font-size: 14px; 
	  display: grid; /* Changed from flex to grid */
	  grid-template-columns: 30px 1fr; /* Fixed width for code, flexible for name */
	  gap: 10px; 
	  border-left: 4px solid transparent;
	  align-items: center;
	  overflow: hidden; /* Prevents text spill */
	}

	.p-name {
	  white-space: nowrap;
	  overflow: hidden;
	  text-overflow: ellipsis; /* Adds "..." if the name is too long */
	}
    .mini-item:hover { background: #eee; }
    .mini-item.active { background: #fff; border-left-color: #007bff; color: #007bff; font-weight: 600; }
    .p-code { color: #007bff; font-weight: bold; width: 20px; }

    /* Main Content */
    .main-content { flex: 1; padding: 40px 60px; max-width: 900px; }
    .problem-header { 
      display: flex; justify-content: space-between; align-items: flex-start; 
      margin-bottom: 40px; border-bottom: 1px solid #eee; padding-bottom: 20px;
    }
    h1 { margin: 0 0 15px 0; font-size: 32px; color: #222; }
    .problem-meta { display: flex; gap: 20px; color: #666; font-size: 14px; }
    .meta-item b { color: #333; }
    
    .submit-btn { 
      background: #007bff; color: white; border: none; padding: 12px 24px; 
      border-radius: 6px; font-weight: 600; cursor: pointer; 
    }
    .submit-btn:hover { background: #0056b3; }

    .statement-box { line-height: 1.6; color: #333; font-size: 17px; }
    .section-label { 
      font-size: 12px; text-transform: uppercase; color: #999; 
      margin-bottom: 15px; font-weight: bold; 
    }
    .text-content { white-space: pre-wrap; background: #fafafa; padding: 20px; border-radius: 8px; border: 1px solid #f0f0f0; }
  `]
})
export class ProblemView implements OnInit {
	problem?: ProblemResponse;
	contest?: ContestResponse;

	constructor(
		private route: ActivatedRoute,
		private contestService: ContestService,
		private cdr: ChangeDetectorRef
	) { }

	ngOnInit() {
		this.route.paramMap.subscribe(params => {
			const id = Number(params.get('id'));
			if (id) {
				this.loadProblem(id);
			}
		});
	}

	loadProblem(id: number) {
		this.contestService.getProblem(id).subscribe({
			next: (p) => {
				this.problem = p;
				this.loadContest(p.contestId);
				this.cdr.detectChanges();
			}
		});
	}

	loadContest(contestId: number) {
		if (this.contest?.id === contestId) return;

		this.contestService.getContest(contestId).subscribe(c => {
			this.contest = c;
			this.cdr.detectChanges();
		});
	}
}
