package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.service.PostService;

import java.util.List;

@Tag(name = "PostsController", description = "PostsController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostService postService;

    @Operation(
            summary = "Get all Posts",
            description = "Get all Posts list. The response is Post objects list.",
            tags = { "Post", "getAll" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content (array = @ArraySchema(schema = @Schema(implementation = Post.class)),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Post>> getAll() {
        var rsl = postService.findAll();
        if (!rsl.isEmpty()) {
            return ResponseEntity.ok(rsl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Get all Posts by userIg",
            description = "Get all User Posts by its userId. The response is Post objects list.",
            tags = { "Post", "getByUserId" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content (array = @ArraySchema(schema = @Schema(implementation = Post.class)),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Post>> getByUserId(@PathVariable("userId") Long userId) {
        var rsl = postService.findByUserId(userId);
        if (!rsl.isEmpty()) {
            return ResponseEntity.ok(rsl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
