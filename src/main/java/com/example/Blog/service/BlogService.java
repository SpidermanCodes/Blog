package com.example.Blog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Blog.dto.BlogResponseDTO;
import com.example.Blog.model.Blog;
import com.example.Blog.model.User;
import com.example.Blog.repository.BlogRepository;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserService userService;

    public BlogResponseDTO createBlog(Blog blog) {
        User writer = userService.getUserById(blog.getWriter().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        blog.setWriter(writer);
        blog.setCreatedAt(LocalDateTime.now());  // Created at is set automatically
        blog.setUpdatedAt(LocalDateTime.now());  // Updated at is set automatically
        Blog savedBlog = blogRepository.save(blog);
        
        return convertToDTO(savedBlog);
    }

    public List<BlogResponseDTO> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BlogResponseDTO getBlogByTitle(String title) {
        Blog blog = blogRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Blog not found with title: " + title));
        
        return convertToDTO(blog);
    }

    public BlogResponseDTO getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with ID: " + id));
        
        return convertToDTO(blog);
    }

    public List<BlogResponseDTO> getAllBlogsByUserId(Long userId) {
        List<Blog> blogs = blogRepository.findByWriterId(userId);
        return blogs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BlogResponseDTO> getAllBlogsByUsername(String username) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Blog> blogs = blogRepository.findByWriterId(user.getId());
        return blogs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BlogResponseDTO updateBlogById(Long id, Blog updatedBlog) {
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        // Only update fields except for createdAt and updatedAt
        existingBlog.setContent(updatedBlog.getContent());
        existingBlog.setTitle(updatedBlog.getTitle());
        existingBlog.setWriter(updatedBlog.getWriter()); // Optional, if you allow changing the writer
        existingBlog.setUpdatedAt(LocalDateTime.now()); // Automatically update the time
        Blog savedBlog = blogRepository.save(existingBlog);
        
        return convertToDTO(savedBlog);
    }

    public BlogResponseDTO updateBlogByTitle(String title, Blog updatedBlog) {
        Blog existingBlog = blogRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        // Only update fields except for createdAt and updatedAt
        existingBlog.setContent(updatedBlog.getContent());
        existingBlog.setTitle(updatedBlog.getTitle());
        existingBlog.setWriter(updatedBlog.getWriter()); // Optional, if you allow changing the writer
        existingBlog.setUpdatedAt(LocalDateTime.now()); // Automatically update the time
        Blog savedBlog = blogRepository.save(existingBlog);
        
        return convertToDTO(savedBlog);
    }

    public void deleteBlogById(Long id) {
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        blogRepository.delete(existingBlog);
    }

    public void deleteBlogByTitle(String title) {
        Blog existingBlog = blogRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        blogRepository.delete(existingBlog);
    }

    private BlogResponseDTO convertToDTO(Blog blog) {
        return new BlogResponseDTO(
                blog.getId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getWriter().getUsername()
        );
    }
}
