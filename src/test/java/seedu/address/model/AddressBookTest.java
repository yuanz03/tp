package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTeams.U12;
import static seedu.address.testutil.TypicalTeams.U16;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.team.Team;
import seedu.address.model.team.exceptions.DuplicateTeamException;
import seedu.address.model.team.exceptions.TeamNotEmptyException;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTeamList());
        assertEquals(Collections.emptyList(), addressBook.getPositionList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE)
            .withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND)
            .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookPersonStub newData = new AddressBookPersonStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateTeams_throwsDuplicateTeamException() {
        List<Team> newTeams = Arrays.asList(U12, U12);
        AddressBookTeamStub newData = new AddressBookTeamStub(newTeams);

        assertThrows(DuplicateTeamException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE)
            .withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND)
            .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void hasTeam_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasTeam(null));
    }

    @Test
    public void hasTeam_teamNotInAddressBook_returnsFalse() {
        addressBook.addTeam(U12);
        assertFalse(addressBook.hasTeam(U16));
    }

    @Test
    public void hasTeam_teamInAddressBook_returnsTrue() {
        addressBook.addTeam(U12);
        assertTrue(addressBook.hasTeam(U12));
    }

    @Test
    public void isTeamEmpty_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.isTeamEmpty(null));
    }

    @Test
    public void isTeamEmpty_teamWithNoPersons_returnsTrue() {
        addressBook.addTeam(U12);
        assertTrue(addressBook.isTeamEmpty(U12));
    }

    @Test
    public void isTeamEmpty_teamWithOnePerson_returnsFalse() {
        addressBook.addTeam(U12);
        addressBook.addPerson(ALICE); // ALICE is in U12
        assertFalse(addressBook.isTeamEmpty(U12));
    }

    @Test
    public void isTeamEmpty_teamWithMultiplePersons_returnsFalse() {
        addressBook.addTeam(U12);
        addressBook.addPerson(ALICE); // ALICE is in U12
        addressBook.addPerson(BENSON); // BENSON is in U12
        assertFalse(addressBook.isTeamEmpty(U12));
    }

    @Test
    public void isTeamEmpty_teamNotMatchingPersonTeam_returnsTrue() {
        addressBook.addTeam(U16);
        addressBook.addPerson(ALICE); // ALICE is in U12, not U16
        assertTrue(addressBook.isTeamEmpty(U16));
    }

    @Test
    public void deleteTeam_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.deleteTeam(null));
    }

    @Test
    public void deleteTeam_emptyTeam_success() {
        addressBook.addTeam(U12);
        assertTrue(addressBook.hasTeam(U12));
        addressBook.deleteTeam(U12);
        assertFalse(addressBook.hasTeam(U12));
    }

    @Test
    public void deleteTeam_teamWithPersons_throwsTeamNotEmptyException() {
        addressBook.addTeam(U12);
        addressBook.addPerson(ALICE); // ALICE is in U12
        assertThrows(TeamNotEmptyException.class, () -> addressBook.deleteTeam(U12));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void getTeamList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getTeamList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
            + "{persons=" + addressBook.getPersonList()
            + ", teams=" + addressBook.getTeamList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookPersonStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<seedu.address.model.position.Position> positions =
            FXCollections.observableArrayList();

        AddressBookPersonStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Team> getTeamList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<seedu.address.model.position.Position> getPositionList() {
            return positions;
        }
    }

    /**
     * A stub ReadOnlyAddressBook whose teams list can violate interface constraints.
     */
    private static class AddressBookTeamStub implements ReadOnlyAddressBook {
        private final ObservableList<Team> teams = FXCollections.observableArrayList();
        private final ObservableList<seedu.address.model.position.Position> positions =
            FXCollections.observableArrayList();

        AddressBookTeamStub(Collection<Team> teams) {
            this.teams.setAll(teams);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Team> getTeamList() {
            return teams;
        }

        @Override
        public ObservableList<seedu.address.model.position.Position> getPositionList() {
            return positions;
        }
    }

}
