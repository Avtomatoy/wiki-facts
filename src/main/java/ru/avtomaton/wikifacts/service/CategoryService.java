package ru.avtomaton.wikifacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avtomaton.wikifacts.entity.Category;
import ru.avtomaton.wikifacts.repository.CategoryRepository;

import java.util.List;

/**
 * @author Anton Akkuzin
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }
}
