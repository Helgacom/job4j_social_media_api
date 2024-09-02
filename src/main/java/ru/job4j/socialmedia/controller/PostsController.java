package ru.job4j.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.service.PostService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getAll() {
        return postService.findAll();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getByUserId(@PathVariable("userId") Long userId) {
        return postService.findByUserId(userId);
    }
}
