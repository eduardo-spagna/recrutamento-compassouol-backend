package com.compassouol.backendrecruitment.repositories;

import com.compassouol.backendrecruitment.models.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
}
