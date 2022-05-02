package ru.avtomaton.wikifacts.model.attribute;

import lombok.Getter;
import lombok.Setter;
import ru.avtomaton.wikifacts.entity.Category;

/**
 * @author Anton Akkuzin
 */
@Getter
@Setter
public class CategoryPair {
    private Category category;
    private boolean checked;

    public CategoryPair(Category category, boolean checked) {
        this.category = category;
        this.checked = checked;
    }
}
