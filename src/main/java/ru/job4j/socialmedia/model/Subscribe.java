package ru.job4j.socialmedia.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subscribes")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_subscriber_id")
    private User userSubscriber;

    @ManyToOne
    @JoinColumn(name = "user_to_id")
    private User userTo;
}
