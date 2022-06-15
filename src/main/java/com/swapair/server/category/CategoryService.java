package com.swapair.server.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getCategory(Long categoryId ) {
        return categoryRepository.getById(categoryId);
    }

}
