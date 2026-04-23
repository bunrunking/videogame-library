import { Component, signal, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ChatService } from './chat.service';
import { ChatResponse } from './chatresponse.model';
import { Message } from './message';

@Component({
  selector: 'app-chat',
  imports: [
    MatCardModule,
    MatListModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    DatePipe
  ],
  templateUrl: './chat.html',
  styleUrl: './chat.scss'
})
export class Chat implements AfterViewChecked {
  @ViewChild('messagesContainer') private messagesContainer!: ElementRef;

  constructor(private chatService: ChatService) {}

  messages = signal<Message[]>([
    { role: 'assistant', content: 'Hello! How can I help you with games today?', timestamp: new Date() }
  ]);
  inputValue = signal('');

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  sendMessage() {
    if (this.inputValue().trim()) {
      this.messages.update(msgs => [
        ...msgs,
        { role: 'user', content: this.inputValue().trim(), timestamp: new Date() }
      ]);
      this.inputValue.set('');

      // Send the last X messages to the backend for processing.
      this.chatService.submitChat(this.messages()).subscribe((response: Message) => {
        this.messages.update(msgs => [
          ...msgs,
          response
        ]);
      });

    }
  }

  private scrollToBottom(): void {
    if (this.messagesContainer) {
      this.messagesContainer.nativeElement.scrollTop = this.messagesContainer.nativeElement.scrollHeight;
    }
  }
}
