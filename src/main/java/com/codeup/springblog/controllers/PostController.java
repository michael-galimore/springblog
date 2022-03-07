package com.codeup.springblog.controllers;

import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.models.Post;
import com.codeup.springblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    private PostRepository postsDao;
    private UserRepository usersDao;
//    inject it into your posts controller.
    private EmailService emailService;

    public PostController(PostRepository postsDao, UserRepository usersDao, EmailService emailService){
        this.postsDao = postsDao;
        this.usersDao = usersDao;
        this.emailService = emailService;
    }
    @GetMapping("/posts")
    public String viewPosts(Model model){
//        ArrayList<Post> allPosts = new ArrayList<>();
//        Post post2 = new Post(2, "test", "can you see me?");
//        Post post3 = new Post(2, "test", "can you see me?");
//        Post post4 = new Post(2, "test", "can you see me?");
//
//        allPosts.add(post2);
//        allPosts.add(post3);
//        allPosts.add(post4);
        model.addAttribute("allPosts", postsDao.findAll());
        return "posts/index";
    }


    @GetMapping("/posts/{id}")
    public String individualPost(@PathVariable long id, Model model){
        model.addAttribute("singlePost", postsDao.getById(id));

        return "posts/show";
    }


    @GetMapping("/posts/create")
    public String showCreateForm(Model model){
        model.addAttribute("newPost", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String submitCreateForm(@ModelAttribute Post newPost){
//        Post newPost = new Post(title, body);
        newPost.setUser(usersDao.getById(1L));
        emailService.prepareAndSend(newPost, "You created a new post"," You just created a post today.");
        postsDao.save(newPost);
        return "redirect:/posts";
    }

    @GetMapping("/posts{id}/edit")
    public String showEditForm(@PathVariable long id, Model model){
        Post postToEdit = postsDao.getById(id);
        model.addAttribute("postToEdit", postToEdit);
        return "posts/edit";
    }

    @PostMapping("posts/{id}/edit")
    public String submitEdit(@ModelAttribute Post postToEdit, @PathVariable long id){
//        Post postToEdit = postsDao.getById(id);
//        postToEdit.setTitle(title);
//        postToEdit.setBody(body);
        postsDao.save(postToEdit);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/delete")
    public String delete (@PathVariable long id){
        postsDao.deleteById(id);
        return "redirect:/posts";
    }



}
