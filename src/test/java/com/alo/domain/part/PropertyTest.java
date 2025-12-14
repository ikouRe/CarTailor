package com.alo.domain.part;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PropertyTest {

    @Test
    void constructor_shouldStoreNameAndValue() {
        Property prop = new Property("power", "150");

        assertEquals("power", prop.getName());
        assertEquals("150", prop.getValue());
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenNameIsNull() {
        assertThrows(NullPointerException.class, ()
                -> new Property(null, "150"));
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenValueIsNull() {
        assertThrows(NullPointerException.class, ()
                -> new Property("power", null));
    }

    @Test
    void exceptionMessage_shouldBeExplicitForNullName() {
        NullPointerException ex = assertThrows(NullPointerException.class, ()
                -> new Property(null, "150"));

        assertEquals("Property name cannot be null", ex.getMessage());
    }

    @Test
    void exceptionMessage_shouldBeExplicitForNullValue() {
        NullPointerException ex = assertThrows(NullPointerException.class, ()
                -> new Property("power", null));

        assertEquals("Property value cannot be null", ex.getMessage());
    }

    @Test
    void toString_shouldReturnNameEqualsValue() {
        Property prop = new Property("power", "150");

        assertEquals("power=150", prop.toString());
    }

    @Test
    void multipleProperties_shouldHaveDifferentValues() {
        Property power = new Property("power", "150");
        Property torque = new Property("torque", "250");

        assertNotEquals(power.getName(), torque.getName());
        assertNotEquals(power.getValue(), torque.getValue());
    }

}
