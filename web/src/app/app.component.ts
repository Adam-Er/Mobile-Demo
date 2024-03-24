import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'web';

  result: number | undefined;

  constructor(
    private api: ApiService
  ) {
  }

  getResult(numA: number, numB: number) {
     this.api.add(numA, numB).then((res) => {
      this.result = res
     });
  }
}
