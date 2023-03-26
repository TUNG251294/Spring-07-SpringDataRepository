package com.example.controller;

import com.example.model.entity.Customer;
import com.example.model.entity.Province;
import com.example.model.repository.ICustomerRepository;
import com.example.model.service.customer.ICustomerService;
import com.example.model.service.province.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Optional;

@Controller
@RequestMapping("/province")
public class ProvinceController {
    @Autowired
    private IProvinceService provinceService;
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/all")
    public ModelAndView listProvinces() {
        Iterable<Province> provinces = provinceService.findAll();
        ModelAndView modelAndView = new ModelAndView("/province/list");
        modelAndView.addObject("provinces", provinces);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createProvince(){
        ModelAndView modelAndView = new ModelAndView("/province/create");
        modelAndView.addObject("province",new Province());
        return modelAndView;
    }
    @PostMapping("/save")
    public ModelAndView saveProvince(@ModelAttribute("province") Province province){
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("/province/create");
        modelAndView.addObject("province",new Province());
        modelAndView.addObject("message", "New province created successfully");
        return modelAndView;
    }
    //có .get() hay khong cung giong nhau a?
    @GetMapping("/update/{id}")
    public ModelAndView updateProvince(@PathVariable Long id){
        //sửa findById() o IGeneralService sang Optional, chuyen kieu doi tuong sang kieu Optional thi phai dung pthuc get() de lay
        Optional<Province> province = provinceService.findById(id);
        if(province.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/province/update");
            modelAndView.addObject("province", province.get());
            return modelAndView;
        }else{
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit")
    public ModelAndView editProvince(@ModelAttribute("province") Province province){
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("/province/update");
        modelAndView.addObject("province",province);
        modelAndView.addObject("message","province was updated");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProvince(@PathVariable Long id){
        Optional<Province> province = provinceService.findById(id);
        if(province.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/province/delete");
            modelAndView.addObject("province", province.get());
            return modelAndView;
        }else{
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/remove")
    public String deleteProvince(@ModelAttribute("province") Province province){
        provinceService.remove(province.getId());
        return "redirect:all";
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id){
        Optional<Province> provinceOptional = provinceService.findById(id);
        if(!provinceOptional.isPresent()){
            return new ModelAndView("/error.404");
        }

        Iterable<Customer> customers = customerService.findAllByProvince(provinceOptional.get());

        ModelAndView modelAndView = new ModelAndView("/province/view");
        modelAndView.addObject("province", provinceOptional.get());
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
}
