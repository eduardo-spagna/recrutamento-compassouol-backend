package com.compassouol.backendrecruitment.services;

import com.compassouol.backendrecruitment.dtos.request.customer.CreateCustomerRequestDTO;
import com.compassouol.backendrecruitment.models.City;
import com.compassouol.backendrecruitment.models.Customer;
import com.compassouol.backendrecruitment.models.Gender;
import com.compassouol.backendrecruitment.repositories.CustomerRepository;
import com.compassouol.backendrecruitment.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer create(CreateCustomerRequestDTO createCustomer, Gender gender, City city) {
        Customer customer = new Customer();

        customer.setCustomerName(createCustomer.getCustomerName());
        customer.setCustomerNameNormalized(StringUtil.normalizeString(createCustomer.getCustomerName()));
        customer.setCustomerBirthdate(createCustomer.getCustomerBirthdate());
        customer.setGender(gender);
        customer.setCity(city);

        return customerRepository.save(customer);
    }
}
