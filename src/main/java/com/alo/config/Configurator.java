package com.alo.config;

import java.util.Set;
import com.alo.domain.Category;
import com.alo.domain.part.PartType;
import com.alo.compatibility.CompatibilityChecker;

public interface Configurator {
    Set<Category> getCategories();

    Set<PartType> getVariants(Category category);

    Configuration getConfiguration();

    CompatibilityChecker getChecker();
}
