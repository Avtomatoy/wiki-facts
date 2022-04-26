package ru.avtomaton.wikifacts.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.avtomaton.wikifacts.entity.Category;
import ru.avtomaton.wikifacts.entity.Fact;

import javax.persistence.ElementCollection;
import java.util.Set;

/**
 * @author Anton Akkuzin
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchData {

    private SearchMethod method;
    @ElementCollection
    private Set<Category> categories;
    private String keywords;
    private long offset;
    private Fact fact;

    public enum SearchMethod {
        RANDOM,
        CATEGORIES,
        CATEGORIES_FAVOURITE,
        KEYWORDS,
        RATING,
    }
}
