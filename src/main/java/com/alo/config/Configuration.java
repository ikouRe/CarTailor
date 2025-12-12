package com.alo.config;

import java.util.Set;
import com.alo.domain.Category;
import com.alo.domain.PartType;

public interface Configuration {
    boolean isComplete();

    boolean isValid();

    Set<PartType> getSelectedParts();

    PartType getSelectionForCategory(Category category);

    void selectPart(PartType part);

    void unselectPartType(Category category);

    void clear();
}
