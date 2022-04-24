package ru.avtomaton.wikifacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.entity.User;
import ru.avtomaton.wikifacts.service.CategoryService;
import ru.avtomaton.wikifacts.service.FactService;
import ru.avtomaton.wikifacts.service.UserService;

import java.security.Principal;
import java.util.Date;

/**
 * @author Anton Akkuzin
 */
@Controller
public class FactController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private FactService factService;

    @GetMapping("facts/publish")
    public String getPublish(Model model) {
        model.addAttribute("factForm", new Fact());
        model.addAttribute("user", new User());
        model.addAttribute("categories", categoryService.allCategories());
        return "facts_publish";
    }

    @PostMapping("facts/publish")
    public String postPublish(@ModelAttribute("factForm") Fact fact, Principal principal) {
        User user = userService.getUserByName(principal.getName());

        fact.setAuthor(user);
        fact.setPublicationDate(new Date());
        fact.setRating(0L);
        fact.setStatus(userService.isTrustedUser(user) ? Fact.Status.VERIFIED : Fact.Status.UNVERIFIED);

        factService.save(fact);
        user.getFacts().add(fact);
        userService.saveUser(user);

        return "facts";
    }
}
