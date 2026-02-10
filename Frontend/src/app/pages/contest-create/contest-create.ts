import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ContestService } from '../../services/contest.service';

@Component({
	selector: 'app-contest-create',
	standalone: true,
	imports: [CommonModule, ReactiveFormsModule],
	template: `
    <div class="create-container">
      <h2>Create new contest</h2>
      
      <form [formGroup]="contestForm" (ngSubmit)="onSubmit()">
        <div class="form-group">
          <label>Name</label>
          <input type="text" formControlName="name">
        </div>

        <div class="form-group">
          <label>Decription</label>
          <textarea formControlName="description"></textarea>
        </div>

        <div class="form-group">
          <label>Start time</label>
          <input type="datetime-local" formControlName="startTime">
        </div>

        <div class="form-group">
          <label>End time</label>
          <input type="datetime-local" formControlName="endTime">
        </div>

        <div class="form-group">
          <label>Access password (optional)</label>
          <input type="text" formControlName="accessPassword">
        </div>

        <div class="form-group">
          <label>
            <input type="checkbox" formControlName="visible"> Visible
          </label>
        </div>

        <button type="submit" [disabled]="contestForm.invalid">Create</button>
      </form>
    </div>
  `,
	styles: [`
    .create-container { max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; }
    .form-group { margin-bottom: 15px; display: flex; flex-direction: column; }
    input[type="text"], input[type="datetime-local"], textarea { padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
    button { padding: 10px; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; }
    button:disabled { background: #ccc; }
  `]
})
export class ContestCreate {
	contestForm = new FormGroup({
		name: new FormControl('', [Validators.required]),
		description: new FormControl(''),
		startTime: new FormControl('', [Validators.required]),
		endTime: new FormControl('', [Validators.required]),
		accessPassword: new FormControl(''),
		visible: new FormControl(true)
	});

	constructor(
		private contestService: ContestService,
		private router: Router
	) { }

	onSubmit() {
		if (this.contestForm.valid) {
			const formValue = this.contestForm.value;

			const request = {
				name: formValue.name!,
				description: formValue.description || '',
				startTime: new Date(formValue.startTime!).toISOString(),
				endTime: new Date(formValue.endTime!).toISOString(),
				accessPassword: formValue.accessPassword || undefined,
				visible: !!formValue.visible
			};

			this.contestService.createContest(request).subscribe({
				next: () => {
					this.router.navigate(['/contests']);
				},
				error: (err) => console.error('Error creating', err)
			});
		}
	}
}
