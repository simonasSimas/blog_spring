package lt.codeacademy.blog_spring.services;

import lt.codeacademy.blog_spring.entities.Comment;
import lt.codeacademy.blog_spring.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    public final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
