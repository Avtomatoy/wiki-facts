package ru.avtomaton.wikifacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.repository.FactRepository;

import java.util.List;

/**
 * @author Anton Akkuzin
 */
@Service
public class FactService {

    @Autowired
    private FactRepository factRepository;

    public Fact save(Fact fact) {
        return factRepository.save(fact);
    }

    public List<Fact> allFacts() {
        return factRepository.findAll();
    }

    public Fact findByRatingDesc(long offset) {
        return factRepository.findOneByRatingDescWithOffset(offset);
    }
}
