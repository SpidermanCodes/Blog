package com.example.Blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Blog.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    
    Optional<Blog> findByTitle(String title);
    List<Blog> findByWriterId(Long writerId); 
}
