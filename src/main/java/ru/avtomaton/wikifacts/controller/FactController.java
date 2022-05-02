package ru.avtomaton.wikifacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.avtomaton.wikifacts.entity.Category;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.entity.User;
import ru.avtomaton.wikifacts.model.attribute.CategoryPair;
import ru.avtomaton.wikifacts.service.CategoryService;
import ru.avtomaton.wikifacts.service.FactService;
import ru.avtomaton.wikifacts.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * @author Anton Akkuzin
 */
@Controller
@RequestMapping("/facts")
public class FactController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private FactService factService;

    @GetMapping("/publish")
    public String getPublish(Model model) {
        model.addAttribute("factForm", new Fact());
        model.addAttribute("user", new User());
        model.addAttribute("categories", categoryService.allCategories());
        return "facts_publish";
    }

    @PostMapping("/publish")
    public String postPublish(@ModelAttribute("factForm") Fact fact, Principal principal) {
        User user = userService.getUserByName(principal.getName());

        fact.setAuthor(user);
        fact.setPublicationDate(new Date());
        fact.setRating(0L);
        fact.setStatus(userService.isTrustedUser(user) ? Fact.Status.VERIFIED : Fact.Status.UNVERIFIED);
        factService.save(fact);

        return "redirect:/facts";
    }

    @GetMapping("/edit")
    public String getEdit(@ModelAttribute("fact") Fact fact, Model model) {
        fact = factService.findById(fact.getId()).get();
        model.addAttribute("fact", fact);
        model.addAttribute("links", String.join("\n", fact.getLinks()));

        ArrayList<CategoryPair> categoryPairs = new ArrayList<>();
        for (Category category : categoryService.allCategories()) {
            categoryPairs.add(new CategoryPair(category, fact.getCategories().contains(category)));
        }
        model.addAttribute("categoryPairs", categoryPairs);

        return "fact_edit";
    }

    @PostMapping("/edit")
    public String postEdit(@ModelAttribute("fact") Fact fact) {

        Optional<Fact> optionalFact = factService.findById(fact.getId());

        if (optionalFact.isPresent()) {
            fact.setAuthor(optionalFact.get().getAuthor());
            fact.setRating(optionalFact.get().getRating());
            fact.setStatus(Fact.Status.VERIFIED);
            fact.setDeleted(optionalFact.get().isDeleted());
            fact.setPublicationDate(optionalFact.get().getPublicationDate());
            fact.setLikedUsers(optionalFact.get().getLikedUsers());
            factService.save(fact);
        }

        return "redirect:/facts/search";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("allFacts", factService.allFacts());

        return "facts_all";
    }
}
