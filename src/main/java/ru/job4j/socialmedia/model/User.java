package ru.job4j.socialmedia.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.job4j.socialmedia.security.models.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
@Schema(description = "User Model Information")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Schema(description = "User name", example = "Vetrov Victor")
    @NotBlank(message = "name не может быть пустым")
    @Length(min = 3,
            message = "name должно быть не менее 3 символов")
    @Column(unique = true, nullable = false)
    private String name;

    @Schema(description = "User login", example = "Vetrov@res.com")
    @NotBlank(message = "login не может быть пустым")
    @Length(min = 3,
            max = 20,
            message = "login должен быть не менее 3 и не более 20 символов")
    @Column(unique = true, nullable = false)
    private String login;

    @Schema(description = "User password", example = "abc123")
    @NotBlank(message = "password не может быть пустым")
    private String password;

    @Schema(description = "User security statuses")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}