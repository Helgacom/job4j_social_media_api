package ru.job4j.socialmedia.model;

import jakarta.persistence.*;
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

    private String name;
    private String path;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
