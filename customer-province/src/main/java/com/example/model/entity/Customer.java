package com.example.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "`phone-number`")
    private String phone;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
    public Customer(){
    }
    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Customer(String name, String phone, Province province) {
        this.name = name;
        this.phone = phone;
        this.province = province;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
