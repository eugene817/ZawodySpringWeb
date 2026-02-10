import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
	ContestResponse,
	ContestCreateRequest,
	ProblemResponse,
	ProblemCreateRequest
} from '../models/contest.models';

@Injectable({
	providedIn: 'root'
})
export class ContestService {
	private contestsUrl = '/api/contests';
	private problemsUrl = '/api/problems';

	constructor(private http: HttpClient) { }

	getAllContests(): Observable<ContestResponse[]> {
		return this.http.get<ContestResponse[]>(`${this.contestsUrl}/all`);
	}

	getContest(id: number): Observable<ContestResponse> {
		return this.http.get<ContestResponse>(this.contestsUrl, {
			params: { id: id.toString() }
		});
	}

	createContest(request: ContestCreateRequest): Observable<string> {
		return this.http.post(this.contestsUrl, request, {
			responseType: 'text'
		});
	}

	createProblem(contestId: number, request: ProblemCreateRequest): Observable<ProblemResponse> {
		return this.http.post<ProblemResponse>(
			`${this.problemsUrl}/contest/${contestId}`,
			request
		);
	}

	getProblem(id: number): Observable<ProblemResponse> {
		return this.http.get<ProblemResponse>(`${this.problemsUrl}/${id}`);
	}
}
