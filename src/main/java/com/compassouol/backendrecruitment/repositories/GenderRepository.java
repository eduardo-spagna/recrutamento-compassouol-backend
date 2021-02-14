package com.compassouol.backendrecruitment.repositories;

import com.compassouol.backendrecruitment.models.Gender;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {
    
}
