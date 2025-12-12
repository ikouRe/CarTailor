package com.alo.config;

import com.alo.compatibility.CompatibilityManagerImpl;
import com.alo.domain.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationImplTest {

    @Test
    void selectPart_shouldStoreSelection() {
        Category engine = new CategoryImpl("Engine");
        PartType eg100 = new PartTypeImpl("EG100", engine);

        Configuration config = new ConfigurationImpl(new CompatibilityManagerImpl());
        config.selectPart(eg100);

        assertEquals(eg100, config.getSelectionForCategory(engine));
    }

    @Test
    void selectingPartTwice_shouldReplacePreviousSelection() {
        Category engine = new CategoryImpl("Engine");
        PartType eg100 = new PartTypeImpl("EG100", engine);
        PartType eg133 = new PartTypeImpl("EG133", engine);

        Configuration config = new ConfigurationImpl(new CompatibilityManagerImpl());

        config.selectPart(eg100);
        config.selectPart(eg133);

        assertEquals(eg133, config.getSelectionForCategory(engine));
    }

    @Test
    void isValid_shouldReturnFalse_whenIncompatiblePartsSelected() {
        Category engine = new CategoryImpl("Engine");
        Category transmission = new CategoryImpl("Transmission");

        PartType eg100 = new PartTypeImpl("EG100", engine);
        PartType ta5 = new PartTypeImpl("TA5", transmission);

        CompatibilityManagerImpl manager = new CompatibilityManagerImpl();
        manager.addIncompatibilities(eg100, Set.of(ta5));

        Configuration config = new ConfigurationImpl(manager);

        config.selectPart(eg100);
        config.selectPart(ta5);

        assertFalse(config.isValid());
    }

    @Test
    void isValid_shouldReturnFalse_whenRequirementMissing() {
        Category engine = new CategoryImpl("Engine");
        Category transmission = new CategoryImpl("Transmission");

        PartType eh120 = new PartTypeImpl("EH120", engine);
        PartType tc120 = new PartTypeImpl("TC120", transmission);

        CompatibilityManagerImpl manager = new CompatibilityManagerImpl();
        manager.addRequirements(eh120, Set.of(tc120));

        Configuration config = new ConfigurationImpl(manager);
        config.selectPart(eh120);

        assertFalse(config.isValid());
    }

    @Test
    void isValid_shouldReturnTrue_whenRequirementSatisfied() {
        Category engine = new CategoryImpl("Engine");
        Category transmission = new CategoryImpl("Transmission");

        PartType eh120 = new PartTypeImpl("EH120", engine);
        PartType tc120 = new PartTypeImpl("TC120", transmission);

        CompatibilityManagerImpl manager = new CompatibilityManagerImpl();
        manager.addRequirements(eh120, Set.of(tc120));

        Configuration config = new ConfigurationImpl(manager);
        config.selectPart(eh120);
        config.selectPart(tc120);

        assertTrue(config.isValid());
    }

    @Test
    void clear_shouldRemoveAllSelections() {
        Category engine = new CategoryImpl("Engine");
        PartType eg133 = new PartTypeImpl("EG133", engine);

        Configuration config = new ConfigurationImpl(new CompatibilityManagerImpl());
        config.selectPart(eg133);
        config.clear();

        assertTrue(config.getSelectedParts().isEmpty());
    }
}
