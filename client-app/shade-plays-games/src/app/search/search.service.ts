import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SearchResponse } from '../model/searchresponse.model';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private apiUrl = '/api/search';

  constructor(private http: HttpClient) { }

  searchGames(keyword: string) {
    return this.http.get<SearchResponse>(`${this.apiUrl}?keyword=${encodeURIComponent(keyword)}`);
  }
}
