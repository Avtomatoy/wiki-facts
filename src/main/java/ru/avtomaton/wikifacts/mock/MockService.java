package ru.avtomaton.wikifacts.mock;

import org.springframework.stereotype.Service;
import ru.avtomaton.wikifacts.entity.Category;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.entity.Role;
import ru.avtomaton.wikifacts.entity.User;
import ru.avtomaton.wikifacts.repository.CategoryRepository;
import ru.avtomaton.wikifacts.repository.FactRepository;
import ru.avtomaton.wikifacts.repository.RoleRepository;
import ru.avtomaton.wikifacts.service.UserService;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Anton Akkuzin
 */
@Service
public class MockService {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final FactRepository factRepository;
    private final CategoryRepository categoryRepository;

    public MockService(UserService userService, RoleRepository roleRepository, FactRepository factRepository, CategoryRepository categoryRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.factRepository = factRepository;
        this.categoryRepository = categoryRepository;

        createAdmin();
    }

    private Set<Role> createRoles() {
        Role role1 = new Role();
        role1.setName(Role.RoleName.ROLE_MEMBER.name());
        Role role2 = new Role();
        role2.setName(Role.RoleName.ROLE_MODERATOR.name());
        Role role3 = new Role();
        role3.setName(Role.RoleName.ROLE_ADMIN.name());

        Set<Role> roles = Set.of(role1, role2, role3);
        roleRepository.saveAll(roles);

        return roles;
    }

    private Set<Fact> createFacts(User user) {
        Category c1 = new Category();
        c1.setName(Category.CategoryName.ANIMALS_NATURE_PLACES.name());
        Category c2 = new Category();
        c2.setName(Category.CategoryName.ARCHITECTURE_ARTS.name());
        Category c3 = new Category();
        c3.setName(Category.CategoryName.ENTERTAINMENT.name());
        Category c4 = new Category();
        c4.setName(Category.CategoryName.HEALTH_FOOD_AND_DRINK.name());
        Category c5 = new Category();
        c5.setName(Category.CategoryName.HISTORY_EVENTS.name());
        Category c6 = new Category();
        c6.setName(Category.CategoryName.KNOWLEDGE_SCIENCE_TECHNOLOGY.name());
        Category c7 = new Category();
        c7.setName(Category.CategoryName.PEOPLE_PERSONS_CHARACTERS.name());
        Category c8 = new Category();
        c8.setName(Category.CategoryName.SOCIETY_CULTURE_HUMAN_BEHAVIOUR.name());
        Category c9 = new Category();
        c9.setName(Category.CategoryName.SPORTS.name());
        Category c10 = new Category();
        c10.setName(Category.CategoryName.TRANSPORT.name());

        Set<Category> categories = Set.of(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
        categoryRepository.saveAll(categories);

        Fact fact1 = new Fact();
        fact1.setAuthor(user);
        fact1.setRating(0L);
        fact1.setFactText("Children's writer Patricia MacLachlan kept a small bag of dirt from the prairies as a reminder of her Wyoming roots.");
        fact1.setStatus(Fact.Status.VERIFIED);
        fact1.setPublicationDate(new Date());
        fact1.setCategories(Set.of(c7, c3, c8));
        fact1.setLinks(Set.of("https://en.wikipedia.org/wiki/Patricia_MacLachlan", "https://en.wikipedia.org/wiki/High_Plains_(United_States)"));

        Fact fact2 = new Fact();
        fact2.setAuthor(user);
        fact2.setRating(1L);
        fact2.setFactText("The Comoro River, which cuts through the East Timorese capital of Dili, has seen large floods despite often running almost dry.");
        fact2.setStatus(Fact.Status.VERIFIED);
        fact2.setPublicationDate(new Date());
        fact2.setCategories(Set.of(c1));
        fact2.setLinks(Set.of("https://en.wikipedia.org/wiki/Comoro_River"));

        Fact fact3 = new Fact();
        fact3.setAuthor(user);
        fact3.setRating(2L);
        fact3.setFactText("The choreography of Crystal Pite's ballet The Seasons' Canon is inspired by the movement of tectonic plates.");
        fact3.setStatus(Fact.Status.UNVERIFIED);
        fact3.setPublicationDate(new Date());
        fact3.setCategories(Set.of(c1, c2, c3));
        fact3.setLinks(Set.of("https://en.wikipedia.org/wiki/The_Seasons%27_Canon", "https://en.wikipedia.org/wiki/Crystal_Pite", "https://en.wikipedia.org/wiki/Plate_tectonics"));

        Fact fact4 = new Fact();
        fact4.setAuthor(user);
        fact4.setRating(3L);
        fact4.setFactText("Claus Leusser, a justice of the German Federal Constitutional Court, served for only four months.");
        fact4.setStatus(Fact.Status.UNVERIFIED);
        fact4.setPublicationDate(new Date());
        fact4.setCategories(Set.of(c7, c8));
        fact4.setLinks(Set.of("https://en.wikipedia.org/wiki/List_of_justices_of_the_Federal_Constitutional_Court"));

        Set<Fact> facts = Set.of(fact1, fact2, fact3, fact4);
        factRepository.saveAll(facts);

        return facts;
    }

    private void createAdmin() {
        User admin = new User();
        admin.setUsername("Admin");
        admin.setPassword("775577");
        admin.setRoles(createRoles());
        userService.saveUser(admin);
        admin.setFacts(new HashSet<>());
        createFacts(admin).forEach(fact -> userService.updateAndSaveUserFacts(admin, fact));
    }
}
