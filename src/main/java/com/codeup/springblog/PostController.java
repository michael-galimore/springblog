package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {
    @GetMapping("/posts")
    @ResponseBody
    public String viewPosts(Model model){
        ArrayList<Post> allPosts = new ArrayList<>();
        Post post2 = new Post(2, "test", "can you see me?");
        Post post3 = new Post(2, "test", "can you see me?");
        Post post4 = new Post(2, "test", "can you see me?");

        allPosts.add(post2);
        allPosts.add(post3);
        allPosts.add(post4);
        model.addAttribute("allpost",allPosts);
        return "posts/index";
    }


    @GetMapping("/posts/{id}")
//    @ResponseBody
    public String individualPost(@PathVariable long id, Model model){
        Post post1 = new Post(1, "title", "Hi there");
        model.addAttribute("singlePost", post1);

        return "/posts/show";
    }


    @GetMapping("/posts/create")
    @ResponseBody
    public String showCreateForm(){
        return "post/create";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String submitCreateForm(){
        return "create a new post";
    }



}
