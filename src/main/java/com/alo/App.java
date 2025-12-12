package com.alo;

import com.alo.catalog.CatalogueLoader;
import com.alo.config.Configuration;
import com.alo.config.Configurator;
import com.alo.domain.Category;
import com.alo.domain.part.PartInstance;
import com.alo.domain.part.PartType;
import com.alo.domain.part.Property;
import com.alo.service.ConfigurationService;
import com.alo.service.ConfigurationServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

        private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

        public static void main(String[] args) throws Exception {

                LOGGER.info("=== CAR CONFIGURATOR V2 START ===");

                // 1️ Charger le catalogue
                Configurator configurator = CatalogueLoader.loadFromResources("catalogue.json");

                // 2️ Créer la configuration + service
                Configuration configuration = configurator.getConfiguration();

                ConfigurationService service = new ConfigurationServiceImpl(configurator.getChecker());

                // 3️ Récupérer catégories et types
                Category engine = findCategory(configurator, "Engine");
                Category transmission = findCategory(configurator, "Transmission");

                PartType eg100 = findType(configurator, engine, "EG100");
                PartType ta5 = findType(configurator, transmission, "TA5");
                PartType tm5 = findType(configurator, transmission, "TM5");

                // 4️ Créer des instances
                PartInstance engineInstance = service.createInstance(eg100);
                PartInstance transmissionBad = service.createInstance(ta5);
                PartInstance transmissionGood = service.createInstance(tm5);

                // 5️ Ajouter des propriétés
                service.addProperty(engineInstance,
                                new Property("power", "150"));

                // 6️ Sélection invalide
                service.addPart(configuration, engineInstance);
                service.addPart(configuration, transmissionBad);

                LOGGER.info("Configuration valid ? {}",
                                service.isValid(configuration));

                // 7️ Correction
                configuration.unselectPart(transmission);
                service.addPart(configuration, transmissionGood);

                LOGGER.info("Configuration valid ? {}",
                                service.isValid(configuration));

                LOGGER.info("=== CAR CONFIGURATOR V2 END ===");
        }

        // ===== Helpers =====

        private static Category findCategory(Configurator configurator, String name) {
                return configurator.getCategories()
                                .stream()
                                .filter(c -> c.getName().equals(name))
                                .findFirst()
                                .orElseThrow();
        }

        private static PartType findType(Configurator configurator,
                        Category category,
                        String name) {
                return configurator.getVariants(category)
                                .stream()
                                .filter(p -> p.getName().equals(name))
                                .findFirst()
                                .orElseThrow();
        }
}
