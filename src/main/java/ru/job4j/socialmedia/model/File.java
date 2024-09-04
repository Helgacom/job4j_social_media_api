package ru.job4j.socialmedia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "name не может быть пустым")
    private String name;

    @NotBlank(message = "path не может быть пустым")
    @Column(unique = true, nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
