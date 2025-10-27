package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.position.Position;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    public static final Position DEFAULT_POSITION = new Position("NONE");
    public static final boolean DEFAULT_CAPTAIN_STATUS = false;

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Injury> injuries = new HashSet<>();
    private final Team team;
    private final Position position;
    private boolean isCaptain;

    /**
     * Creates a Person object assigned to a team with the default injury status, position, and captain status.
     * Overloaded constructor sets the person's injury status to the default value {@code "FIT"} and position
     * to the default value {@code "NONE"}. The {@code isCaptain} field defaults to {@code false}.
     * All other fields must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Team team, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, team, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.team = team;
        this.tags.addAll(tags);
        this.position = DEFAULT_POSITION;
        this.injuries.add(Injury.DEFAULT_INJURY_STATUS);
        this.isCaptain = DEFAULT_CAPTAIN_STATUS;
    }

    /**
     * Creates a Person object assigned to a team with an explicit injury status, position, and captain status.
     * Overloaded constructor sets the person's injury status to the specified {@code injuries},
     * position to the specified {@code position}, and captain status to the specified {@code isCaptain}.
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Team team, Set<Tag> tags,
                  Position position, Set<Injury> injuries, boolean isCaptain) {
        requireAllNonNull(name, phone, email, address, team, tags, position, injuries, isCaptain);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.team = team;
        this.tags.addAll(tags);
        this.position = position;
        this.injuries.addAll(injuries);
        this.isCaptain = isCaptain;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Team getTeam() {
        return team;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isCaptain() {
        return isCaptain;
    }

    public void assignCaptain() {
        this.isCaptain = true;
    }

    public void stripCaptain() {
        this.isCaptain = false;
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable injury set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Injury> getInjuries() {
        return Collections.unmodifiableSet(injuries);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && team.equals(otherPerson.team)
                && tags.equals(otherPerson.tags)
                && position.equals(otherPerson.position)
                && injuries.equals(otherPerson.injuries)
                && isCaptain == otherPerson.isCaptain();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, team, tags, position, injuries, isCaptain);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("team", team)
                .add("position", position)
                .add("injuries", injuries)
                .add("isCaptain", isCaptain)
                .toString();
    }
}
