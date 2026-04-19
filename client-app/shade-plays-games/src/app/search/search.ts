import { Component } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { SearchService } from './search.service';
import { SearchResponse } from './searchresponse.model';
import { AgGridAngular } from 'ag-grid-angular'; // Angular Data Grid Component
import type { ColDef } from 'ag-grid-community'; // Column Definition Type Interface
import { Game } from '../model/game.model';

@Component({
  selector: 'app-search',
  imports: [FormsModule, MatInputModule, MatCardModule, MatFormField, MatLabel, MatTableModule, AgGridAngular ],
  providers: [SearchService],
  standalone: true,
  templateUrl: './search.html',
  styleUrls: ['./search.scss']
})
export class Search {
  keyword: string = '';
  searchResults: SearchResponse | null = null;

  displayedColumns: string[] = ['name', 'imageUrl'];
  dataSource: Game[] = [];

  constructor(private searchService: SearchService) {}

  onSubmit(form: NgForm) {
    console.log(`Searching for: ${this.keyword}`);
    this.searchService.searchGames(this.keyword).subscribe((response: SearchResponse) => {
      this.searchResults = response;
      this.dataSource = response.results
    });
  }
}
