package ru.job4j.socialmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.socialmedia.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private Long id;

    private String title;

    private String text;

    private LocalDateTime created;

    private User user;

    private final List<FileDto> fileList = new ArrayList<>();
}
