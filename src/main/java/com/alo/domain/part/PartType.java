package com.alo.domain.part;

import com.alo.domain.Category;

/**
 * Type de pièce (variant) appartenant à une {@link Category}.
 */
public interface PartType {

    /**
     * Nom unique du type.
     */
    String getName();

    /**
     * Catégorie à laquelle ce type appartient.
     */
    Category getCategory();
}
