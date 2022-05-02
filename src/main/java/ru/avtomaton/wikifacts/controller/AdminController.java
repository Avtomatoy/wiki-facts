package ru.avtomaton.wikifacts.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.entity.User;
import ru.avtomaton.wikifacts.service.FactService;
import ru.avtomaton.wikifacts.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Anton Akkuzin
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private FactService factService;

    @GetMapping("/admin")
    public String userList(Model model) {
        ArrayList<UserDto> userDtos = new ArrayList<>();

        for (User user : userService.allUsers()) {
            userDtos.add(new UserDto(user,
                    factService.allFacts().stream()
                            .filter(fact -> Objects.equals(fact.getAuthor().getId(), user.getId()))
                            .collect(Collectors.toList())));
        }


        model.addAttribute("userDtos", userDtos);

        return "admin";
    }

    @PostMapping("/admin")
    public String deleteUser(@RequestParam(defaultValue = "") Long userId,
                             @RequestParam(defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }

        return "redirect:/admin";
    }

    @Getter
    @Setter
    public static class UserDto {
        private User user;
        private List<Fact> facts;

        public UserDto(User user, List<Fact> facts) {
            this.user = user;
            this.facts = facts;
        }
    }
}
