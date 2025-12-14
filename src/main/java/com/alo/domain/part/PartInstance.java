package com.alo.domain.part;

import java.util.Objects;

/**
 * Instance concrète d'un {@link PartType} sélectionnée dans la configuration.
 */
public final class PartInstance {

    private final PartType type;

    /**
     * Crée une instance pour un type donné.
     *
     * @param type type de pièce (non nul)
     * @throws NullPointerException si {@code type} est nul
     */
    public PartInstance(PartType type) {
        this.type = Objects.requireNonNull(type, "PartType cannot be null");
    }

    /**
     * Type de pièce associé.
     */
    public PartType getType() {
        return type;
    }

    /**
     * Nom du type de pièce.
     */
    public String getName() {
        return type.getName();
    }

    @Override
    public String toString() {
        return "PartInstance{" + type.getName() + '}';
    }
}
