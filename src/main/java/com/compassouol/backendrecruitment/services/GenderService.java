package com.compassouol.backendrecruitment.services;

import java.util.List;
import java.util.Optional;

import com.compassouol.backendrecruitment.models.Gender;
import com.compassouol.backendrecruitment.repositories.GenderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenderService {
    @Autowired
    private GenderRepository genderRepository;

    public Gender findById(long id) {
        Optional<Gender> gender = genderRepository.findById(id);

        if (gender.isPresent() == true) {
            return gender.get();
        }

        return null;
    }

    public List<Gender> findAll() {
        return genderRepository.findAll();
    }
}
