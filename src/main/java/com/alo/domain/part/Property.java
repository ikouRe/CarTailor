package com.alo.domain.part;

import java.util.Objects;

/**
 * Propriété (nom=valeur) associée à une instance de pièce.
 */
public final class Property {

    private final String name;
    private final String value;

    /**
     * Crée une propriété.
     *
     * @param name nom de la propriété (non nul)
     * @param value valeur de la propriété (non nulle)
     * @throws NullPointerException si {@code name} ou {@code value} est nul
     */
    public Property(String name, String value) {
        this.name = Objects.requireNonNull(name, "Property name cannot be null");
        this.value = Objects.requireNonNull(value, "Property value cannot be null");
    }

    /**
     * Nom de la propriété.
     */
    public String getName() {
        return name;
    }

    /**
     * Valeur de la propriété.
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
