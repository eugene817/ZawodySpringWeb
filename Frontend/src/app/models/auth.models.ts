export interface LoginRequest {
	login: string;
	password: string;
}

export interface AuthResponse {
	token: string;
	login: string;
}
