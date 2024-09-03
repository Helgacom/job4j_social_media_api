package ru.job4j.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.dto.UserDto;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.service.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        var rsl = userService.findAll();
        if (!rsl.isEmpty()) {
            return ResponseEntity.ok(rsl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> findWithPosts(@RequestBody List<Long> userIds) {
        var rsl = userService.findByUserIdsList(userIds);
        if (!rsl.isEmpty()) {
            return ResponseEntity.ok(rsl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
