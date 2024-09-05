package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.dto.UserDto;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.security.userdetails.UserDetailsImpl;
import ru.job4j.socialmedia.service.UserService;

@Tag(name = "UserController", description = "UserController management APIs")
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Retrieve a User by userId",
            description = "Get a User object by specifying its userId. The response is User object with userId.",
            tags = { "User", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<User> get(@PathVariable("userId")
                                        @NotNull
                                        @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                        Long userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Create a User from userDto",
            description = "Create new User from passed DTO object. The response is saved new User.",
            tags = { "User", "save" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    @PostAuthorize("#user.login == authentication.principal.login")
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto user) {
        userService.create(user);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(user);
    }

    @Operation(
            summary = "Update a User from userDto",
            description = "Update saved User from passed DTO object.",
            tags = { "User", "update" })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Unable to update")})
    @PutMapping
    @PreAuthorize("user.login == authentication.principal.login")
    public ResponseEntity<Void> update(@Valid @RequestBody UserDto user) {
        if (userService.updateFromDto(user)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Change a User from userDto",
            description = "Change saved User from passed DTO object.",
            tags = { "User", "change" })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Unable to change")})
    @PatchMapping
    @PreAuthorize("#user.login == authentication.principal.login")
    public ResponseEntity<Void> change(@Valid @RequestBody UserDto user) {
        if (userService.updateFromDto(user)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Delete a User by Id",
            description = "Delete a User object by specifying its userId.",
            tags = { "User", "removeById" })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Unable to remove")})
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeById(@PathVariable Long userId) {
        if (userService.deleteById(userId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Return current authenticated User",
            description = "Get a User details object.",
            tags = { "User", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserDetailsImpl.class), mediaType = "application/json") })})
    @GetMapping("/current")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDetailsImpl> getCurrentUserDetails(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userDetails);
    }
}
