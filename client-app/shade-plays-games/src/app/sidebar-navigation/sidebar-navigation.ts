import { Component } from '@angular/core';
import { MatNavList } from '@angular/material/list';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-sidebar-navigation',
  imports: [MatNavList, RouterLink],
  templateUrl: './sidebar-navigation.html',
  styleUrls: ['./sidebar-navigation.scss']
})
export class SidebarNavigation {
  public routeLinks = [
    { name: "Home", link: "home" },
    { name: "About", link: "about" }
  ];
}
