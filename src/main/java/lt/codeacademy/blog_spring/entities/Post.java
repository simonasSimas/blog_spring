package lt.codeacademy.blog_spring.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "date_edited")
    private LocalDateTime dateEdited;

    public Post() {
    }
}
