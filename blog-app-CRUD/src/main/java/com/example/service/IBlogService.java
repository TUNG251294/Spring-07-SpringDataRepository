package com.example.service;

import com.example.model.Blog;

public interface IBlogService extends IGeneralService<Blog>{
    Iterable<Blog> findAllByAuthor(String author);
}
