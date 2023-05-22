package com.example.demo.calculator;
import org.springframework.web.bind.annotation.RestController;

import java.util.Stack;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

    @RestController
    public class calccontroller{
        @CrossOrigin
        @GetMapping("/{s}")
        public static String EvaluateExpression(@PathVariable String s){
            char[] expression = s.toCharArray();
 
           Stack<Double> values = new
                                 Stack<Double>();
    
           Stack<Character> ops = new
                                 Stack<Character>();
            if(expression[expression.length-1]=='x'||expression[expression.length-1]=='+'
            ||expression[expression.length-1]=='-'||expression[expression.length-1]=='÷'){return "Error";}
           for (int i = 0; i < expression.length; i++)
           {
                
               if (expression[i] == ' ')
               continue;
               if(expression[0]=='-'&&i==0){
                StringBuffer sbufn = new
                               StringBuffer();
                    int count=0;
                   sbufn.append(expression[i++]);
                   while (i < expression.length &&((expression[i] >= '0' &&expression[i] <= '9')||expression[i]=='.')){
                       if(expression[i]=='.'){count++;}
                    sbufn.append(expression[i++]);}
                    
                   if(count>1){return "Error";} 
                   values.push(Double.parseDouble(sbufn.
                                         toString()));
                    i--;
               }
    
               else if ((expression[i] >= '0' &&expression[i] <= '9')||
               expression[i]=='.'||expression[i]=='√')
               {
                   StringBuffer sbuf = new
                               StringBuffer();
                    int count=0;
                    boolean checkroot=false;
                    boolean checkpower1=false;
                    boolean checkpower2=false;
                    boolean checkpercent=false;

                    while (i < expression.length &&((expression[i] >= '0' &&expression[i] <= '9')||
                   expression[i]=='.'||expression[i]=='P'||expression[i]=='√'||expression[i]=='^')){
                       if(expression[i]=='.'){count++;}
                       if(expression[i]=='√'){checkroot=true;}
                       if(expression[i]=='^'){checkpower1=true;
                            if(expression[i+1]=='2'){
                                checkpower2=true;
                            }else{
                                checkpower2=false;
                                i++;
                            }
                        }
                       if(expression[i]=='P'){checkpercent=true;}
                    sbuf.append(expression[i++]);}
                   if(count>1){return "Error";}
                   if(sbuf.charAt(sbuf.length()-1)=='√'){
                    return "Error";
                   }
                   if(checkroot){
                        String sn=sbuf.substring(1, sbuf.length());
                        double res;
                        if(checkpower1){
                            if(checkpercent){
                                sn=sn.substring(0, sn.length()-1);
                            }
                            if(checkpower2){
                                sn=sn.substring(0, sn.length()-2);
                                res= Math.pow(Double.parseDouble(sn.toString()), 2);
                                values.push(Math.sqrt(res));
                            }else{
                                sn=sn.substring(0, sn.length()-2);
                                res= Math.pow(Double.parseDouble(sn.toString()), -1);
                                values.push(Math.sqrt(res));
                            }
                        }else{
                            if(checkpercent){
                                sn=sn.substring(0, sn.length()-1);
                            }
                            res=Double.parseDouble(sn.toString());
                        }
                        values.push(Math.sqrt(res));
                        if(checkpercent){
                        double x=values.pop()/100;
                        values.push(x);
                        }
                    }
                    else if(checkpower1){
                        String sq;
                        if(checkpercent){
                            sq=sbuf.substring(0, sbuf.length()-1);
                        }
                        if(checkpower2){
                            sq=sbuf.substring(0, sbuf.length()-2);
                            double res= Math.pow(Double.parseDouble(sq.toString()), 2);
                            values.push(res);
                        }else{
                            sq=sbuf.substring(0, sbuf.length()-2);
                            double res= Math.pow(Double.parseDouble(sq.toString()),-1.0);
                            values.push(res);
                        }
                        if(checkpercent){
                            double x=values.pop()/100;
                            values.push(x);
                        
                            }
                    }
                    else{ 
                    if(checkpercent){
                        String s1=sbuf.substring(0, sbuf.length()-1);
                        double x=Double.parseDouble(s1.toString());
                        x=x/100;
                        values.push(x);
                    }
                    else{
                    values.push(Double.parseDouble(sbuf.
                    toString()));
                    }
                    }

                     i--;
               }
    
               else if (expression[i] == '+' ||
                        expression[i] == '-' ||
                        expression[i] == 'x' ||
                        expression[i] == '÷')
               {

                   while (!ops.empty() &&hasPrecedence(expression[i],ops.peek())){
                    double b=values.pop();
                    double a=values.pop();
                    char x=ops.pop();
                    if(x=='÷'&&b==0){
                        return "Error";
                    }
                     values.push(applyOp(x,b,a));}
    
                   ops.push(expression[i]);
               }
           }
    
           while (!ops.empty()){
            double b=values.pop();
            double a=values.pop();
            char x=ops.pop();
            if(x=='÷'&&b==0){
                return "Error";
            }
            values.push(applyOp(x,b,a));

            }
            
           String finalvalue=values.pop().toString();
           if (finalvalue.charAt(finalvalue.length()-1)=='0'
           &&finalvalue.charAt(finalvalue.length()-2)=='.')
           {finalvalue=finalvalue.substring(0, finalvalue.length()-2);}
           return finalvalue;
       }
    
       public static boolean hasPrecedence(
                              char op1, char op2)
       {

           if ((op1 == 'x' || op1 == '÷') &&
               (op2 == '+' || op2 == '-'))
               return false;
           else
               return true;
       }
    
       public static double applyOp(char op,
                              double b, double a)
       {
           switch (op)
           {
           case '+':
               return a + b;
           case '-':
               return a - b;
           case 'x':
               return a * b;
           case '÷':
               return a / b;
           }
           return 0;
       }


       @CrossOrigin
       @GetMapping("/r/{s}")
       public String Reverseval(@PathVariable String s){
            String ans=EvaluateExpression(s);
            if(ans.charAt(0)=='-'){
                ans=ans.substring(1,ans.length());
            }
            else{
                ans="-"+ans;  
            }
            return ans;
       }

	}


    

