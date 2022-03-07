package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {
    private PostRepository postsDao;

    public PostController(PostRepository postsDao){
        this.postsDao = postsDao;
    }
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
    public String submitCreateForm(@RequestParam(name= "title") String title, @RequestParam (name="body") String body){
        Post newPost = new Post(title, body);
        postsDao.save(newPost);

        return "redirect:/posts";
    }

    @GetMapping("posts{id}/edit")
    public String showEditForm(@PathVariable long id, Model model){
        Post postToEdit = postsDao.getById(id);
        model.addAttribute("postToEdit", postToEdit);
        return "posts/edit";
    }

    @PostMapping("posts/{id}/edit")
    public String submitEdit(@RequestParam(name ="title") String title, @RequestParam(name="body") String body, @PathVariable long id){
        Post postToEdit = postsDao.getById(id);
        postToEdit.setTitle(title);
        postToEdit.setBody(body);
        postsDao.save(postToEdit);
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}/delete")
    public String delete (@PathVariable long id){
        postsDao.deleteById(id);
        return "redirect:/posts";
    }



}
