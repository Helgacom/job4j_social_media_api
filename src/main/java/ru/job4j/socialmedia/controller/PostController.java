package ru.job4j.socialmedia.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.service.PostService;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable("postId")
                                        @NotNull
                                        @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                        Long postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<PostDto> save(@Valid @RequestBody PostDto post) {
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
    public ResponseEntity<Void> update(@Valid @RequestBody PostDto post) {
        if (postService.updateFromDto(post)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping
    public ResponseEntity<Void> change(@Valid @RequestBody PostDto post) {
        if (postService.updateFromDto(post)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeById(@PathVariable Long postId) {
        if (postService.deleteById(postId)) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
