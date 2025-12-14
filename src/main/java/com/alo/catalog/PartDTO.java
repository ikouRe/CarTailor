package com.alo.catalog;

import java.util.List;

/**
 * Modèle de données décrivant un type de pièce dans le catalogue.
 */
public class PartDTO {

    /**
     * Nom unique du type de pièce (ex. "TM5").
     */
    public String name;
    /**
     * Nom de la catégorie (ex. "Transmission").
     */
    public String category;
    /**
     * Noms des types requis pour être compatibles.
     */
    public List<String> requires;
    /**
     * Noms des types incompatibles avec ce type.
     */
    public List<String> incompatible;

    /**
     * Constructeur par défaut requis pour la désérialisation Jackson.
     */
    public PartDTO() {
    }
}
