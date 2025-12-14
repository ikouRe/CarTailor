package com.alo.catalog;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.alo.config.Configurator;

class CatalogueLoaderTest {

    @Test
    void load_shouldCreateConfigurator() throws Exception {

        Path path = Path.of("src/test/resources/catalogue.json");
        Configurator configurator = CatalogueLoader.load(path);

        assertNotNull(configurator);
        assertFalse(configurator.getCategories().isEmpty());
    }

    @Test
    void load_shouldThrowIOException_whenFileNotFound() {
        Path path = Path.of("src/test/resources/nonexistent.json");

        assertThrows(IOException.class, () -> CatalogueLoader.load(path));
    }

    @Test
    void loadFromResources_shouldCreateConfigurator() throws IOException {
        Configurator configurator = CatalogueLoader.loadFromResources("catalogue.json");

        assertNotNull(configurator);
        assertFalse(configurator.getCategories().isEmpty());
    }

    @Test
    void loadFromResources_shouldThrowIllegalStateException_whenResourceNotFound() {
        assertThrows(IllegalStateException.class, ()
                -> CatalogueLoader.loadFromResources("nonexistent_catalogue.json"));
    }

    @Test
    void load_shouldPopulateVariants() throws Exception {
        Path path = Path.of("src/test/resources/catalogue.json");
        Configurator configurator = CatalogueLoader.load(path);

        // Vérifier qu'au moins une catégorie a des variants
        boolean hasVariants = configurator.getCategories()
                .stream()
                .anyMatch(cat -> !configurator.getVariants(cat).isEmpty());

        assertTrue(hasVariants, "Au moins une catégorie devrait avoir des variants");
    }

}
