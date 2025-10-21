import { Component } from '@angular/core';
import { MatToolbar, MatToolbarRow } from '@angular/material/toolbar';
import { SidebarNavigation } from '../sidebar-navigation/sidebar-navigation';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatToolbar, MatToolbarRow, SidebarNavigation
  ],
  templateUrl: './home.html',
  styleUrls: ['./home.scss']
})
export class Home {

}
