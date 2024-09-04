package ru.job4j.socialmedia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "name не может быть пустым")
    @Length(min = 3,
            message = "name должно быть не менее 3 символов")
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank(message = "login не может быть пустым")
    @Length(min = 3,
            max = 20,
            message = "login должен быть не менее 3 и не более 20 символов")
    @Column(unique = true, nullable = false)
    private String login;

    @NotBlank(message = "password не может быть пустым")
    private String password;
}