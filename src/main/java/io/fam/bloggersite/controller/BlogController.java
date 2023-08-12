package io.fam.bloggersite.controller;

import java.util.*;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.fam.bloggersite.service.BlogService;
import jakarta.validation.Valid;
import io.fam.bloggersite.entity.Blog;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/blogs")
    public String getListBlogs(Model model) {
        List<Blog> blogs = blogService.getFiveBlogs(0);
        model.addAttribute("blogs", blogs);
        model.addAttribute("pageNumber", 1);
        return "all";
    }

    @GetMapping("/blogs/page/{page}")
    public String getBlogForPage(@PathVariable("page") int page, Model model) {
        if (page < 1) {
            return "redirect:/blogs";
        }
        List<Blog> blogs = blogService.getFiveBlogs(page - 1);
        model.addAttribute("blogs", blogs);
        model.addAttribute("pageNumber", page);
        return "all";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }

    @GetMapping("/new")
    public String getNewForm(Blog blog) {
        return "new";
    }

    @GetMapping("/blog/{id}")
    public String getBlogDetail(@PathVariable("id") Long id, Model model) {
        Blog blog = blogService.getOneBlog(id);
        String blogContent = blogService.render(blog.getContent());
        model.addAttribute("blog", blog);
        model.addAttribute("blogContent", blogContent);
        return "blog";
    }

    @GetMapping("/update/{id}")
    public String getEditBlogForm(@PathVariable("id") Long id, Model model, Blog blog) {
        Blog retrievedBlog = blogService.getOneBlog(id);
        model.addAttribute("blog", retrievedBlog);
        return "update";
    }

    @PostMapping("/new")
    public String postNewBlog(@Valid Blog blog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        blogService.save(blog);
        return "redirect:/blogs";
    }

    @PostMapping("/update/{id}")
    public String updateBlogPost(@PathVariable("id") Long id, @Valid Blog blog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update";
        }
        blog.setId(id);
        blogService.save(blog);
        return "redirect:/blog/" + blog.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteBlogPost(@PathVariable("id") Long id) {
        blogService.deleteOneBlog(id);
        return "redirect:/blogs";
    }

}
