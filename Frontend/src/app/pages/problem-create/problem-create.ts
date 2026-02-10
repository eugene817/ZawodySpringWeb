import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ContestService } from '../../services/contest.service';

@Component({
	selector: 'app-problem-create',
	standalone: true,
	imports: [CommonModule, ReactiveFormsModule],
	template: `
    <div class="form-card">
      <h2>New Problem / Nowe zadanie</h2>
      <form [formGroup]="problemForm" (ngSubmit)="onSubmit()">
        <div class="form-group">
          <label>Title</label>
          <input type="text" formControlName="name">
        </div>

        <div class="row">
          <div class="form-group">
            <label>Short Code (e.g., A)</label>
            <input type="text" formControlName="shortCode">
          </div>
          <div class="form-group">
            <label>Max Points</label>
            <input type="number" formControlName="maxPoints">
          </div>
        </div>

        <div class="form-group">
          <label>Statement(Markdown)</label>
          <textarea formControlName="statement" rows="8"></textarea>
        </div>

        <div class="row">
          <div class="form-group">
            <label>Time Limit (ms)</label>
            <input type="number" formControlName="timeLimitMs">
          </div>
          <div class="form-group">
            <label>Memory Limit (MB)</label>
            <input type="number" formControlName="memoryLimitMb">
          </div>
        </div>

        <button type="submit" [disabled]="problemForm.invalid" class="submit-btn">
          Create Problem
        </button>
      </form>
    </div>
  `,
	styles: [`
    .form-card { max-width: 600px; margin: 40px auto; padding: 25px; background: white; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
    .form-group { margin-bottom: 15px; display: flex; flex-direction: column; }
    .row { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
    label { font-weight: 500; margin-bottom: 5px; color: #444; }
    input, textarea { padding: 10px; border: 1px solid #ddd; border-radius: 6px; font-size: 14px; }
    .submit-btn { width: 100%; padding: 12px; background: #007bff; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: bold; margin-top: 10px; }
    .submit-btn:disabled { background: #ccc; }
  `]
})
export class ProblemCreate implements OnInit {
	contestId!: number;
	problemForm = new FormGroup({
		name: new FormControl('', [Validators.required]),
		shortCode: new FormControl('A'),
		statement: new FormControl(''),
		timeLimitMs: new FormControl(1000),
		memoryLimitMb: new FormControl(256),
		maxPoints: new FormControl(100),
		visibleInRanking: new FormControl(true)
	});

	constructor(
		private route: ActivatedRoute,
		private contestService: ContestService,
		private router: Router
	) { }

	ngOnInit() {
		this.contestId = Number(this.route.snapshot.paramMap.get('contestId'));
	}

	onSubmit() {
		if (this.problemForm.valid) {
			this.contestService.createProblem(this.contestId, this.problemForm.value as any).subscribe({
				next: () => this.router.navigate(['/contests', this.contestId]),
				error: (err) => console.error(err)
			});
		}
	}
}
