package edu.project.myrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @Autowired
    PostService postService;

    Logger log= LoggerFactory.getLogger(HelloController.class);

    @GetMapping("")
    public String hello(){
        return "Hello world!";
    }

    @GetMapping("/hello")
    public String hello(String name){
        return "Hello " + name+ "!";
    }

    @GetMapping("/makePost")
    public String makePost(@RequestBody Post post){
        log.info("adding post with id {}",post.id());
        postService.addPost(post);
        return "added";
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable int id){
        Post p = postService.getPost(id);
        if (p!=null) return ResponseEntity.ok(p);
        return ResponseEntity.notFound().build();
    }
}