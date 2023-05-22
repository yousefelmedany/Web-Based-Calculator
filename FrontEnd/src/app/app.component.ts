import { Component } from '@angular/core';
import { evaluator } from './calc/evaluator';
import {HttpClient} from "@angular/common/http";
import { __values } from 'tslib';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Calculator';
  currvalue='';
  toshow='0';
  constructor(private calc:evaluator){}

  writetoinput(value:string){
    if(this.currvalue==''){
      if(value!='+'&&value!='x'&&value!='÷'){
        this.currvalue=this.currvalue+value;
        this.toshow=this.currvalue;
      }
    }
    else{
    if(this.currvalue.length==1&&this.currvalue=='-'){
        if(value=='x'||value=='+'||value=='÷'){
          this.currvalue='';this.toshow='0';
          }
        else if(value!='-'){
          this.currvalue=this.currvalue+value;
          this.toshow=this.currvalue;
        }
      }
    else{
      if(value=='-'||value=='+'||value=='x'||value=='÷'){
        if(this.currvalue.charAt(this.currvalue.length-1)=='x'
        ||this.currvalue.charAt(this.currvalue.length-1)=='+'
        ||this.currvalue.charAt(this.currvalue.length-1)=='-'
        ||this.currvalue.charAt(this.currvalue.length-1)=='÷'){
          this.currvalue=this.currvalue.substring(0,this.currvalue.length-1)+value;
          this.toshow=this.currvalue;
        }
        else{
          this.currvalue=this.currvalue+value;
          this.toshow=this.currvalue;
        }
      }else{
        if(this.currvalue.charAt(this.currvalue.length-3)!='^'&&this.currvalue.charAt(this.currvalue.length-2)!='^'){
        this.currvalue=this.currvalue+value;
        this.toshow=this.currvalue;}
      }

    }
    }
}

clear(){
this.currvalue='';
this.toshow='0';
}

back(){
if(this.toshow!='0'){  
this.currvalue=this.currvalue.slice(0,-1);
this.toshow=this.currvalue;}

if(this.currvalue==''){
  this.toshow='0';
}
}

equal(){
  this.serverside(this.currvalue);
  }

calcvalue(val:any){
  this.toshow=eval(val);
}

serverside(s:string){
  let res=this.calc.send(s,"http://localhost:8080/").subscribe(result=>{this.toshow=result,this.currvalue=result});
}


insertroot(){
  if(this.currvalue.charAt(this.currvalue.length-1)=='+'
  ||this.currvalue.charAt(this.currvalue.length-1)=='x'
  ||this.currvalue.charAt(this.currvalue.length-1)=='÷'
  ||this.currvalue.charAt(this.currvalue.length-1)=='-'
  ||this.currvalue==''){
    this.currvalue=this.currvalue+'√';
    this.toshow=this.currvalue;
  }
}

insertsquare(){
  if(this.currvalue.charAt(this.currvalue.length-1)!='√'
  &&this.currvalue.charAt(this.currvalue.length-1)!='.'
  &&this.currvalue.charAt(this.currvalue.length-1)!='÷'
  &&this.currvalue.charAt(this.currvalue.length-1)!='x'
  &&this.currvalue.charAt(this.currvalue.length-1)!='-'
  &&this.currvalue.charAt(this.currvalue.length-2)!='^'
  &&this.currvalue.charAt(this.currvalue.length-3)!='^'
  &&this.currvalue.charAt(this.currvalue.length-1)!='+'
  &&this.currvalue!=''){
    this.currvalue=this.currvalue+"^2";
    this.toshow=this.currvalue;
  }
}

insertinverse(){
  if(this.currvalue.charAt(this.currvalue.length-1)!='√'
  &&this.currvalue.charAt(this.currvalue.length-1)!='.'
  &&this.currvalue.charAt(this.currvalue.length-1)!='÷'
  &&this.currvalue.charAt(this.currvalue.length-1)!='x'
  &&this.currvalue.charAt(this.currvalue.length-1)!='-'
  &&this.currvalue.charAt(this.currvalue.length-2)!='^'
  &&this.currvalue.charAt(this.currvalue.length-3)!='^'
  &&this.currvalue.charAt(this.currvalue.length-1)!='+'
  &&this.currvalue!=''){
    this.currvalue=this.currvalue+"^-1";
    this.toshow=this.currvalue;
  }
}
insertpercecent(){
  if(this.currvalue.charAt(this.currvalue.length-1)!='√'
  &&this.currvalue.charAt(this.currvalue.length-1)!='.'
  &&this.currvalue.charAt(this.currvalue.length-1)!='÷'
  &&this.currvalue.charAt(this.currvalue.length-1)!='x'
  &&this.currvalue.charAt(this.currvalue.length-1)!='-'
  &&this.currvalue.charAt(this.currvalue.length-1)!='+'
  &&this.currvalue.charAt(this.currvalue.length-1)!='P'
  &&this.currvalue!=''){
    this.currvalue=this.currvalue+"P";
    this.toshow=this.currvalue;
  }
}

reversesign(){
  let res=this.calc.send(this.currvalue,"http://localhost:8080/r/").subscribe(result=>{this.toshow=result,this.currvalue=result});
}

}