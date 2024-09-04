package ru.job4j.socialmedia.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Schema(description = "Post title", example = "Congratulation")
    @NotBlank(message = "title не может быть пустым")
    @Length(min = 3,
            message = "title должен быть не менее 3 символов")
    private String title;

    @Schema(description = "Post text content", example = "Today is...")
    private String text;

    @Schema(description = "Date of creation", example = "2023-10-15T15:15:15")
    private LocalDateTime created = LocalDateTime.now();

    @Schema(description = "Post author")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}


