package lt.codeacademy.blog_spring.services;

import lt.codeacademy.blog_spring.dtos.BlogDTO;
import lt.codeacademy.blog_spring.entities.Blog;
import lt.codeacademy.blog_spring.entities.Comment;
import lt.codeacademy.blog_spring.repositories.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class BlogService {

    private final CommentService commentService;

    private final BlogRepository blogRepository;

    public BlogService(CommentService commentService, BlogRepository blogRepository) {
        this.commentService = commentService;
        this.blogRepository = blogRepository;
    }

    public Page<Blog> getPostsPaginatedByTitle(Pageable pageable, String title) {
        return title!= null ? blogRepository.getPostsByTitleContaining(pageable, title) : blogRepository.findAll(pageable);
    }



    @Transactional
    public Blog saveBlog(BlogDTO blogDTO){
        Blog blog = new Blog(blogDTO);
        return blogRepository.save(blog);
    }

    public Blog getBlogById (Long id) {
        return blogRepository.getBlogById(id);
    }

    public void deleteBlog(Blog blog) {
        for ( Comment comment : commentService.getAllComments()){
            if (comment.getBlog().getId().equals(blog.getId())){
                commentService.deleteComment(comment);
            }
        }
        blogRepository.delete(blog);
    }

}
