package lt.codeacademy.blog_spring.controllers;

import lt.codeacademy.blog_spring.services.BlogService;
import lt.codeacademy.blog_spring.services.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    public final BlogService blogService;

    public final CommentService commentService;

    public UserController(BlogService blogService, CommentService commentService) {
        this.blogService = blogService;
        this.commentService = commentService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/view")
    public String getMyPosts(
            @PageableDefault(size = 9) Pageable pageable,
            @RequestParam(required = false) String title,
            Model model,
            HttpSession session) {
        model.addAttribute("comments", commentService.getAllComments());
        model.addAttribute("post", blogService.getPostsPaginatedByTitle(pageable, title));
        return "users/view";
    }
}
