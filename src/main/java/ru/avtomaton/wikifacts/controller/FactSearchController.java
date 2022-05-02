package ru.avtomaton.wikifacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.entity.User;
import ru.avtomaton.wikifacts.infra.DataBaseCleaner;
import ru.avtomaton.wikifacts.service.CategoryService;
import ru.avtomaton.wikifacts.service.FactService;
import ru.avtomaton.wikifacts.service.UserService;
import ru.avtomaton.wikifacts.model.attribute.SearchData;

import java.security.Principal;
import java.util.Optional;

/**
 * @author Anton Akkuzin
 */
@Controller
@RequestMapping("facts/search")
public class FactSearchController {

    @Autowired
    private FactService factService;
    @Autowired
    private UserService userService;
    @Autowired
    private DataBaseCleaner dataBaseCleaner;
    @Autowired
    private SearchData searchData;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/hub")
    public String getHub(Model model) {
        model.addAttribute("searchData", searchData);
        model.addAttribute("categories", categoryService.allCategories());

        return "facts_search_hub";
    }

    @GetMapping
    public String get(@ModelAttribute("searchData") SearchData searchData, Principal principal, Model model) {
        if (searchData.getMethod() == null) {
            searchData = this.searchData;
        }
        if (searchData.getUsername() == null) {
            searchData.setUsername(this.searchData.getUsername());
        }
        if (searchData.getCategories() == null) {
            searchData.setCategories(this.searchData.getCategories());
        }

        User user = null;
        Fact.Status status = Fact.Status.VERIFIED;
        if (principal != null) {
            user = userService.getUserByName(principal.getName());
            status = user.getPreferredStatus();
        }

        Fact fact = null;
        searchData.setOffset(searchData.getOffset() < 0 ? 0 : searchData.getOffset());
        switch (searchData.getMethod()) {
            case RANDOM:
                fact = factService.findRandom(status);
                break;
            case CATEGORIES:
                fact = factService.findByCategories(searchData.getOffset(), searchData.getCategories(), status);
                break;
            case CATEGORIES_FAVOURITE:
                fact = factService.findByPreferredCategories(searchData.getOffset(), user, status);
                break;
            case KEYWORDS:
                fact = factService.findByKeywords(searchData.getOffset(), searchData.getKeywords(), status);
                break;
            case RATING:
                fact = factService.findByRatingDesc(searchData.getOffset(), status);
                break;
            case OWN:
                fact = factService.findByAuthorUsername(searchData.getOffset(), searchData.getUsername());
                break;
            case LIKE:
                fact = factService.findByLikes(searchData.getOffset(), userService.getUserByName(searchData.getUsername()));
                break;
        }
        model.addAttribute("fact", fact);
        model.addAttribute("searchData", searchData);

        if (user != null && fact != null) {
            model.addAttribute("liked",
                    factService.containsUserInLikes(fact, user.getId()));
            model.addAttribute("canDelete", userService.isTrustedUser(user));
            model.addAttribute("canModerate", userService.isTrustedUser(user));
        }

        this.searchData = searchData;

        return "fact_view";
    }


    @PostMapping("/like")
    public String like(@ModelAttribute("fact") Fact fact, Principal principal, Model model) {

        if (principal != null && fact != null && fact.getId() != null) {
            User user = userService.getUserByName(principal.getName());
            fact = factService.findById(fact.getId()).orElseThrow();
            boolean liked = false;
            if (factService.containsUserInLikes(fact, user.getId())) {
                fact.setRating(fact.getRating() - 1);
                fact.getLikedUsers().remove(user);
            } else {
                fact.setRating(fact.getRating() + 1);
                fact.getLikedUsers().add(user);
                liked = true;
            }
            userService.save(user);
            factService.save(fact);
            model.addAttribute("liked", liked);
        }

        return "redirect:/facts/search";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("fact") Fact fact, Model model, Principal principal) {
        Optional<Fact> optionalFact = factService.findById(fact.getId());

        if (optionalFact.isPresent()) {
            optionalFact.get().setDeleted(true);
            factService.save(optionalFact.get());
        }

        model.addAttribute("deleted", "true");

        return "redirect:/facts/search";
    }
}
