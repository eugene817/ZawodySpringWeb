export interface ProblemResponse {
	id: number;
	name: string;
	shortCode: string;
	statement: string;
	memoryLimitMb: number;
	codeSizeLimitKb: number;
	timeLimitMs: number;
	maxPoints: number;
	contestId: number;
	visibleInRanking: boolean;
}

export interface ProblemCreateRequest {
	name: string;
	shortCode?: string;
	statement?: string;
	memoryLimitMb?: number;
	codeSizeLimitKb?: number;
	timeLimitMs?: number;
	maxPoints?: number;
	visibleInRanking?: boolean;
}

export interface ContestResponse {
	id: number;
	name: string;
	description: string;
	startTime: string; // ISO Date
	endTime: string;   // ISO Date
	accessPassword?: string;
	visible: boolean;
	problems?: ProblemResponse[];
}

export interface ContestCreateRequest {
	name: string;
	description: string;
	startTime: string;
	endTime: string;
	accessPassword?: string;
	visible: boolean;
}
