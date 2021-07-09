package lt.codeacademy.blog_spring.entities;

import lombok.Data;
import lt.codeacademy.blog_spring.utils.UserRole;

import javax.persistence.*;

@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

//    private Post postId;

    public User() {
    }
}
