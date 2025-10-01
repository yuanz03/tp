package seedu.address.model.team;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents a Team in the address book.
 */
public class Team {
    // Identity fields
    private final Name name;

    // Data fields
    private final Set<Person> members = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Team(Name name, Set<Person> members) {
        requireAllNonNull(name, members);
        this.name = name;
        this.members.addAll(members);
    }

    public Name getName() {
        return name;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    /**
     * Returns true if both teams have the same name.
     * This defines a weaker notion of equality between two teams.
     */
    public boolean isSameTeam(Team otherTeam) {
        if (otherTeam == this) {
            return true;
        }

        return otherTeam != null
            && otherTeam.getName().equals(getName());
    }

    /**
     * Returns true if both teams have the same identity and data fields.
     * This defines a stronger notion of equality between two teams.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Team)) {
            return false;
        }
        Team otherTeam = (Team) other;
        return otherTeam.getName().equals(getName())
            && otherTeam.getMembers().equals(getMembers());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, members);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", name)
            .add("members", members)
            .toString();
    }
}
