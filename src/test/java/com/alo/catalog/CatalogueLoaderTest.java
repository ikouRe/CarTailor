package com.alo.catalog;

import com.alo.config.Configurator;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CatalogueLoaderTest {

    @Test
    void load_shouldCreateConfigurator() throws Exception {

        Path path = Path.of("src/test/resources/catalogue.json");
        Configurator configurator = CatalogueLoader.load(path);

        assertNotNull(configurator);
        assertFalse(configurator.getCategories().isEmpty());
    }

}
