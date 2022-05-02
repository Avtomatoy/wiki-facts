package ru.avtomaton.wikifacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.entity.Role;
import ru.avtomaton.wikifacts.entity.User;
import ru.avtomaton.wikifacts.entity.Category;
import ru.avtomaton.wikifacts.model.attribute.SearchData;
import ru.avtomaton.wikifacts.model.attribute.CategoryPair;
import ru.avtomaton.wikifacts.repository.RoleRepository;
import ru.avtomaton.wikifacts.service.UserService;
import ru.avtomaton.wikifacts.service.CategoryService;

import java.security.Principal;
import java.util.ArrayList;

/**
 * @author Anton Akkuzin
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/{username}")
    public String getProfile(@PathVariable("username") String username, Principal principal, Model model) {
        User user = userService.getUserByName(username);
        String role = null;
        switch (user.getMainRole()) {
            case ROLE_ADMIN: role = "Admin";break;
            case ROLE_MODERATOR: role = "Moderator";break;
            case ROLE_MEMBER: role = "Member";break;
        }
        model.addAttribute("user", user);
        model.addAttribute("role", role);
        model.addAttribute("searchData", new SearchData(user.getUsername()));
        model.addAttribute("canUpgrade", userService.isTrustedUser(userService.getUserByName(principal.getName())) && role.equals("Member"));

        ArrayList<CategoryPair> categoryPairs = new ArrayList<>();
        for (Category category : categoryService.allCategories()) {
            categoryPairs.add(new CategoryPair(category, user.getPreferredCategories().contains(category)));
        }
        model.addAttribute("categoryPairs", categoryPairs);

        return "profile";
    }

    @PostMapping("/{username}/upgrade")
    public String upgrade(@PathVariable("username") String username) {
        User user = userService.getUserByName(username);

        Role role = roleRepository.findAll().stream()
                .filter(r -> r.getName().equals(Role.RoleName.ROLE_MODERATOR.name()))
                .findFirst().get();

        user.getRoles().add(role);
        userService.save(user);

        return "redirect:/profile/" + username;
    }

    @PostMapping("/{username}/prefst")
    public String changePreferredStatus(@PathVariable("username") String username) {
        User user = userService.getUserByName(username);

        user.setPreferredStatus(user.getPreferredStatus() == Fact.Status.VERIFIED ? Fact.Status.UNVERIFIED : Fact.Status.VERIFIED);
        userService.save(user);

        return "redirect:/profile/" + username;
    }

    @PostMapping("/{username}/prefcat")
    public String changePreferredCategories(@PathVariable("username") String username,
                                            @ModelAttribute("searchData") SearchData searchData) {
        User user = userService.getUserByName(username);
        user.setPreferredCategories(searchData.getCategories());
        userService.save(user);

        return "redirect:/profile/" + username;
    }
}
