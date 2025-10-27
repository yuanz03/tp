package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTeams.U12;
import static seedu.address.testutil.TypicalTeams.U16;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Injury;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.team.Team;
import seedu.address.model.team.exceptions.TeamNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getTeamCaptain_teamWithCaptain_returnsCaptain() {
        Person captain = new PersonBuilder(ALICE).withTeam(U16.getName()).withCaptain(true).build();

        modelManager.addTeam(U16);
        modelManager.addPerson(captain);

        assertEquals(captain, modelManager.getTeamCaptain(U16));
    }

    @Test
    public void getTeamCaptain_teamWithoutCaptain_returnsNull() {
        Person member = new PersonBuilder(ALICE).withTeam(U16.getName()).withCaptain(false).build();

        modelManager.addTeam(U16);
        modelManager.addPerson(member);

        assertNull(modelManager.getTeamCaptain(U16));
    }

    @Test
    public void getTeamCaptain_teamWithMultipleCaptains_returnsFirstCaptainFound() {
        Person captain1 = new PersonBuilder(ALICE).withTeam(U16.getName()).withCaptain(true).build();
        Person captain2 = new PersonBuilder(BENSON).withTeam(U16.getName()).withCaptain(true).build();

        modelManager.addTeam(U16);
        modelManager.addPerson(captain1);
        modelManager.addPerson(captain2);

        assertEquals(captain1, modelManager.getTeamCaptain(U16));
    }

    @Test
    public void getTeamCaptain_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.getTeamCaptain(null));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void hasTeam_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTeam(null));
    }

    @Test
    public void hasTeam_teamNotInAddressBook_returnsFalse() {
        modelManager.addTeam(U16);
        assertFalse(modelManager.hasTeam(U12));
    }

    @Test
    public void hasTeam_teamInAddressBook_returnsTrue() {
        modelManager.addTeam(U12);
        assertTrue(modelManager.hasTeam(U12));
    }

    @Test
    public void addInjury_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addInjury(null, Injury.DEFAULT_INJURY_STATUS));
    }

    @Test
    public void addInjury_nullInjury_throwsNullPointerException() {
        modelManager.addPerson(ALICE);
        assertThrows(NullPointerException.class, () -> modelManager.addInjury(ALICE, null));
    }

    @Test
    public void addInjury_personNotInAddressBook_throwsPersonNotFoundException() {
        Injury injury = new Injury("Knee fracture");
        assertThrows(PersonNotFoundException.class, () -> modelManager.addInjury(ALICE, injury));
    }

    @Test
    public void addInjury_validPersonAndInjury_addsInjuryStatusCorrectly() {
        modelManager.addPerson(ALICE);
        Injury injury = new Injury("Knee fracture");
        modelManager.addInjury(ALICE, injury);

        Person updatedPerson = modelManager.getPersonByName(ALICE.getName());
        assertTrue(updatedPerson.getInjuries().contains(injury));

        assertEquals(ALICE.getName(), updatedPerson.getName());
        assertEquals(ALICE.getPhone(), updatedPerson.getPhone());
        assertEquals(ALICE.getEmail(), updatedPerson.getEmail());
        assertEquals(ALICE.getAddress(), updatedPerson.getAddress());
        assertEquals(ALICE.getTeam(), updatedPerson.getTeam());
        assertEquals(ALICE.getPosition(), updatedPerson.getPosition());
        assertEquals(ALICE.getTags(), updatedPerson.getTags());
    }

    @Test
    public void addInjury_duplicateInjury_noDuplicateInjuryAdded() {
        modelManager.addPerson(ALICE);
        Injury sameInjury = new Injury("ACL");
        modelManager.addInjury(ALICE, sameInjury);

        Person firstPerson = modelManager.getPersonByName(ALICE.getName());
        int firstInjuryCount = firstPerson.getInjuries().size();

        // Add the same injury again
        modelManager.addInjury(ALICE, sameInjury);

        Person secondPerson = modelManager.getPersonByName(ALICE.getName());
        assertEquals(firstInjuryCount, secondPerson.getInjuries().size());
        assertTrue(secondPerson.getInjuries().contains(sameInjury));
    }

    @Test
    public void addInjury_multipleInjuryUpdates_addsAllInjuries() {
        modelManager.addPerson(ALICE);

        Injury firstInjury = new Injury("Knee fracture");
        modelManager.addInjury(ALICE, firstInjury);
        assertTrue(modelManager.getPersonByName(ALICE.getName()).getInjuries().contains(firstInjury));

        Injury secondInjury = new Injury("Sprained finger");
        modelManager.addInjury(modelManager.getPersonByName(ALICE.getName()), secondInjury);

        Person updatedPerson = modelManager.getPersonByName(ALICE.getName());
        assertTrue(updatedPerson.getInjuries().contains(firstInjury));
        assertTrue(updatedPerson.getInjuries().contains(secondInjury));
    }

    @Test
    public void addInjury_defaultInjury_throwsIllegalArgumentException() {
        modelManager.addPerson(ALICE);

        assertThrows(IllegalArgumentException.class, () -> modelManager.addInjury(ALICE, Injury.DEFAULT_INJURY_STATUS));
    }

    @Test
    public void addInjury_personWithDefaultInjury_addsInjuryStatusCorrectly() {
        Person personWithDefaultInjury = new PersonBuilder().build();
        modelManager.addPerson(personWithDefaultInjury);

        // Verify the initial injury status is the default "FIT" status
        assertTrue(personWithDefaultInjury.getInjuries().contains(Injury.DEFAULT_INJURY_STATUS));

        // Add a different injury
        Injury newInjury = new Injury("ACL");
        modelManager.addInjury(personWithDefaultInjury, newInjury);

        // Verify that the default "FIT" status is removed and the new injury is added
        Person updatedPerson = modelManager.getPersonByName(personWithDefaultInjury.getName());
        assertFalse(updatedPerson.getInjuries().contains(Injury.DEFAULT_INJURY_STATUS));
        assertTrue(updatedPerson.getInjuries().contains(newInjury));
    }

    @Test
    public void hasInjury_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasInjury(null));
    }

    @Test
    public void hasInjury_personWithOnlyDefaultInjury_returnsFalse() {
        Person personWithDefaultInjury = new PersonBuilder().build();
        modelManager.addPerson(personWithDefaultInjury);
        assertFalse(modelManager.hasInjury(modelManager.getPersonByName(personWithDefaultInjury.getName())));
    }

    @Test
    public void hasInjury_personWithSingleInjury_returnsTrue() {
        Person personWithInjury = new PersonBuilder().withInjuries("ACL").build();
        modelManager.addPerson(personWithInjury);
        assertTrue(modelManager.hasInjury(modelManager.getPersonByName(personWithInjury.getName())));
    }

    @Test
    public void hasInjury_personWithMultipleInjuries_returnsTrue() {
        Person personWithMultipleInjuries = new PersonBuilder().withInjuries("ACL", "MCL").build();
        modelManager.addPerson(personWithMultipleInjuries);
        assertTrue(modelManager.hasInjury(modelManager.getPersonByName(personWithMultipleInjuries.getName())));
    }

    @Test
    public void hasInjury_personNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.hasInjury(ALICE));
    }

    @Test
    public void hasInjury_caseInsensitiveDefaultInjury_returnsFalse() {
        Person personWithLowercaseDefaultInjury = new PersonBuilder().withInjuries("fit").build();
        modelManager.addPerson(personWithLowercaseDefaultInjury);
        assertFalse(modelManager.hasInjury(personWithLowercaseDefaultInjury));
    }

    @Test
    public void hasInjury_caseInsensitiveActualInjury_returnsTrue() {
        Person personWithLowercaseInjury = new PersonBuilder().withInjuries("acl").build();
        modelManager.addPerson(personWithLowercaseInjury);
        assertTrue(modelManager.hasInjury(personWithLowercaseInjury));
    }

    @Test
    public void deleteInjury_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteInjury(null, new Injury("ACL")));
    }

    @Test
    public void deleteInjury_nullInjury_throwsNullPointerException() {
        modelManager.addPerson(ALICE);
        assertThrows(NullPointerException.class, () -> modelManager.deleteInjury(ALICE, null));
    }

    @Test
    public void deleteInjury_personNotInAddressBook_throwsPersonNotFoundException() {
        Injury injury = new Injury("Knee fracture");
        assertThrows(PersonNotFoundException.class, () -> modelManager.deleteInjury(ALICE, injury));
    }

    @Test
    public void deleteInjury_validInjury_removesInjuryStatusCorrectly() {
        modelManager.addPerson(ALICE);
        Injury injury = new Injury("Knee fracture");
        modelManager.addInjury(ALICE, injury);

        // Verify injury was added
        Person personAfterAdd = modelManager.getPersonByName(ALICE.getName());
        assertTrue(personAfterAdd.getInjuries().contains(injury));

        // Remove the injury from the person
        modelManager.deleteInjury(modelManager.getPersonByName(ALICE.getName()), injury);

        Person personAfterDelete = modelManager.getPersonByName(ALICE.getName());
        assertFalse(personAfterDelete.getInjuries().contains(injury));
    }

    @Test
    public void deleteInjury_nonExistentInjury_noInjuryStatusChange() {
        modelManager.addPerson(ALICE);
        Injury existingInjury = new Injury("ACL");
        Injury nonExistentInjury = new Injury("Hamstring strain");

        modelManager.addInjury(ALICE, existingInjury);
        Person personBeforeDelete = modelManager.getPersonByName(ALICE.getName());
        int injuryCountBeforeDelete = personBeforeDelete.getInjuries().size();

        // Attempt to remove the non-existent injury
        modelManager.deleteInjury(ALICE, nonExistentInjury);

        Person personAfterDelete = modelManager.getPersonByName(ALICE.getName());
        assertEquals(injuryCountBeforeDelete, personAfterDelete.getInjuries().size());
        assertTrue(personAfterDelete.getInjuries().contains(existingInjury));
    }

    @Test
    public void deleteInjury_lastInjuryInList_addsDefaultInjury() {
        modelManager.addPerson(ALICE);
        Injury existingInjury = new Injury("ACL");

        // Remove all injuries in injury list
        modelManager.deleteInjury(ALICE, existingInjury);

        Person personAfterDelete = modelManager.getPersonByName(ALICE.getName());
        assertTrue(personAfterDelete.getInjuries().contains(Injury.DEFAULT_INJURY_STATUS));
        assertEquals(1, personAfterDelete.getInjuries().size());
    }

    @Test
    public void getFilteredTeamList_returnsUnfilteredTeamList() {
        ModelManager modelManager = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        // The default filteredTeams should contain all teams in the typical address
        // book
        ObservableList<Team> filteredList = modelManager.getFilteredTeamList();
        assertEquals(getTypicalAddressBook().getTeamList(), filteredList);
    }

    @Test
    public void equals_filteredTeamListDifferent_notEqual() {
        AddressBook ab = getTypicalAddressBook();
        UserPrefs prefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(ab, prefs);
        ModelManager modelManagerCopy = new ModelManager(ab, prefs);

        // Both start equal
        assertTrue(modelManager.equals(modelManagerCopy));

        // Apply a filter on modelManager only
        modelManager.updateFilteredTeamList(team -> team.getName().equals(U12.getName()));
        assertFalse(modelManager.equals(modelManagerCopy));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    @Test
    public void addTeam_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addTeam(null));
    }

    @Test
    public void deleteTeam_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteTeam(null));
    }

    @Test
    public void deleteTeam_teamNotInAddressBook_throwsTeamNotFoundException() {
        assertThrows(TeamNotFoundException.class, () -> modelManager.deleteTeam(U12));
    }

    @Test
    public void deleteTeam_validTeam_teamDeleted() {
        modelManager.addTeam(U12);
        modelManager.deleteTeam(U12);
        assertFalse(modelManager.hasTeam(U12));
    }

    @Test
    public void isTeamEmpty_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.isTeamEmpty(null));
    }

    @Test
    public void isTeamEmpty_teamNotInAddressBook_returnsTrue() {
        assertTrue(modelManager.isTeamEmpty(U12));
    }

    @Test
    public void isTeamEmpty_teamInAddressBookWithoutMembers_returnsTrue() {
        modelManager.addTeam(U12);
        assertTrue(modelManager.isTeamEmpty(U12));
    }

    @Test
    public void assignCaptain_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.assignCaptain(null));
    }

    @Test
    public void assignCaptain_personNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.assignCaptain(ALICE));
    }

    @Test
    public void assignCaptain_validPerson_assignsCaptainCorrectly() {
        modelManager.addPerson(ALICE);
        assertFalse(ALICE.isCaptain());

        modelManager.assignCaptain(ALICE);

        Person updatedPerson = modelManager.getPersonByName(ALICE.getName());
        assertTrue(updatedPerson.isCaptain());

        // Verify other fields remain unchanged
        assertEquals(ALICE.getName(), updatedPerson.getName());
        assertEquals(ALICE.getPhone(), updatedPerson.getPhone());
        assertEquals(ALICE.getEmail(), updatedPerson.getEmail());
        assertEquals(ALICE.getAddress(), updatedPerson.getAddress());
        assertEquals(ALICE.getTeam(), updatedPerson.getTeam());
        assertEquals(ALICE.getPosition(), updatedPerson.getPosition());
        assertEquals(ALICE.getInjuries(), updatedPerson.getInjuries());
        assertEquals(ALICE.getTags(), updatedPerson.getTags());
    }

    @Test
    public void assignCaptain_alreadyCaptain_remainsCaptain() {
        Person captain = new PersonBuilder(ALICE).withCaptain(true).build();
        modelManager.addPerson(captain);
        assertTrue(captain.isCaptain());

        modelManager.assignCaptain(modelManager.getPersonByName(captain.getName()));

        Person updatedPerson = modelManager.getPersonByName(captain.getName());
        assertTrue(updatedPerson.isCaptain());
    }

    @Test
    public void stripCaptain_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.stripCaptain(null));
    }

    @Test
    public void stripCaptain_personNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.stripCaptain(ALICE));
    }

    @Test
    public void stripCaptain_validCaptain_stripsCaptainCorrectly() {
        Person captain = new PersonBuilder(ALICE).withCaptain(true).build();
        modelManager.addPerson(captain);
        assertTrue(captain.isCaptain());

        modelManager.stripCaptain(modelManager.getPersonByName(captain.getName()));

        Person updatedPerson = modelManager.getPersonByName(captain.getName());
        assertFalse(updatedPerson.isCaptain());

        // Verify other fields remain unchanged
        assertEquals(captain.getName(), updatedPerson.getName());
        assertEquals(captain.getPhone(), updatedPerson.getPhone());
        assertEquals(captain.getEmail(), updatedPerson.getEmail());
        assertEquals(captain.getAddress(), updatedPerson.getAddress());
        assertEquals(captain.getTeam(), updatedPerson.getTeam());
        assertEquals(captain.getPosition(), updatedPerson.getPosition());
        assertEquals(captain.getInjuries(), updatedPerson.getInjuries());
        assertEquals(captain.getTags(), updatedPerson.getTags());
    }

    @Test
    public void stripCaptain_alreadyNotCaptain_remainsNotCaptain() {
        modelManager.addPerson(ALICE);
        assertFalse(ALICE.isCaptain());

        modelManager.stripCaptain(ALICE);

        Person updatedPerson = modelManager.getPersonByName(ALICE.getName());
        assertFalse(updatedPerson.isCaptain());
    }

}
