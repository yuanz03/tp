package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INJURY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different team -> returns false
        editedAlice = new PersonBuilder(ALICE).withTeam(VALID_TEAM_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different position -> returns false
        editedAlice = new PersonBuilder(ALICE).withPosition(VALID_POSITION_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different injury -> returns false
        editedAlice = new PersonBuilder(ALICE).withInjuries(VALID_INJURY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different captaincy -> false
        editedAlice = new PersonBuilder(ALICE).withCaptain(true).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void withTeam_newTeam_returnsPersonWithNewTeam() {
        // Create a person with original team
        Person originalPerson = new PersonBuilder(ALICE).withTeam("U16").build();

        // Create new team
        seedu.address.model.team.Team newTeam = new seedu.address.model.team.Team("U21");

        // Create person with new team
        Person personWithNewTeam = originalPerson.withTeam(newTeam);

        // Verify team changed
        assertEquals(newTeam, personWithNewTeam.getTeam());
        assertEquals("U21", personWithNewTeam.getTeam().getName());

        // Verify all other fields remain the same
        assertEquals(originalPerson.getName(), personWithNewTeam.getName());
        assertEquals(originalPerson.getPhone(), personWithNewTeam.getPhone());
        assertEquals(originalPerson.getEmail(), personWithNewTeam.getEmail());
        assertEquals(originalPerson.getAddress(), personWithNewTeam.getAddress());
        assertEquals(originalPerson.getTags(), personWithNewTeam.getTags());
        assertEquals(originalPerson.getPosition(), personWithNewTeam.getPosition());
        assertEquals(originalPerson.getInjuries(), personWithNewTeam.getInjuries());
        assertEquals(originalPerson.isCaptain(), personWithNewTeam.isCaptain());

        // Verify original person is unchanged
        assertEquals("U16", originalPerson.getTeam().getName());
    }

    @Test
    public void withCaptain_newCaptainStatus_returnsPersonWithNewCaptainStatus() {
        // Create a person who is not a captain
        Person nonCaptain = new PersonBuilder(ALICE).withCaptain(false).build();

        // Create person as captain
        Person captain = nonCaptain.withCaptain(true);

        // Verify captain status changed
        assertFalse(nonCaptain.isCaptain());
        assertTrue(captain.isCaptain());

        // Verify all other fields remain the same
        assertEquals(nonCaptain.getName(), captain.getName());
        assertEquals(nonCaptain.getPhone(), captain.getPhone());
        assertEquals(nonCaptain.getEmail(), captain.getEmail());
        assertEquals(nonCaptain.getAddress(), captain.getAddress());
        assertEquals(nonCaptain.getTeam(), captain.getTeam());
        assertEquals(nonCaptain.getTags(), captain.getTags());
        assertEquals(nonCaptain.getPosition(), captain.getPosition());
        assertEquals(nonCaptain.getInjuries(), captain.getInjuries());

        // Test stripping captain
        Person strippedCaptain = captain.withCaptain(false);
        assertFalse(strippedCaptain.isCaptain());
        assertEquals(captain.getName(), strippedCaptain.getName());
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags()
                + ", team=" + ALICE.getTeam() + ", position=" + ALICE.getPosition()
                + ", injuries=" + ALICE.getInjuries() + ", isCaptain=" + ALICE.isCaptain() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
