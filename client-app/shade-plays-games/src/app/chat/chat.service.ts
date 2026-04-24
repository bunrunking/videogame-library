import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ChatResponse } from './chatresponse.model';
import { Message } from './message';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiUrl = '/api/chat';

  constructor(private http: HttpClient) { }

  submitChat(messages: Message[]) {
    return this.http.post<Message[]>(this.apiUrl, messages);
  }
}
