package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class InjuryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Injury(null));
    }

    @Test
    public void constructor_invalidInjury_throwsIllegalArgumentException() {
        String invalidInjuryName = "";
        assertThrows(IllegalArgumentException.class, () -> new Injury(invalidInjuryName));
    }

    @Test
    public void isValidInjuryName() {
        // null injury name
        assertThrows(NullPointerException.class, () -> Injury.isValidInjuryName(null));

        // invalid injury names
        assertFalse(Injury.isValidInjuryName("")); // empty string
        assertFalse(Injury.isValidInjuryName(" ")); // spaces only
        assertFalse(Injury.isValidInjuryName("^")); // only non-alphanumeric characters
        assertFalse(Injury.isValidInjuryName("musiala*")); // contains non-alphanumeric characters
        assertFalse(Injury.isValidInjuryName("@Kane")); // starts with non-alphanumeric characters

        // valid injury names
        assertTrue(Injury.isValidInjuryName("ACL")); // alphabets only
        assertTrue(Injury.isValidInjuryName("12345")); // numbers only
        assertTrue(Injury.isValidInjuryName("Grade 2 Hamstring Tear")); // alphanumeric characters
        assertTrue(Injury.isValidInjuryName("Broken Left Leg")); // with capital letters
        assertTrue(Injury.isValidInjuryName("Grade III Anterior Cruciate Ligament Rupture with Concomitant Medial "
                + "Meniscus Bucket Handle Tear and Lateral Femoral Condyle Bone Contusion")); // long names
    }

    @Test
    public void equals() {
        Injury injury = new Injury("ACL");

        // same values -> returns true
        assertTrue(injury.equals(new Injury("ACL")));
        assertTrue(injury.equals(new Injury("acl"))); // case-insensitive

        // same object -> returns true
        assertTrue(injury.equals(injury));

        // null -> returns false
        assertFalse(injury.equals(null));

        // different types -> returns false
        assertFalse(injury.equals(5.0f));

        // different values -> returns false
        assertFalse(injury.equals(new Injury("Other Valid Injury")));
    }

    @Test
    public void toString_returnsInjuryName() {
        String expectedInjuryName = "Concussion";
        Injury injury = new Injury(expectedInjuryName);
        assertEquals(expectedInjuryName, injury.toString());
    }
}
