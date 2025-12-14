package com.alo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.alo.catalog.CatalogueLoader;
import com.alo.config.Configuration;
import com.alo.config.Configurator;
import com.alo.domain.Category;
import com.alo.domain.part.PartInstance;
import com.alo.domain.part.PartType;
import com.alo.domain.part.Property;
import com.alo.service.ConfigurationService;
import com.alo.service.ConfigurationServiceImpl;

/**
 * Integration test for the CarTailor application.
 */
public class AppTest {

    /**
     * Full scenario: load catalogue, create configuration, add parts, test
     * compatibility, and fix issues.
     */
    @Test
    public void fullScenario_shouldDemonstrateConfigurationValidation() throws Exception {
        // 1️ Load catalogue
        Configurator configurator = CatalogueLoader.loadFromResources("catalogue.json");

        assertTrue(!configurator.getCategories().isEmpty(), "Catalogue should have categories");

        // 2️ Create service and configuration
        Configuration configuration = new com.alo.config.ConfigurationImpl();
        ConfigurationService service = new ConfigurationServiceImpl(configurator.getChecker());

        // 3️ Get categories and types
        Category engine = findCategory(configurator, "Engine");
        Category transmission = findCategory(configurator, "Transmission");

        PartType eg100 = findType(configurator, engine, "EG100");
        PartType ta5 = findType(configurator, transmission, "TA5");
        PartType tm5 = findType(configurator, transmission, "TM5");

        // 4️ Create instances
        PartInstance engineInstance = service.createInstance(eg100);
        PartInstance transmissionBad = service.createInstance(ta5);
        PartInstance transmissionGood = service.createInstance(tm5);

        // 5️ Add property to engine
        service.addProperty(engineInstance, new Property("power", "150"));

        // 6️ Invalid selection
        service.addPart(configuration, engineInstance);
        service.addPart(configuration, transmissionBad);

        assertFalse(service.isValid(configuration),
                "Configuration with EG100 + TA5 should be invalid");

        // 7️ Fix by replacing transmission
        configuration.unselectPart(transmission);
        service.addPart(configuration, transmissionGood);

        assertTrue(service.isValid(configuration),
                "Configuration with EG100 + TM5 should be valid");
    }

    /**
     * Empty configuration should be valid
     */
    @Test
    public void emptyConfiguration_shouldBeValid() throws Exception {
        Configurator configurator = CatalogueLoader.loadFromResources("catalogue.json");
        Configuration configuration = new com.alo.config.ConfigurationImpl();
        ConfigurationService service = new ConfigurationServiceImpl(configurator.getChecker());

        assertTrue(service.isValid(configuration), "Empty configuration should be valid");
    }

    /**
     * Single part selection should be valid
     */
    @Test
    public void singlePartSelection_shouldBeValid() throws Exception {
        Configurator configurator = CatalogueLoader.loadFromResources("catalogue.json");
        Configuration configuration = new com.alo.config.ConfigurationImpl();
        ConfigurationService service = new ConfigurationServiceImpl(configurator.getChecker());

        Category engine = findCategory(configurator, "Engine");
        PartType eg100 = findType(configurator, engine, "EG100");

        PartInstance instance = service.createInstance(eg100);
        service.addPart(configuration, instance);

        assertTrue(service.isValid(configuration), "Single part should be valid");
        assertFalse(configuration.getSelectedParts().isEmpty(), "Configuration should contain the part");
    }

    // ===== Helpers =====
    private static Category findCategory(Configurator configurator, String name) {
        return configurator.getCategories()
                .stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + name));
    }

    private static PartType findType(Configurator configurator, Category category, String name) {
        return configurator.getVariants(category)
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("PartType not found: " + name));
    }
}
