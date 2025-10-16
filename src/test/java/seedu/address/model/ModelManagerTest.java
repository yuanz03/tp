package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void updatePersonInjuryStatus_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                modelManager.updatePersonInjuryStatus(null, new Injury(Person.DEFAULT_INJURY_STATUS)));
    }

    @Test
    public void updatePersonInjuryStatus_nullInjury_throwsNullPointerException() {
        modelManager.addPerson(ALICE);
        assertThrows(NullPointerException.class, () ->
                modelManager.updatePersonInjuryStatus(ALICE, null));
    }

    @Test
    public void updatePersonInjuryStatus_personNotInAddressBook_throwsPersonNotFoundException() {
        Injury injury = new Injury("Knee fracture");
        assertThrows(PersonNotFoundException.class, () -> modelManager.updatePersonInjuryStatus(ALICE, injury));
    }

    @Test
    public void updatePersonInjuryStatus_validPersonAndInjury_updatesInjuryStatusCorrectly() {
        modelManager.addPerson(ALICE);
        Injury injury = new Injury("Knee fracture");
        modelManager.updatePersonInjuryStatus(ALICE, injury);

        Person updatedPerson = modelManager.getPersonByName(ALICE.getName());
        assertEquals(injury, updatedPerson.getInjury());

        assertEquals(ALICE.getName(), updatedPerson.getName());
        assertEquals(ALICE.getPhone(), updatedPerson.getPhone());
        assertEquals(ALICE.getEmail(), updatedPerson.getEmail());
        assertEquals(ALICE.getAddress(), updatedPerson.getAddress());
        assertEquals(ALICE.getTeam(), updatedPerson.getTeam());
        assertEquals(ALICE.getPosition(), updatedPerson.getPosition());
        assertEquals(ALICE.getTags(), updatedPerson.getTags());
    }

    @Test
    public void updatePersonInjuryStatus_sameInjury_noInjuryStatusChange() {
        modelManager.addPerson(ALICE);
        Injury sameInjury = new Injury("ACL");

        modelManager.updatePersonInjuryStatus(ALICE, sameInjury);

        Person updatedPerson = modelManager.getPersonByName(ALICE.getName());
        assertEquals(sameInjury, updatedPerson.getInjury());
    }

    @Test
    public void updatePersonInjuryStatus_multipleInjuryUpdates_updatesInjuryStatusCorrectly() {
        modelManager.addPerson(ALICE);

        Injury firstInjury = new Injury("Knee fracture");
        modelManager.updatePersonInjuryStatus(ALICE, firstInjury);
        assertEquals(firstInjury, modelManager.getPersonByName(ALICE.getName()).getInjury());

        Injury secondInjury = new Injury("Sprained finger");
        modelManager.updatePersonInjuryStatus(modelManager.getPersonByName(ALICE.getName()), secondInjury);
        assertEquals(secondInjury, modelManager.getPersonByName(ALICE.getName()).getInjury());

        modelManager.updatePersonInjuryStatus(modelManager.getPersonByName(ALICE.getName()), ALICE.getInjury());
        assertEquals(ALICE.getInjury(), modelManager.getPersonByName(ALICE.getName()).getInjury());
    }

    @Test
    public void hasInjury_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                modelManager.hasInjury(null));
    }

    @Test
    public void hasInjury_personWithDefaultInjury_returnsFalse() {
        Person personWithDefaultInjury = new PersonBuilder().withInjury(Person.DEFAULT_INJURY_STATUS).build();
        modelManager.addPerson(personWithDefaultInjury);
        assertFalse(modelManager.hasInjury(personWithDefaultInjury));
    }

    @Test
    public void hasInjury_personWithActualInjury_returnsTrue() {
        Person personWithInjury = new PersonBuilder().withInjury("ACL").build();
        modelManager.addPerson(personWithInjury);
        assertTrue(modelManager.hasInjury(personWithInjury));
    }

    @Test
    public void hasInjury_personNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.hasInjury(ALICE));
    }

    @Test
    public void hasInjury_caseInsensitiveDefaultInjury_returnsFalse() {
        Person personWithLowercaseDefaultInjury = new PersonBuilder().withInjury("fit").build();
        modelManager.addPerson(personWithLowercaseDefaultInjury);
        assertFalse(modelManager.hasInjury(personWithLowercaseDefaultInjury));
    }

    @Test
    public void hasInjury_caseInsensitiveActualInjury_returnsTrue() {
        Person personWithLowercaseInjury = new PersonBuilder().withInjury("acl").build();
        modelManager.addPerson(personWithLowercaseInjury);
        assertTrue(modelManager.hasInjury(personWithLowercaseInjury));
    }

    @Test
    public void hasInjury_afterInjuryUpdate_reflectsUpdatedStatus() {
        Person person = new PersonBuilder().withInjury("ACL").build();
        modelManager.addPerson(person);
        assertTrue(modelManager.hasInjury(person));

        // Update to the default injury status
        modelManager.updatePersonInjuryStatus(person, new Injury(Person.DEFAULT_INJURY_STATUS));
        assertFalse(modelManager.hasInjury(modelManager.getPersonByName(person.getName())));
    }

    @Test
    public void isDuplicateInjuryAssigned_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                modelManager.isDuplicateInjuryAssigned(null, new Injury("ACL")));
    }

    @Test
    public void isDuplicateInjuryAssigned_nullInjury_throwsNullPointerException() {
        modelManager.addPerson(ALICE);
        assertThrows(NullPointerException.class, () ->
                modelManager.isDuplicateInjuryAssigned(ALICE, null));
    }

    @Test
    public void isDuplicateInjuryAssigned_personNotInAddressBook_throwsPersonNotFoundException() {
        Injury injury = new Injury("Knee fracture");
        assertThrows(PersonNotFoundException.class, () -> modelManager.isDuplicateInjuryAssigned(ALICE, injury));
    }

    @Test
    public void isDuplicateInjuryAssigned_sameInjury_returnsTrue() {
        modelManager.addPerson(ALICE);
        Injury sameInjury = new Injury("ACL");
        assertTrue(modelManager.isDuplicateInjuryAssigned(ALICE, sameInjury));
    }

    @Test
    public void isDuplicateInjuryAssigned_differentInjury_returnsFalse() {
        modelManager.addPerson(ALICE);
        Injury differentInjury = new Injury("Hamstring strain");
        assertFalse(modelManager.isDuplicateInjuryAssigned(ALICE, differentInjury));
    }

    @Test
    public void isDuplicateInjuryAssigned_caseInsensitiveSameInjury_returnsTrue() {
        modelManager.addPerson(ALICE);
        Injury sameInjuryDifferentCase = new Injury("acl");
        assertTrue(modelManager.isDuplicateInjuryAssigned(ALICE, sameInjuryDifferentCase));
    }

    @Test
    public void isDuplicateInjuryAssigned_multiplePersonsDifferentInjuries_reflectsUpdatedStatus() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);

        // ALICE initially has "ACL" injury status
        assertTrue(modelManager.isDuplicateInjuryAssigned(ALICE, new Injury("ACL")));
        assertFalse(modelManager.isDuplicateInjuryAssigned(ALICE, new Injury("Broken foot")));

        // BENSON initially has "Broken foot" injury status
        assertTrue(modelManager.isDuplicateInjuryAssigned(BENSON, new Injury("Broken foot")));
        assertFalse(modelManager.isDuplicateInjuryAssigned(BENSON, new Injury("ACL")));
    }

    @Test
    public void isDuplicateInjuryAssigned_afterInjuryUpdate_reflectsUpdatedStatus() {
        modelManager.addPerson(ALICE);

        // ALICE initially has "ACL" injury status
        assertTrue(modelManager.isDuplicateInjuryAssigned(ALICE, new Injury("ACL")));
        assertFalse(modelManager.isDuplicateInjuryAssigned(ALICE, new Injury("Concussion")));

        // Update ALICE injury status to Concussion
        modelManager.updatePersonInjuryStatus(ALICE, new Injury("Concussion"));

        // ALICE now has "Concussion" injury status
        assertTrue(modelManager.isDuplicateInjuryAssigned(modelManager.getPersonByName(ALICE.getName()),
                new Injury("Concussion")));
        assertFalse(modelManager.isDuplicateInjuryAssigned(modelManager.getPersonByName(ALICE.getName()),
                new Injury("ACL")));
    }

    @Test
    public void getFilteredTeamList_returnsUnfilteredTeamList() {
        ModelManager modelManager = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        // The default filteredTeams should contain all teams in the typical address book
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
    public void makeCaptain_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.makeCaptain(null));
    }

    @Test
    public void makeCaptain_personNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.makeCaptain(ALICE));
    }

    @Test
    public void makeCaptain_validPerson_makesCaptainCorrectly() {
        modelManager.addPerson(ALICE);
        assertFalse(ALICE.isCaptain());

        modelManager.makeCaptain(ALICE);

        Person updatedPerson = modelManager.getPersonByName(ALICE.getName());
        assertTrue(updatedPerson.isCaptain());

        // Verify other fields remain unchanged
        assertEquals(ALICE.getName(), updatedPerson.getName());
        assertEquals(ALICE.getPhone(), updatedPerson.getPhone());
        assertEquals(ALICE.getEmail(), updatedPerson.getEmail());
        assertEquals(ALICE.getAddress(), updatedPerson.getAddress());
        assertEquals(ALICE.getTeam(), updatedPerson.getTeam());
        assertEquals(ALICE.getPosition(), updatedPerson.getPosition());
        assertEquals(ALICE.getInjury(), updatedPerson.getInjury());
        assertEquals(ALICE.getTags(), updatedPerson.getTags());
    }

    @Test
    public void makeCaptain_alreadyCaptain_remainsCaptain() {
        Person captain = new PersonBuilder(ALICE).withCaptain(true).build();
        modelManager.addPerson(captain);
        assertTrue(captain.isCaptain());

        modelManager.makeCaptain(modelManager.getPersonByName(captain.getName()));

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
        assertEquals(captain.getInjury(), updatedPerson.getInjury());
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
