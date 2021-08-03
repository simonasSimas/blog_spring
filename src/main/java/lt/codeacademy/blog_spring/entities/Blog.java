package lt.codeacademy.blog_spring.entities;

import lombok.Data;
import lt.codeacademy.blog_spring.dtos.BlogDTO;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post")
@DynamicUpdate
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String contents;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "date_edited")
    private LocalDateTime dateEdited;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    public Blog(BlogDTO blogDTO) {
        this.id = blogDTO.getId();
        this.title = blogDTO.getTitle();
        this.contents = blogDTO.getContents();
        this.dateCreated = blogDTO.getDateCreated();
        this.dateEdited = blogDTO.getDateEdited();
        this.user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Blog() {
    }
}
