package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.position.Position;
import seedu.address.model.team.Team;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Team> filteredTeams;
    private final FilteredList<Position> filteredPositions;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTeams = new FilteredList<>(this.addressBook.getTeamList());
        filteredPositions = new FilteredList<>(this.addressBook.getPositionList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook =============================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public Person getPersonByName(Name name) {
        requireNonNull(name);
        return addressBook.getPersonByName(name);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void updatePersonInjuryStatus(Person target, Injury injury) {
        requireAllNonNull(target, injury);

        Person updatedPerson = new Person(target.getName(), target.getPhone(), target.getEmail(), target.getAddress(),
                target.getTeam(), target.getTags(), target.getPosition(), injury, target.isCaptain());

        setPerson(target, updatedPerson);
    }

    @Override
    public boolean hasInjury(Person target) {
        requireNonNull(target);

        if (!hasPerson(target)) {
            throw new PersonNotFoundException();
        }
        return !target.getInjury().getInjuryName().equalsIgnoreCase(Person.DEFAULT_INJURY_STATUS);
    }

    @Override
    public boolean isDuplicateInjuryAssigned(Person target, Injury injury) {
        requireAllNonNull(target, injury);

        if (!hasPerson(target)) {
            throw new PersonNotFoundException();
        }
        return target.getInjury().equals(injury);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Team List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Team} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Team> getFilteredTeamList() {
        return filteredTeams;
    }

    @Override
    public void updateFilteredTeamList(Predicate<Team> predicate) {
        requireNonNull(predicate);
        filteredTeams.setPredicate(predicate);
    }

    //=========== Captain Commands =============================================================

    @Override
    public void makeCaptain(Person person) {
        person.makeCaptain();
    }

    @Override
    public void stripCaptain(Person person) {
        person.stripCaptain();
    }

    //=========== Team Commands =============================================================

    //@@author jovnc
    @Override
    public boolean hasTeam(Team team) {
        requireNonNull(team);
        return addressBook.hasTeam(team);
    }

    @Override
    public void addTeam(Team team) {
        requireNonNull(team);
        addressBook.addTeam(team);
        updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
    }

    @Override
    public boolean isTeamEmpty(Team team) {
        requireNonNull(team);
        return addressBook.isTeamEmpty(team);
    }

    @Override
    public void deleteTeam(Team team) {
        requireNonNull(team);
        addressBook.deleteTeam(team);
        updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
    }

    @Override
    public void assignTeam(Person person, Team team) {
        requireAllNonNull(person, team);
        addressBook.assignTeam(person, team);
    }

    //@@author
    //=========== Positions ==============================================================================
    @Override
    public boolean hasPosition(Position position) {
        requireNonNull(position);
        return addressBook.hasPosition(position);
    }

    @Override
    public void addPosition(Position position) {
        addressBook.addPosition(position);
        updateFilteredPositionList(p -> true);
    }

    @Override
    public void deletePosition(Position position) {
        addressBook.removePosition(position);
        updateFilteredPositionList(p -> true);
    }

    @Override
    public ObservableList<Position> getFilteredPositionList() {
        return filteredPositions;
    }

    @Override
    public void updateFilteredPositionList(Predicate<Position> predicate) {
        requireNonNull(predicate);
        filteredPositions.setPredicate(predicate);
    }

    @Override
    public Position getPositionByName(String name) {
        return addressBook.getPositionByName(name);
    }

    @Override
    public boolean isPositionAssigned(Position position) {
        requireNonNull(position);
        return addressBook.getPersonList().stream()
                .anyMatch(person -> person.getPosition().getName().equalsIgnoreCase(position.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredTeams.equals(otherModelManager.filteredTeams);
    }
}
