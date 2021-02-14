package com.compassouol.backendrecruitment.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.compassouol.backendrecruitment.dtos.request.customer.CreateCustomerRequestDTO;
import com.compassouol.backendrecruitment.dtos.request.customer.UpdateCustomerRequestDTO;
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

    public Customer update(UpdateCustomerRequestDTO updateCustomer, Customer currentCustomer) {
        if (updateCustomer.getCustomerName() != null) {
            currentCustomer.setCustomerName(updateCustomer.getCustomerName());
            currentCustomer.setCustomerNameNormalized(StringUtil.normalizeString(updateCustomer.getCustomerName()));
        }

        currentCustomer.setCustomerUpdatedAt(LocalDateTime.now());

        return customerRepository.save(currentCustomer);
    }

    public List<Customer> findAllWithSearch(String search) {
        if (search == null) {
            search = "";
        } else {
            search = StringUtil.normalizeString(search);
        }

        return customerRepository.findAllWithSearch(search);
    }

    public Customer findById(long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent() == true) {
            return customer.get();
        }

        return null;
    }
}
