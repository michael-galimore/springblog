package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MathController {

    @RequestMapping (path ="/add/{num1}/and/{num2}", method = RequestMethod.GET)
    @ResponseBody
    public String add(@PathVariable int num1, @PathVariable int num2){
        return "The sum of " + num1 + " and " + num2 + " is " + (num1 + num2);
    }
       @RequestMapping("/subtract/{number1}/from/{number2}")
    @ResponseBody
    public String subtract(@PathVariable int number1, @PathVariable int number2){
        return "Subtracting " + number1 + " from " + number2 + " is " + (number1-number2);
       }

       @RequestMapping("/multiply/{number1}/and/{number2}")
    @ResponseBody
    public String multiply(@PathVariable int number1, @PathVariable int number2){
        return "By multiplying " + number1 + " by " + number2 + ", you get " + (number1*number2);
       }

       @GetMapping("/divide/{number1}/by/{number2}")
    @ResponseBody
    public double divide(@PathVariable double number1, @PathVariable double number2) {
           return (number1 / number2);
       }

}
