package com.codeup.springblog;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringblogApplication.class)
@AutoConfigureMockMvc
public class PostsIntegrationTests {

    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    PostRepository postsDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception{

        testUser = userDao.findByUsername("testUser");

        // Creates the test user if not exists
        if(testUser == null){
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("password"));
            newUser.setEmail("testUser@mail.com");
            testUser = userDao.save(newUser);
        }

        // Throws a Post request to /login and expect a redirection to the Posts index page after being logged in
        httpSession = this.mvc.perform(post("/login").with(csrf())
                .param("username", "testUser")
                .param("password", "password"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/posts"))
                .andReturn()
                .getRequest()
                .getSession();
    }

    @Test
    public void contextLoads(){
        // Sanity Test, just to make sure the MVC bean is working
        assertNotNull(mvc);
    }

    @Test
    public void testIfUserSessionIsActive(){
        // It makes sure the returned session is not null
        assertNotNull(httpSession);
    }

//    CREATE
    @Test
    public void testCreatePost() throws Exception{
        // Makes a Post request to /posts/create and expect a redirection to the Post
        this.mvc.perform(
                post("/posts/create").with(csrf())
                        .session((MockHttpSession) httpSession)
                // Add all the required parameters to your request like this
                        .param("title", "test")
                        .param("body", "can you see this?"))
                .andExpect(status().is3xxRedirection());
    }



//    READ
    @Test
    public void testShowPost() throws Exception {

        Post existingPost = postsDao.findAll().get(0);

        // Makes a Get request to /posts/{id} and expect a redirection to the Posts show page
        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
        // Test the dynamic content of the page
                .andExpect(content().string(containsString(existingPost.getBody())));
    }

    @Test
    public void testPostsIndex() throws Exception{
        Post existingPost = postsDao.findAll().get(0);

        // Makes a Get request to /ads and verifies that we get some of the static text of the posts/index.html template and at least the title from the first Ad is present in the template.
        this.mvc.perform(get("/posts"))
                .andExpect(status().isOk())
        // Test the static content of the page
                .andExpect(content().string(containsString("Latest Posts")))
        // Test the dynamic content of the page
                .andExpect(content().string(containsString(existingPost.getTitle())));

    }


// UPDATE
    @Test
    public void testEditPost() throws Exception{
//        Gets the first Post for test purposes
        Post existingPost = postsDao.findAll().get(0);

//        Makes a POST request to /posts/{id}/edit and expect a redirection to the Post show page.
        this.mvc.perform(
                post("/posts/" + existingPost.getId() + "/edit").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("title", "edited title")
                .param("body", "edited body"))
                .andExpect(status().is3xxRedirection());


        //    Makes a GET request to /posts/{id} and expect a redirection to the Post show page
        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
        // Test the dynamic content of the page
                .andExpect(content().string(containsString("edited title")))
                .andExpect(content().string(containsString("edited body")));

    }

//    DELETE
    @Test
    public void testDeletePost() throws Exception{
//        Creates a test Post to be deleted
        this.mvc.perform(
                post("/posts/create").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("title", "post to be deleted")
                        .param("body", "this will not last long"))
                .andExpect(status().is3xxRedirection());

//        Get the recent Post that matches the title
        Post existingPost = postsDao.findByTitle("post to be deleted");

//        Makes a Post request to /posts/{id}/delete and expect a redirection to the Posts index
        this.mvc.perform(post("/posts/" + existingPost.getId() + "/delete").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("id", String.valueOf(existingPost.getId())))
                .andExpect(status().is3xxRedirection());
    }







}
