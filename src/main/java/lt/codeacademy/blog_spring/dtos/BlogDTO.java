package lt.codeacademy.blog_spring.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class BlogDTO {

    private Long id;

    @NotBlank
    private String title;

    private String contents;

    private LocalDateTime dateCreated;

    private LocalDateTime dateEdited;
}
