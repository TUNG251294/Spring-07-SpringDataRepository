package com.example.model.service.customer;

import com.example.model.entity.Customer;
import com.example.model.entity.Province;
import com.example.model.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService extends IGeneralService<Customer> {
//    Page<Customer> findAll(Pageable pageable);
    Iterable<Customer> findAllByProvince(Province province);
}
