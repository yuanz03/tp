package seedu.address.model.team;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Team in the address book.
 */
public class Team {

    public static final String MESSAGE_CONSTRAINTS = "Team names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    // Identity fields
    private final String name;

    /**
     * Every field must be present and not null.
     */
    public Team(String name) {
        requireAllNonNull(name);
        checkArgument(isValidTeamName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid team name.
     */
    public static boolean isValidTeamName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getName() {
        return name;
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
            && otherTeam.getName().equalsIgnoreCase(getName());
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
        return otherTeam.getName().equalsIgnoreCase(getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
