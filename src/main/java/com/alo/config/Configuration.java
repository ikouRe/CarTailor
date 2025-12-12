package com.alo.config;

import java.util.Set;
import com.alo.domain.Category;
import com.alo.domain.part.PartInstance;

public interface Configuration {

    Set<PartInstance> getSelectedParts();

    PartInstance getSelectionForCategory(Category category);

    void selectPart(PartInstance instance);

    void unselectPart(Category category);

    void clear();
}
