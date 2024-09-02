package ru.job4j.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.service.PostService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable("postId") Long postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostDto> save(@RequestBody PostDto post) {
        postService.create(post);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(post);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody PostDto post) {
        if (postService.updateFromDto(post)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@RequestBody PostDto post) {
        postService.updateFromDto(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeById(@PathVariable Long postId) {
        if (postService.deleteById(postId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
