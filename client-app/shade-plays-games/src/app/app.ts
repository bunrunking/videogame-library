import { Component, signal } from '@angular/core';
import { SidebarNavigation } from './sidebar-navigation/sidebar-navigation';
import { MatSidenavContainer, MatSidenav, MatSidenavContent } from '@angular/material/sidenav';
import { MatToolbar, MatToolbarRow } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { RouterOutlet } from '@angular/router';
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community';

// Register all Community features
ModuleRegistry.registerModules([AllCommunityModule]);

/*
  Sidebar navigation component providing links to different sections of the app.
  Code snippet from: https://meganrook.medium.com/how-to-create-a-mini-nav-with-angular-materials-sidenav-dbc46ce4abf5
*/
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ MatSidenavContainer, MatSidenav, MatSidenavContent,
    MatToolbar, MatToolbarRow,
    MatIconModule,
    SidebarNavigation,
    RouterOutlet
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.scss']
})
export class App {
}
