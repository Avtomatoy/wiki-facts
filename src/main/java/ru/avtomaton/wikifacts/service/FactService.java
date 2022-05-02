package ru.avtomaton.wikifacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avtomaton.wikifacts.entity.Category;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.entity.User;
import ru.avtomaton.wikifacts.repository.FactRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Fact findByPreferredCategories(long offset, User user, Fact.Status status) {
        List<Fact> facts = factRepository.findAllByCategoriesIn(user.getPreferredCategories());
        if (status == Fact.Status.VERIFIED) {
            facts = facts.stream().filter(fact -> fact.getStatus() == Fact.Status.VERIFIED).collect(Collectors.toList());
        }
        return offset < facts.size() ? facts.get((int) offset) : null;
    }

    public Fact findByCategories(long offset, Set<Category> categories, Fact.Status status) {
        List<Fact> facts = factRepository.findAllByCategoriesIn(categories);
        if (status == Fact.Status.VERIFIED) {
            facts = facts.stream().filter(fact -> fact.getStatus() == Fact.Status.VERIFIED).collect(Collectors.toList());
        }
        return offset < facts.size() ? facts.get((int) offset) : null;
    }

    public Fact findByAuthorUsername(long offset, String username) {
        List<Fact> facts = factRepository.findAll().stream()
                .filter(fact -> fact.getAuthor().getUsername().equals(username)).collect(Collectors.toList());

        return offset < facts.size() ? facts.get((int) offset) : null;
    }

    public Fact findByRatingDesc(long offset, Fact.Status status) {
        return status == Fact.Status.VERIFIED
                ? factRepository.findOneByRatingDescWithOffsetVerified(offset)
                : factRepository.findOneByRatingDescWithOffset(offset);
    }

    public Fact findByKeywords(long offset, String keywords, Fact.Status status) {
        String join = String.join("|", keywords.split(" "));
        return status == Fact.Status.VERIFIED
                ? factRepository.findOneByKeywordsVerified(offset, join)
                : factRepository.findOneByKeywords(offset, join);
    }

    public Fact findByLikes(long offset, User user) {
        List<Fact> facts = factRepository.findAll().stream()
                .filter(fact -> containsUserInLikes(fact, user.getId())).collect(Collectors.toList());
        return offset < facts.size() ? facts.get((int) offset) : null;
    }

    public Fact findRandom(Fact.Status status) {
        return status == Fact.Status.VERIFIED
                ? factRepository.findRandomVerified()
                : factRepository.findRandom();
    }

    public boolean containsUserInLikes(Fact fact, Long userId) {
        for (User user : fact.getLikedUsers()) {
            if (Objects.equals(user.getId(), userId)) {
                return true;
            }
        }
        return false;
    }

    public void deleteById(Long id) {
        factRepository.deleteById(id);
    }

    public Optional<Fact> findById(Long id) {
        return factRepository.findById(id);
    }

    public List<Fact> findAllWithDeletedMark() {
        return factRepository.findAllWithDeletedMark();
    }

    public void deleteAll(List<Fact> facts) {
        factRepository.deleteAll(facts);
    }
}
