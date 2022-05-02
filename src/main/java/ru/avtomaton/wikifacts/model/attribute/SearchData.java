package ru.avtomaton.wikifacts.model.attribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.avtomaton.wikifacts.entity.Category;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Anton Akkuzin
 */
@Getter
@Setter
@NoArgsConstructor
@Component
@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class SearchData implements Serializable {

    private SearchMethod method;
    private Set<Category> categories;
    private String keywords;
    private long offset;
    private String username;

    public SearchData(String username) {
        this.username = username;
    }

    public enum SearchMethod {
        RANDOM,
        CATEGORIES,
        CATEGORIES_FAVOURITE,
        KEYWORDS,
        RATING,
        LIKE,
        OWN,
    }
}
