package com.alo.domain.part;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartInstanceTest {

    @Test
    void creatingPartInstanceWithNullTypeShouldThrowException() {

        // assertThrows = on teste un comportement anormal attendu
        assertThrows(NullPointerException.class, () -> {
            new PartInstance(null);
        });
    }

    @Test
    void exceptionMessageShouldBeExplicit() {
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> new PartInstance(null));

        assertEquals("PartType cannot be null", ex.getMessage());
    }

}
