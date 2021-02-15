package com.compassouol.backendrecruitment.services;

import java.util.ArrayList;
import java.util.List;

import com.compassouol.backendrecruitment.models.Gender;
import com.compassouol.backendrecruitment.repositories.GenderRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GenderServiceTest {
    @TestConfiguration
    static class GenderServiceTestConfiguration {
        @Bean
        public GenderService genderService() {
            return new GenderService();
        }
    }

    @Autowired
    private GenderService genderService;

    @MockBean
    private GenderRepository genderRepository;

    @BeforeEach
    public void setup() {
        List<Gender> allGenders = new ArrayList<Gender>();

        Gender male = new Gender(1L, "Masculino");
        Gender female = new Gender(2L, "Feminino");
        Gender notInformed = new Gender(3L, "Prefiro não declarar");

        allGenders.add(male);
        allGenders.add(female);
        allGenders.add(notInformed);

        Mockito.when(genderRepository.findById(female.getGenderId())).thenReturn(java.util.Optional.of(female));
        Mockito.when(genderRepository.findAll()).thenReturn(allGenders);
    }

    @Test
    @DisplayName("Should return a gender named Feminino")
    public void shouldReturnFeminino() {
        Gender findGenderById = genderService.findById(2L);
        Assertions.assertEquals("Feminino", findGenderById.getGenderDescription());
    }

    @Test
    @DisplayName("Should return null when it does not find the gender")
    public void shouldReturnNullWhenDoesntExist() {
        Gender findGenderById = genderService.findById(4L);
        Assertions.assertNull(findGenderById);
    }

    @Test
    @DisplayName("Should return three genders")
    public void shouldReturnThreeGenders() {
        List<Gender> allGenders = genderService.findAll();
        Assertions.assertEquals(3, allGenders.size());
    }
}
