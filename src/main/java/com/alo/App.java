package com.alo;

import com.alo.catalog.CatalogueLoader;
import com.alo.config.Configuration;
import com.alo.config.Configurator;
import com.alo.domain.Category;
import com.alo.domain.PartType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class App {

        private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

        public static void main(String[] args) throws Exception {

                LOGGER.info("=== CAR CONFIGURATOR V1 START ===");

                // 1️⃣ Charger le catalogue
                Configurator configurator = CatalogueLoader.load(Path.of("src/resources/catalogue.json"));

                LOGGER.info("Catalogue loaded successfully");

                // 2️⃣ Créer une configuration utilisateur
                Configuration config = configurator.getConfiguration();

                // 3️⃣ Récupérer les catégories
                Category engine = findCategory(configurator, "Engine");
                Category transmission = findCategory(configurator, "Transmission");

                // 4️⃣ Récupérer des pièces
                PartType eg100 = findPart(configurator, engine, "EG100");
                PartType ta5 = findPart(configurator, transmission, "TA5");
                PartType tm5 = findPart(configurator, transmission, "TM5");

                // 5️⃣ Sélection invalide
                LOGGER.info("Selecting parts: {} + {} (expected INVALID)",
                                eg100.getName(), ta5.getName());

                config.selectPart(eg100);
                config.selectPart(ta5);

                LOGGER.info("Configuration valid ? {}", config.isValid());

                // 6️⃣ Correction
                LOGGER.info("Fixing configuration: replacing TA5 with TM5");

                config.unselectPartType(transmission);
                config.selectPart(tm5);

                LOGGER.info("Configuration valid ? {}", config.isValid());

                // 7️⃣ Reset
                LOGGER.info("Resetting configuration");
                config.clear();

                LOGGER.info("Selected parts after reset: {}",
                                config.getSelectedParts().size());

                LOGGER.info("=== CAR CONFIGURATOR V1 END ===");
        }

        // ===== Helper methods (private, lisibilité du scénario) =====

        private static Category findCategory(Configurator configurator, String name) {
                return configurator.getCategories()
                                .stream()
                                .filter(c -> c.getName().equals(name))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException("Category not found: " + name));
        }

        private static PartType findPart(Configurator configurator,
                        Category category,
                        String name) {
                return configurator.getVariants(category)
                                .stream()
                                .filter(p -> p.getName().equals(name))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException("Part not found: " + name));
        }
}
