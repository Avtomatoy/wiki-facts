package ru.avtomaton.wikifacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.service.FactService;
import ru.avtomaton.wikifacts.util.SearchData;

import java.security.Principal;

/**
 * @author Anton Akkuzin
 */
@Controller
@RequestMapping("facts/search")
public class FactSearchController {

    @Autowired
    private FactService factService;

    @GetMapping
    public String getSearch(Model model) {
        model.addAttribute("searchData", new SearchData());

        return "facts_search_lobby";
    }

    @PostMapping
    public String postSearch(@ModelAttribute("searchData") SearchData searchData, Principal principal, Model model) {

        switch (searchData.getMethod()) {
            case RANDOM:
                break;
            case CATEGORIES:
                break;
            case CATEGORIES_FAVOURITE:
                break;
            case KEYWORDS:
                break;
            case RATING:
                searchData.setOffset(searchData.getOffset() < 0 ? 0 : searchData.getOffset());
                Fact fact = factService.findByRatingDesc(searchData.getOffset());
                if (fact == null) {
                    searchData.setOffset(searchData.getOffset() - 1);
                }
                fact = factService.findByRatingDesc(searchData.getOffset());
                searchData.setFact(fact);
                break;
        }

        model.addAttribute("searchData", searchData);

        return "fact_view";
    }

    @PostMapping("/like")
    public String postLike() {
        //todo

        return "fact_view";
    }
}
