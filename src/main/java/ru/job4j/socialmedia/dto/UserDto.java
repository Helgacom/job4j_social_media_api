package ru.job4j.socialmedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;

    @Schema(description = "User name", example = "Vetrov Victor")
    private String name;

    @Schema(description = "User posts")
    private List<PostDto> posts;
}
