import { Component } from '@angular/core';
import { MatNavList } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-sidebar-navigation',
  imports: [MatNavList, MatIconModule, RouterLink],
  templateUrl: './sidebar-navigation.html',
  styleUrls: ['./sidebar-navigation.scss']
})
export class SidebarNavigation {
  public routeLinks = [
    { name: 'Home', link: 'home', icon: 'home' },
    { name: 'Search', link: 'search', icon: 'search' },
    { name: 'Chat', link: 'chat', icon: 'chat' },
    { name: 'About', link: 'about', icon: 'info' }
  ];
}
