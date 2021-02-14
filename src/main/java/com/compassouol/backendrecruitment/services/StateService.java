package com.compassouol.backendrecruitment.services;

import java.util.List;
import java.util.Optional;

import com.compassouol.backendrecruitment.models.State;
import com.compassouol.backendrecruitment.repositories.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {
    @Autowired
    private StateRepository stateRepository;

    public State findById(long id) {
        Optional<State> state = stateRepository.findById(id);

        if (state.isPresent() == true) {
            return state.get();
        }

        return null;
    }

    public List<State> findAll() {
        return stateRepository.findAll();
    }
}
