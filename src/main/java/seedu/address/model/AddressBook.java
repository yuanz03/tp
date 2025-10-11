package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.position.Position;
import seedu.address.model.position.UniquePositionList;
import seedu.address.model.team.Team;
import seedu.address.model.team.UniqueTeamList;
import seedu.address.model.team.exceptions.TeamNotEmptyException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTeamList teams;
    private final UniquePositionList positions;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     * among constructors.
     */
    {
        persons = new UniquePersonList();
        teams = new UniqueTeamList();
        positions = new UniquePositionList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTeams(newData.getTeamList());
        setPositions(newData.getPositionList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    public Person getPersonByName(Name name) {
        requireNonNull(name);
        return persons.getPersonByName(name);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// team level operations

    /**
     * Returns true if a team with the same identity as {@code team} exists in the address book.
     */
    public boolean hasTeam(Team team) {
        requireNonNull(team);
        return teams.contains(team);
    }

    /**
     * Adds a team to the address book.
     * The team must not already exist in the address book.
     */
    public void addTeam(Team t) {
        teams.add(t);
    }

    /**
     * Replaces the contents of the teams list with {@code teams}.
     * {@code teams} must not contain duplicate teams.
     */
    public void setTeams(List<Team> teams) {
        this.teams.setTeams(teams);
    }

    /**
     * Assigns a person to a team.
     * The person must exist in the address book.
     * The team must exist in the address book.
     */
    public void assignTeam(Person person, Team team) {
        this.persons.assignTeam(person, team);
    }

    /**
     * Returns true if team has no players.
     */
    public boolean isTeamEmpty(Team team) {
        requireNonNull(team);
        for (Person person : persons) {
            if (team.equals(person.getTeam())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deletes team from the address book.
     * {@code team} must exist in the address book.
     */
    public void deleteTeam(Team team) {
        requireNonNull(team);
        if (!isTeamEmpty(team)) {
            throw new TeamNotEmptyException();
        }
        teams.remove(team);
    }

    /**
     * Replaces the contents of the positions list with {@code positions}.
     * {@code positions} must not contain duplicate positions.
     */
    public void setPositions(List<Position> positions) {
        this.positions.setPositions(positions);
    }

    /**
     * Returns true if a position with the same identity as {@code position} exists in the address book.
     * <p>
     * Identity is determined by {@link seedu.address.model.position.Position#isSamePosition(Position)}
     * which compares position names case-insensitively.
     *
     * @param position the position to check; must not be {@code null}.
     * @return {@code true} if an equivalent position already exists; {@code false} otherwise.
     */
    public boolean hasPosition(Position position) {
        requireNonNull(position);
        return positions.contains(position);
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public void removePosition(Position position) {
        positions.remove(position);
    }

    public Position getPositionByName(String name) {
        return positions.getByName(name);
    }

    /// / util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("teams", teams)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Team> getTeamList() {
        return teams.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Position> getPositionList() {
        return positions.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && teams.equals(otherAddressBook.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, teams);
    }
}
