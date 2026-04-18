import { Component, signal, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';

interface Message {
  sender: 'user' | 'bot';
  text: string;
  timestamp: Date;
}

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

  messages = signal<Message[]>([
    { sender: 'bot', text: 'Hello! How can I help you with games today?', timestamp: new Date() }
  ]);
  inputValue = signal('');

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  sendMessage() {
    if (this.inputValue().trim()) {
      this.messages.update(msgs => [
        ...msgs,
        { sender: 'user', text: this.inputValue().trim(), timestamp: new Date() }
      ]);
      this.inputValue.set('');
      // Simulate bot response
      setTimeout(() => {
        this.messages.update(msgs => [
          ...msgs,
          { sender: 'bot', text: 'Thanks for your message! This is a demo response.', timestamp: new Date() }
        ]);
      }, 1000);
    }
  }

  private scrollToBottom(): void {
    if (this.messagesContainer) {
      this.messagesContainer.nativeElement.scrollTop = this.messagesContainer.nativeElement.scrollHeight;
    }
  }
}
