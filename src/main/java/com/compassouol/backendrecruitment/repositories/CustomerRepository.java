package com.compassouol.backendrecruitment.repositories;

import java.util.List;

import com.compassouol.backendrecruitment.models.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT customers.* " + "FROM customers "
            + "WHERE customers.customer_name_normalized ILIKE %:search% "
            + "ORDER BY customers.customer_name_normalized ASC", nativeQuery = true)
    List<Customer> findAllWithSearch(String search);
}
