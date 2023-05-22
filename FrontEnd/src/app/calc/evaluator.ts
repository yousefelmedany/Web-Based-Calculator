import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
@Injectable({
  providedIn: 'root'
})


export class evaluator{
    constructor(private http:HttpClient){}
    send(s:string,url:string){
        return this.http.get(url+s,{responseType:'text'})

    }




}