package com.example.controller;

import com.example.model.entity.Customer;
import com.example.model.entity.Province;
import com.example.model.service.customer.ICustomerService;
import com.example.model.service.province.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }

    @GetMapping("/all")
    public ModelAndView listCustomers() {
        Iterable<Customer> customers = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createCustomer(){
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer",new Customer());
        return modelAndView;
    }
    @PostMapping("/save")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer",new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }
    //có .get() hay khong cung giong nhau a?
    @GetMapping("/update/{id}")
    public ModelAndView updateCustomer(@PathVariable Long id){
        //sửa findById() o IGeneralService sang Optional, chuyen kieu doi tuong sang kieu Optional thi phai dung pthuc get() de lay
        Optional<Customer> customer = customerService.findById(id);
        if(customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/update");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;
        }else{
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit")
    public ModelAndView editCustomer(@ModelAttribute("customer") Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/update");
        modelAndView.addObject("customer",customer);
        modelAndView.addObject("message","customer was updated");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCustomer(@PathVariable Long id){
        Optional<Customer> customer = customerService.findById(id);
        if(customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/delete");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;
        }else{
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/remove")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer){
        customerService.remove(customer.getId());
        return "redirect:all";
    }
}
