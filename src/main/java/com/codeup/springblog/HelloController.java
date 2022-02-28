package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

//    use curly braces to establish Path Variables in the mapping definition.
// get and url {path variable}
    @GetMapping("/hello/{name}")

//    Use annotation to get the value of the Path Variable.
//    whatever is returned is the body
    @ResponseBody
    public String hello(@PathVariable String name){
        return "<h1>Hello  " + name + "!<h1>";
    }

// REQUEST MAPPING
    @RequestMapping(path = "/increment/{number}", method = RequestMethod.GET)
    @ResponseBody
    public String increment(@PathVariable int number) {
        return number + " plus one is " + (number + 1) + "!";


    }

}
