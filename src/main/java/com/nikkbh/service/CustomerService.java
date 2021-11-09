package com.nikkbh.service;

import com.nikkbh.entity.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> getCustomers();

    public Customer getCustomer(int customerId);

    public void saveCustomer(Customer theCustomer);

    public void deleteCustomer(int customerId);

    public List<Customer> searchCustomers(String theSearchName);
}
