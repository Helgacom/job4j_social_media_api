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
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.dto.UserDto;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.service.UserService;

import java.util.List;

@Tag(name = "UsersController", description = "UsersController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    @Operation(
            summary = "Get all Users",
            description = "Get all Users list. The response is User objects list.",
            tags = { "User", "getAll" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        var rsl = userService.findAll();
        if (!rsl.isEmpty()) {
            return ResponseEntity.ok(rsl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Get UserDto list with posts",
            description = "Get UserDto list with posts by passed Ids list. The response is UserDto objects list.",
            tags = { "User", "findUsersWithPosts" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> findUsersWithPosts(@RequestBody List<Long> userIds) {
        var rsl = userService.findByUserIdsList(userIds);
        if (!rsl.isEmpty()) {
            return ResponseEntity.ok(rsl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
