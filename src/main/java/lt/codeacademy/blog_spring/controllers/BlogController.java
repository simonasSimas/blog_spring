package lt.codeacademy.blog_spring.controllers;

import lt.codeacademy.blog_spring.dtos.BlogDTO;
import lt.codeacademy.blog_spring.entities.Blog;
import lt.codeacademy.blog_spring.entities.Comment;
import lt.codeacademy.blog_spring.services.BlogService;
import lt.codeacademy.blog_spring.services.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/posts")
public class BlogController {

    private final BlogService blogService;

    private final CommentService commentService;

    public BlogController(BlogService blogService, CommentService commentService) {
        this.blogService = blogService;
        this.commentService = commentService;
    }

    @GetMapping
    public String getPosts(@PageableDefault(size = 6)Pageable pageable,
                           @RequestParam(required = false) String title,
                           Model model,
                           HttpSession session) {
        model.addAttribute("post", blogService.getPostsPaginatedByTitle(pageable, title));
        model.addAttribute("comment", new Comment());
        return "posts/index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String createPost(Model model) {
        model.addAttribute("blogDTO", new BlogDTO());
        return "posts/create";
    }

    @GetMapping("/{id}/view")
    public String getPost(@PathVariable("id") Blog blog, Model model) {
        model.addAttribute("post", blog);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", commentService.getAllComments());
        return "posts/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String editPost(@PathVariable("id") Blog blog, Model model) {
        model.addAttribute("blog", blog);
        return "posts/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/delete")
    public String deleteCar(@PathVariable("id") Blog blog) {
        long blogId = blog.getId();
        blogService.deleteBlog(blog);
        return "redirect:/posts/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createPost(@Valid BlogDTO blogDTO, BindingResult bindingResult, Model model, RedirectAttributes attributes) throws IOException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("blogDTO", blogDTO);
            return "posts/create";
        }
        Blog blog = blogService.saveBlog(blogDTO);
        attributes.addFlashAttribute("successMsg", "Your post has been successfully created!");
        return "redirect:/posts/" + blog.getId() + "/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable("id") Blog blog,@Valid BlogDTO blogDTO, BindingResult bindingResult, Model model, RedirectAttributes attributes) throws IOException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("blogDTO", blog);
            return "/posts" + blog.getId() + "/edit";
        }
        attributes.addFlashAttribute("successMsg", "Your post has been successfully edited!");
        return "redirect:/posts/" + blogService.saveBlog(blogDTO).getId() + "/view";
    }
}