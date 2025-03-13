import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HttpClientModule, CommonModule],
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
  }

  startSimulation(): void {
    this.http.post<string>('/api/v1/sessions', {}).subscribe(response => {

      console.log('resp: ' + response);
      this.sessionId = response;
      this.simulateGame();
      this.http.post<string>('/api/v1/sessions/' + this.sessionId + '/simulate', {}).subscribe(response => {
          console.log(response);
      });
    });
  }

  simulateGame(): void {

    const eventSource = new EventSource(`/api/v1/sessions/${this.sessionId}`);

    eventSource.onmessage = (event) => {
      const gameData = JSON.parse(event.data);
      console.log(gameData);
      this.updateBoard(gameData);
    };

    eventSource.onerror = () => {
      console.error("Error connection to SSE");
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
