package com.example.controller;

import com.example.model.Blog;
import com.example.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @GetMapping
    public ModelAndView list(@RequestParam("search") Optional<String> search) {
        Iterable<Blog> blogs;
        if(search.isPresent()){
            blogs = blogService.findAllByAuthor(search.get());
        } else {
            blogs = blogService.findAll();
        }
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("message", "New blog created successfully");
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("update");
            modelAndView.addObject("blog", blog.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("error.404");
            return modelAndView;
        }
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        return "update";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView DeleteForm(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("delete");
            modelAndView.addObject("blog", blog.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("error.404");
            return modelAndView;
        }
    }

    //    @PostMapping("/delete")
//    public String deleteBlog(@ModelAttribute("blog") Blog blog) {
//        blogService.remove(blog.getId());
//        return "redirect:/blogs";
//    }
    @PostMapping("/delete")
    public String deleteBlog(Long id) {
        blogService.remove(id);
        return "redirect:/blogs";
    }
}
