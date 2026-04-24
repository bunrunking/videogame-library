import { Component, signal, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { finalize } from 'rxjs';
import { ChatService } from './chat.service';
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
  isSending = signal(false);

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  sendMessage() {
    const text = this.inputValue().trim();
    if (!text || this.isSending()) {
      return;
    }

    this.isSending.set(true);
    this.messages.update(msgs => [
      ...msgs,
      { role: 'user', content: text, timestamp: new Date() }
    ]);
    this.inputValue.set('');

    // Send the last X messages to the backend for processing.
    this.chatService.submitChat(this.messages())
      .pipe(finalize(() => this.isSending.set(false)))
      .subscribe({
        next: (response: Message[]) => {
          this.messages.update(msgs => [
            ...msgs,
            ...response
          ]);
        },
        error: () => {
          // Optionally handle the error here.
        }
      });
  }

  private scrollToBottom(): void {
    if (this.messagesContainer) {
      this.messagesContainer.nativeElement.scrollTop = this.messagesContainer.nativeElement.scrollHeight;
    }
  }
}
