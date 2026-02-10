import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { ContestList } from './pages/contest-list/contest-list';
import { ContestCreate } from './pages/contest-create/contest-create';
import { ProblemCreate } from './pages/problem-create/problem-create';
import { Profile } from './pages/profile/profile';
import { ProblemView } from './pages/problem-view/problem-view';

export const routes: Routes = [
	{ path: '', redirectTo: 'contests', pathMatch: 'full' },
	{ path: 'login', component: Login },
	{ path: 'contests', component: ContestList },
	{ path: 'contests/create', component: ContestCreate },
	{ path: 'contests/:id', loadComponent: () => import('./pages/contest-view/contest-view').then(m => m.ContestView) },
	{ path: 'contests/:contestId/add-problem', component: ProblemCreate },
	{ path: 'problems/:id', component: ProblemView },
	{ path: 'profile', component: Profile },
	// { path: 'profile', loadComponent: () => import('./pages/profile/profile').then(m => m.Profile) }
];
