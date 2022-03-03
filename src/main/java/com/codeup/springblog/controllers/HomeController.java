package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
//    @GetMapping("/")
//    @ResponseBody
//    public String landing(){
//        return "This is the landing page";
//    }

    //    shows the html
    @GetMapping("/roll-dice")
    public String showDice() {
        return "roll-dice";
    }

    @RequestMapping(path = "/roll-dice/{n}", method = RequestMethod.GET)
    @ResponseBody
    public String guess(@PathVariable int n, Model model) {
        int random = (int) (Math.floor(Math.random() * 6) + 1);

        if(n == random){
            model.addAttribute("result", "You guessed correct");
        }else if (n != random){
            model.addAttribute("result", "Try again!");
        }
        return "roll-dice";
    }



}

