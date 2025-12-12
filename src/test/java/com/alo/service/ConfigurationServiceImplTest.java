package com.alo.service;

import com.alo.compatibility.CompatibilityManagerImpl;
import com.alo.config.Configuration;
import com.alo.config.ConfigurationImpl;
import com.alo.domain.Category;
import com.alo.domain.CategoryImpl;
import com.alo.domain.part.PartInstance;
import com.alo.domain.part.PartType;
import com.alo.domain.part.PartTypeImpl;
import com.alo.domain.part.Property;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationServiceImplTest {

    @Test
    void configurationShouldBeInvalidWhenPartsAreIncompatible() {
        Category engine = new CategoryImpl("Engine");
        Category transmission = new CategoryImpl("Transmission");

        PartType eg100 = new PartTypeImpl("EG100", engine);
        PartType ta5 = new PartTypeImpl("TA5", transmission);

        CompatibilityManagerImpl manager = new CompatibilityManagerImpl();
        manager.addIncompatibilities(eg100, Set.of(ta5));

        ConfigurationService service = new ConfigurationServiceImpl(manager);

        Configuration config = new ConfigurationImpl();

        PartInstance engineInstance = service.createInstance(eg100);
        PartInstance transmissionInstance = service.createInstance(ta5);

        service.addPart(config, engineInstance);
        service.addPart(config, transmissionInstance);

        assertFalse(service.isValid(config));
    }

    @Test
    void configurationShouldBeValidWhenIncompatibilityIsResolved() {
        Category engine = new CategoryImpl("Engine");
        Category transmission = new CategoryImpl("Transmission");

        PartType eg100 = new PartTypeImpl("EG100", engine);
        PartType ta5 = new PartTypeImpl("TA5", transmission);
        PartType tm5 = new PartTypeImpl("TM5", transmission);

        CompatibilityManagerImpl manager = new CompatibilityManagerImpl();
        manager.addIncompatibilities(eg100, Set.of(ta5));

        ConfigurationService service = new ConfigurationServiceImpl(manager);

        Configuration config = new ConfigurationImpl();

        PartInstance engineInstance = service.createInstance(eg100);
        PartInstance badTransmission = service.createInstance(ta5);
        PartInstance goodTransmission = service.createInstance(tm5);

        service.addPart(config, engineInstance);
        service.addPart(config, badTransmission);

        assertFalse(service.isValid(config));

        config.unselectPart(transmission);
        service.addPart(config, goodTransmission);

        assertTrue(service.isValid(config));
    }

    @Test
    void emptyConfigurationShouldBeValid() {
        ConfigurationService service = new ConfigurationServiceImpl(new CompatibilityManagerImpl());

        Configuration config = new ConfigurationImpl();

        assertTrue(service.isValid(config));
    }

    @Test
    void fullUserScenarioShouldWork() {
        Category engine = new CategoryImpl("Engine");
        Category transmission = new CategoryImpl("Transmission");

        PartType eg100 = new PartTypeImpl("EG100", engine);
        PartType ta5 = new PartTypeImpl("TA5", transmission);
        PartType tm5 = new PartTypeImpl("TM5", transmission);

        CompatibilityManagerImpl manager = new CompatibilityManagerImpl();
        manager.addIncompatibilities(eg100, Set.of(ta5));

        ConfigurationService service = new ConfigurationServiceImpl(manager);
        Configuration config = new ConfigurationImpl();

        PartInstance engineI = service.createInstance(eg100);
        PartInstance badT = service.createInstance(ta5);
        PartInstance goodT = service.createInstance(tm5);

        service.addPart(config, engineI);
        service.addPart(config, badT);

        assertFalse(service.isValid(config));

        config.unselectPart(transmission);
        service.addPart(config, goodT);

        assertTrue(service.isValid(config));
    }

    @Test
    void addingPropertiesAndResetShouldKeepConfigurationConsistent() {
        Category engine = new CategoryImpl("Engine");
        PartType eg100 = new PartTypeImpl("EG100", engine);

        ConfigurationService service = new ConfigurationServiceImpl(new CompatibilityManagerImpl());

        Configuration config = new ConfigurationImpl();

        // 1️config vide = valide
        assertTrue(service.isValid(config));

        // 2 créer instance + propriété
        PartInstance engineInstance = service.createInstance(eg100);
        service.addProperty(engineInstance, new Property("power", "150"));

        service.addPart(config, engineInstance);

        assertTrue(service.isValid(config));
        assertEquals(1, config.getSelectedParts().size());

        // 3️ reset
        config.clear();

        assertTrue(config.getSelectedParts().isEmpty());
        assertTrue(service.isValid(config));
    }

}
