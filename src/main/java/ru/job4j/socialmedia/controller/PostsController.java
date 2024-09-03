package ru.job4j.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Post>> getAll() {
        var rsl = postService.findAll();
        if (!rsl.isEmpty()) {
            return ResponseEntity.ok(rsl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> getByUserId(@PathVariable("userId") Long userId) {
        var rsl = postService.findByUserId(userId);
        if (!rsl.isEmpty()) {
            return ResponseEntity.ok(rsl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
