package ru.job4j.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.service.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAll() {
        return userService.findAll();
    }
}
