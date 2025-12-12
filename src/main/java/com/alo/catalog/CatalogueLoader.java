package com.alo.catalog;

import com.alo.compatibility.CompatibilityManagerImpl;
import com.alo.config.Configurator;
import com.alo.config.ConfiguratorImpl;
import com.alo.domain.Category;
import com.alo.domain.CategoryImpl;
import com.alo.domain.part.PartType;
import com.alo.domain.part.PartTypeImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;

public final class CatalogueLoader {

    private CatalogueLoader() {
        // util class
    }

    public static Configurator load(Path jsonFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CatalogueDTO dto = mapper.readValue(jsonFile.toFile(), CatalogueDTO.class);
        return buildConfigurator(dto);
    }

    public static Configurator loadFromResources(String resourceName) throws IOException {

        InputStream is = CatalogueLoader.class
                .getClassLoader()
                .getResourceAsStream(resourceName);

        if (is == null) {
            throw new IllegalStateException("Resource not found: " + resourceName);
        }

        ObjectMapper mapper = new ObjectMapper();
        CatalogueDTO dto = mapper.readValue(is, CatalogueDTO.class);

        return buildConfigurator(dto);
    }

    private static Configurator buildConfigurator(CatalogueDTO dto) {

        Map<String, Category> categoriesByName = new HashMap<>();
        Set<Category> categories = new HashSet<>();

        for (String catName : dto.categories) {
            Category c = new CategoryImpl(catName);
            categories.add(c);
            categoriesByName.put(catName, c);
        }

        Map<Category, Set<PartType>> variants = new HashMap<>();
        Map<String, PartType> partsByName = new HashMap<>();

        for (PartDTO p : dto.parts) {
            Category category = categoriesByName.get(p.category);
            if (category == null) {
                throw new IllegalStateException("Unknown category: " + p.category);
            }

            PartType partType = new PartTypeImpl(p.name, category);
            partsByName.put(p.name, partType);

            variants
                    .computeIfAbsent(category, k -> new HashSet<>())
                    .add(partType);
        }

        Map<Category, Set<PartType>> immutableVariants = new HashMap<>();
        for (var e : variants.entrySet()) {
            immutableVariants.put(e.getKey(), Set.copyOf(e.getValue()));
        }

        CompatibilityManagerImpl manager = new CompatibilityManagerImpl();

        for (PartDTO p : dto.parts) {
            PartType reference = partsByName.get(p.name);

            if (p.requires != null) {
                for (String req : p.requires) {
                    manager.addRequirements(reference, Set.of(partsByName.get(req)));
                }
            }

            if (p.incompatible != null) {
                for (String inc : p.incompatible) {
                    manager.addIncompatibilities(reference, Set.of(partsByName.get(inc)));
                }
            }
        }

        return new ConfiguratorImpl(
                Set.copyOf(categories),
                immutableVariants,
                manager);
    }

}
