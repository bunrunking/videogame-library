import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Search } from './search/search';
import { Chat } from './chat/chat';

export const routes: Routes = [
    {path: '', redirectTo: 'home', pathMatch: 'full'},
    {path: 'home', component: Home},
    {path: 'search', component: Search},
    {path: 'chat', component: Chat},
    {path: 'about', component: Home}
];
