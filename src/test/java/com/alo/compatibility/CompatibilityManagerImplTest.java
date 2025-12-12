package com.alo.compatibility;

import com.alo.domain.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CompatibilityManagerImplTest {

    @Test
    void addIncompatibility_shouldBeRetrievable() {
        Category engine = new CategoryImpl("Engine");
        PartType eg100 = new PartTypeImpl("EG100", engine);
        PartType eg133 = new PartTypeImpl("EG133", engine);

        CompatibilityManagerImpl manager = new CompatibilityManagerImpl();
        manager.addIncompatibilities(eg100, Set.of(eg133));

        assertTrue(manager.getIncompatibilities(eg100).contains(eg133));
    }

    @Test
    void addRequirement_shouldBeRetrievable() {
        Category engine = new CategoryImpl("Engine");
        PartType eh120 = new PartTypeImpl("EH120", engine);
        PartType tc120 = new PartTypeImpl("TC120", new CategoryImpl("Transmission"));

        CompatibilityManagerImpl manager = new CompatibilityManagerImpl();
        manager.addRequirements(eh120, Set.of(tc120));

        assertTrue(manager.getRequirements(eh120).contains(tc120));
    }

    @Test
    void removeIncompatibility_shouldRemoveRule() {
        Category engine = new CategoryImpl("Engine");
        PartType eg100 = new PartTypeImpl("EG100", engine);
        PartType eg133 = new PartTypeImpl("EG133", engine);

        CompatibilityManagerImpl manager = new CompatibilityManagerImpl();
        manager.addIncompatibilities(eg100, Set.of(eg133));
        manager.removeIncompatibility(eg100, eg133);

        assertTrue(manager.getIncompatibilities(eg100).isEmpty());
    }
}
