package com.compassouol.backendrecruitment.services;

import java.util.ArrayList;
import java.util.List;

import com.compassouol.backendrecruitment.models.State;
import com.compassouol.backendrecruitment.repositories.StateRepository;

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
public class StateServiceTest {
    @TestConfiguration
    static class StateServiceTestConfiguration {
        @Bean
        public StateService stateService() {
            return new StateService();
        }
    }

    @Autowired
    private StateService stateService;

    @MockBean
    private StateRepository stateRepository;

    @BeforeEach
    public void setup() {
        List<State> allStates = new ArrayList<State>();

        State stateOfSP = new State(1L, "São Paulo", "sao_paulo", "SP");
        State stateOfPR = new State(2L, "Paraná", "parana", "PR");
        State stateOfMG = new State(3L, "Minas Gerais", "minas_gerais", "MG");
        State stateOfRS = new State(4L, "Rio Grande do Sul", "rio_grande_do_sul", "RS");

        allStates.add(stateOfSP);
        allStates.add(stateOfPR);
        allStates.add(stateOfMG);
        allStates.add(stateOfRS);

        Mockito.when(stateRepository.findById(stateOfSP.getStateId())).thenReturn(java.util.Optional.of(stateOfSP));
        Mockito.when(stateRepository.findAll()).thenReturn(allStates);
    }

    @Test
    @DisplayName("Should return a state named São Paulo")
    public void shouldReturnSaoPaulo() {
        State findStateById = stateService.findById(1L);
        Assertions.assertEquals("São Paulo", findStateById.getStateName());
    }

    @Test
    @DisplayName("Should return null when it does not find the state")
    public void shouldReturnNullWhenDoesntExist() {
        State findStateById = stateService.findById(3L);
        Assertions.assertNull(findStateById);
    }

    @Test
    @DisplayName("Should return four states")
    public void shouldReturnFourStates() {
        List<State> allStates = stateService.findAll();
        Assertions.assertEquals(4, allStates.size());
    }
}
