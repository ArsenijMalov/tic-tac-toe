import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {CommonModule} from '@angular/common';  // Это важно для директив Angular, таких как *ngIf

@Component({
  selector: 'app-root',
  standalone: true,  // Указываем, что компонент standalone
  imports: [HttpClientModule, CommonModule],  // Импортируем необходимые модули
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  sessionId: string = '';
  board: string[][] = [
    ['', '', ''],
    ['', '', ''],
    ['', '', '']
  ];
  gameStatus: string = 'Game in Progress';
  result: string = '';
  moveHistory: string[] = [];

  constructor(private http: HttpClient,  private cdRef: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    // Логика инициализации компонента, если нужно
  }

  startSimulation(): void {
    // Запрос на создание новой игровой сессии
    this.http.post<string>('/api/sessions', {}).subscribe(response => {
      this.sessionId = response;
      this.simulateGame();
      this.http.post<string>('/api/sessions/' + this.sessionId + '/simulate', {}).subscribe(response => {
          console.log(response);
      });
    });
  }

  simulateGame(): void {
    // Подключаемся к SSE, чтобы получать обновления о ходе игры

    console.log(this.sessionId);
    const eventSource = new EventSource(`/api/sessions/${this.sessionId}`);

    eventSource.onmessage = (event) => {
      const gameData = JSON.parse(event.data);
      console.log(gameData);
      this.updateBoard(gameData);
    };

    eventSource.onerror = () => {
      console.error("Ошибка подключения к SSE");
    };
  }

  updateBoard(gameData: any): void {
    this.board = gameData.board;
    this.gameStatus = gameData.statusName;
    this.result = gameData.result;
    this.moveHistory.push(gameData.lastMove);
    console.log(this.board);
    this.cdRef.detectChanges();
  }
}
