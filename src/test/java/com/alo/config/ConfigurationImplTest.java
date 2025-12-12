package com.alo.config;

import com.alo.compatibility.CompatibilityManagerImpl;
import com.alo.domain.Category;
import com.alo.domain.CategoryImpl;
import com.alo.domain.part.PartInstance;
import com.alo.domain.part.PartType;
import com.alo.domain.part.PartTypeImpl;
import com.alo.service.ConfigurationService;
import com.alo.service.ConfigurationServiceImpl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationImplTest {

    @Test
    void selectPart_shouldStoreSelection() {
        Category engine = new CategoryImpl("Engine");
        PartType eg100 = new PartTypeImpl("EG100", engine);

        Configuration config = new ConfigurationImpl();
        PartInstance instance = new PartInstance(eg100);

        config.selectPart(instance);

        assertEquals(instance, config.getSelectionForCategory(engine));
        assertTrue(config.getSelectedParts().contains(instance));
    }

    @Test
    void selectingPartTwice_shouldReplacePreviousSelectionInSameCategory() {
        Category engine = new CategoryImpl("Engine");
        PartType eg100 = new PartTypeImpl("EG100", engine);
        PartType eg133 = new PartTypeImpl("EG133", engine);

        Configuration config = new ConfigurationImpl();

        PartInstance i1 = new PartInstance(eg100);
        PartInstance i2 = new PartInstance(eg133);

        config.selectPart(i1);
        config.selectPart(i2);

        assertEquals(i2, config.getSelectionForCategory(engine));
        assertFalse(config.getSelectedParts().contains(i1));
        assertTrue(config.getSelectedParts().contains(i2));
    }

    @Test
    void unselectPart_shouldRemoveSelection() {
        Category engine = new CategoryImpl("Engine");
        PartType eg100 = new PartTypeImpl("EG100", engine);

        Configuration config = new ConfigurationImpl();
        PartInstance instance = new PartInstance(eg100);

        config.selectPart(instance);
        config.unselectPart(engine);

        assertNull(config.getSelectionForCategory(engine));
        assertTrue(config.getSelectedParts().isEmpty());
    }

    @Test
    void clear_shouldRemoveAllSelections() {
        Category engine = new CategoryImpl("Engine");
        PartType eg100 = new PartTypeImpl("EG100", engine);

        Configuration config = new ConfigurationImpl();
        config.selectPart(new PartInstance(eg100));

        config.clear();

        assertTrue(config.getSelectedParts().isEmpty());
    }

    @Test
    void selectingSameCategoryTwiceKeepsOnlyOneInstance() {
        Category engine = new CategoryImpl("Engine");
        PartType eg100 = new PartTypeImpl("EG100", engine);
        PartType eg133 = new PartTypeImpl("EG133", engine);

        Configuration config = new ConfigurationImpl();
        ConfigurationService service = new ConfigurationServiceImpl(new CompatibilityManagerImpl());

        service.addPart(config, service.createInstance(eg100));
        service.addPart(config, service.createInstance(eg133));

        assertEquals(1, config.getSelectedParts().size());
    }

}
