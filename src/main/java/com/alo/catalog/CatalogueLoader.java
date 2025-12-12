package com.alo.catalog;

import com.alo.compatibility.CompatibilityManagerImpl;
import com.alo.config.Configurator;
import com.alo.config.ConfiguratorImpl;
import com.alo.domain.Category;
import com.alo.domain.CategoryImpl;
import com.alo.domain.PartType;
import com.alo.domain.PartTypeImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public final class CatalogueLoader {

    private CatalogueLoader() {
        // util class
    }

    public static Configurator load(Path jsonFile) throws IOException {

        // 1) Lire le JSON
        ObjectMapper mapper = new ObjectMapper();
        CatalogueDTO dto = mapper.readValue(jsonFile.toFile(), CatalogueDTO.class);

        // 2) Construire les catégories du domaine
        Map<String, Category> categoriesByName = new HashMap<>();
        Set<Category> categories = new HashSet<>();

        for (String catName : dto.categories) {
            Category c = new CategoryImpl(catName);
            categories.add(c);
            categoriesByName.put(catName, c);
        }

        // 3) Construire les PartType + map par catégorie
        Map<Category, Set<PartType>> variants = new HashMap<>();
        Map<String, PartType> partsByName = new HashMap<>();

        for (PartDTO p : dto.parts) {
            Category category = categoriesByName.get(p.category);
            if (category == null) {
                throw new IllegalStateException("Unknown category in JSON: " + p.category);
            }

            PartType partType = new PartTypeImpl(p.name, category);
            partsByName.put(p.name, partType);

            variants
                    .computeIfAbsent(category, k -> new HashSet<>())
                    .add(partType);
        }

        // on fige les collections (immutabilité)
        Map<Category, Set<PartType>> immutableVariants = new HashMap<>();
        for (Map.Entry<Category, Set<PartType>> e : variants.entrySet()) {
            immutableVariants.put(e.getKey(), Set.copyOf(e.getValue()));
        }

        // 4) Construire le CompatibilityManager avec les relations
        CompatibilityManagerImpl manager = new CompatibilityManagerImpl();

        for (PartDTO p : dto.parts) {

            PartType reference = partsByName.get(p.name);
            if (reference == null) {
                throw new IllegalStateException("Unknown part in map for: " + p.name);
            }

            // Requirements
            if (p.requires != null) {
                for (String reqName : p.requires) {
                    PartType target = partsByName.get(reqName);
                    if (target == null) {
                        throw new IllegalStateException(
                                "Unknown required part " + reqName + " for " + p.name);
                    }
                    manager.addRequirements(reference, Set.of(target));
                }
            }

            // Incompatibilities
            if (p.incompatible != null) {
                for (String incName : p.incompatible) {
                    PartType target = partsByName.get(incName);
                    if (target == null) {
                        throw new IllegalStateException(
                                "Unknown incompatible part " + incName + " for " + p.name);
                    }
                    manager.addIncompatibilities(reference, Set.of(target));
                }
            }
        }

        // 5) Créer le Configurator avec toutes les infos
        return new ConfiguratorImpl(
                Set.copyOf(categories),
                immutableVariants,
                manager);
    }
}
