package com.example.Blog.controller;

import com.example.Blog.dto.BlogResponseDTO;
import com.example.Blog.model.Blog;
import com.example.Blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    // Create blog
    @PostMapping
    public ResponseEntity<BlogResponseDTO> createBlog(@RequestBody Blog blog) {
        BlogResponseDTO response = blogService.createBlog(blog);
        return ResponseEntity.ok(response);
    }

    // Get all blogs
    @GetMapping
    public ResponseEntity<List<BlogResponseDTO>> getAllBlogs() {
        List<BlogResponseDTO> response = blogService.getAllBlogs();
        return ResponseEntity.ok(response);
    }

    @GetMapping("title/{title}")
    public ResponseEntity<BlogResponseDTO> getBlogByTitle(@PathVariable String title) {
        BlogResponseDTO response = blogService.getBlogByTitle(title);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BlogResponseDTO> getBlogById(@PathVariable Long id) {
        BlogResponseDTO response = blogService.getBlogById(id);
        return ResponseEntity.ok(response);
    }



    // Get all blogs by user ID
    @GetMapping("/user/id/{userId}")
    public ResponseEntity<List<BlogResponseDTO>> getAllBlogsByUserId(@PathVariable Long userId) {
        List<BlogResponseDTO> response = blogService.getAllBlogsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    // Get all blogs by user's username
    @GetMapping("/user/{username}")
    public ResponseEntity<List<BlogResponseDTO>> getAllBlogsByUsername(@PathVariable String username) {
        List<BlogResponseDTO> response = blogService.getAllBlogsByUsername(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<BlogResponseDTO> updateBlogById(
            @PathVariable Long id,
            @RequestBody Blog updatedBlog) {
        BlogResponseDTO response = blogService.updateBlogById(id, updatedBlog);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{title}")
    public ResponseEntity<BlogResponseDTO> updateBlogByTitle(
            @PathVariable String title,
            @RequestBody Blog updatedBlog) {
        BlogResponseDTO response = blogService.updateBlogByTitle(title, updatedBlog);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteBlogById(
            @PathVariable Long id) {
        blogService.deleteBlogById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Void> deleteBlogByTitle(
            @PathVariable String title) {
        blogService.deleteBlogByTitle(title);
        return ResponseEntity.noContent().build();
    }
}
