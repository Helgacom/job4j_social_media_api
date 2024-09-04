package ru.job4j.socialmedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Title", example = "Congratulation")
    private String title;

    @Schema(description = "Text content", example = "Today is...")
    private String text;

    @Schema(description = "Date of creation", example = "2023-10-15T15:15:15")
    private LocalDateTime created;

    @Schema(description = "Post author")
    private User user;

    @Schema(description = "Post files")
    private final List<FileDto> fileList = new ArrayList<>();
}
