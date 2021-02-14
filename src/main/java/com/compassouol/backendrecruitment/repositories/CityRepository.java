package com.compassouol.backendrecruitment.repositories;

import com.compassouol.backendrecruitment.models.City;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    
}
