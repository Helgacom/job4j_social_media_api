package ru.job4j.socialmedia.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "friends")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_offer_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_accept_id")
    private User friend;

    private boolean status;
}
