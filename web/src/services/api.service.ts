import { Injectable } from '@angular/core';
import { CalculatorClient } from '../proto/CalcServiceClientPb';
import { CalcRequest, CalcReply } from '../proto/calc_pb';


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private client = new CalculatorClient('');

  constructor() {
  }

  add(numA: number, numB: number): Promise<number> {
    return new Promise((resolve, reject) => {
      const request = new CalcRequest();
      request.setNumberA(numA);
      request.setNumberB(numB);
      console.log(request);
      this.client.plus(request, {}, (err: any, response: CalcReply) => {
        if (err) {
          console.log(err)
          return reject(err);
        }
        console.log(response);
        resolve(response?.getResult() || 0);
      });
    });
  }
}