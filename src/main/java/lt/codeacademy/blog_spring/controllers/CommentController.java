package lt.codeacademy.blog_spring.controllers;

import lt.codeacademy.blog_spring.entities.Blog;
import lt.codeacademy.blog_spring.entities.Comment;
import lt.codeacademy.blog_spring.entities.User;
import lt.codeacademy.blog_spring.services.BlogService;
import lt.codeacademy.blog_spring.services.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/comment")
public class CommentController {

    public final CommentService commentService;

    public final BlogService blogService;

    public CommentController(CommentService commentService, BlogService blogService) {
        this.commentService = commentService;
        this.blogService = blogService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/create")
    public String saveComment(@PathVariable("id") Blog blog, @AuthenticationPrincipal User currentUser, @Valid Comment comment, BindingResult bindingResult, HttpSession session) {
        comment.setId(null);
        comment.setBlog(blogService.getBlogById(blog.getId()));
        comment.setUser(currentUser);
        commentService.saveComment(comment);
        return "redirect:/posts/" + blog.getId() + "/view";
    }

    @PreAuthorize("hasRole('ADMIN') || principal.id == #comment.user.id")
    @GetMapping("/{id}/delete")
    public String deleteCar(@PathVariable("id") Comment comment) {
        long commentId = comment.getBlog().getId();
        commentService.deleteComment(comment);
        return "redirect:/posts/" + commentId + "/view";
    }
    @PreAuthorize("hasRole('ADMIN') || principal.id == #comment.user.id")
    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable("id") Comment comment, RedirectAttributes attributes){
        attributes.addFlashAttribute("successMessage", "Comment edited successfully!");
        return "redirect:/posts/" + commentService.saveComment(comment).getBlog().getId() + "/view";
    }
}
