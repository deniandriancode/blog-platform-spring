package io.fam.bloggersite.service;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import com.github.slugify.*;

import io.fam.bloggersite.entity.Blog;
import io.fam.bloggersite.repository.BlogRepository;

import java.util.List;
import java.util.ArrayList;


@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    private Parser parser;
    private HtmlRenderer renderer;
    private Slugify slugify;

    public BlogService() {
        parser = Parser.builder().build();
        renderer = HtmlRenderer.builder().build();
        slugify = Slugify.builder().build();
    }

    public String render(String content) {
        Node document = parser.parse(content);
        return renderer.render(document).strip();
    }

    public String renderNewLine(String content) { // render html with newline appended
        Node document = parser.parse(content);
        return renderer.render(document);
    }

    public String slug(String content) {
        return slugify.slugify(content);
    }

    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    public Blog getOneBlog(Long id) {
        return blogRepository.findById(id).orElseGet(() -> new Blog());
    }

    public List<Blog> getAllBlogs() {
        List<Blog> result = new ArrayList<>();
        blogRepository.findAll().forEach(result::add);
        return result;
    }

    public void deleteOneBlog(Long id) {
        blogRepository.deleteById(id);
    }

    public void updateOneBlog(Long id, Blog blog) {
        blogRepository.save(blog);
    }

    public List<Blog> getFiveBlogs(int page) {
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        List<Blog> blog = blogRepository.findAll(pageable).getContent();
        return blog;
    }

}
