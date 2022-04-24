package ru.avtomaton.wikifacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.repository.FactRepository;

/**
 * @author Anton Akkuzin
 */
@Service
public class FactService {

    @Autowired
    private FactRepository factRepository;

    public void save(Fact fact) {
        factRepository.save(fact);
    }
}
