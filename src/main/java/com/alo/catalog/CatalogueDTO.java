package com.alo.catalog;

import java.util.List;

/**
 * Modèle de données pour le catalogue JSON.
 *
 * <p>
 * Contient la liste des catégories et des pièces telles que
 * sérialisées/désérialisées par Jackson.</p>
 */
public class CatalogueDTO {

    /**
     * Noms des catégories présentes dans le catalogue.
     */
    public List<String> categories;
    /**
     * Définition des pièces avec contraintes et compatibilités.
     */
    public List<PartDTO> parts;

    /**
     * Constructeur par défaut requis pour la désérialisation Jackson.
     */
    public CatalogueDTO() {
    }
}
