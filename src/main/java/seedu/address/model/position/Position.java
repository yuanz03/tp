package seedu.address.model.position;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a player position in the address book (e.g. LW, RW, ST).
 * <p>
 * Names are case-insensitive for equality and must be alphanumeric.
 */
public class Position {

    public static final String MESSAGE_CONSTRAINTS = "Position name can only contain alphanumeric characters.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    private final String name;

    /**
     * Constructs a {@code Position} with a validated name.
     *
     * @param name position name; must be non-null and alphanumeric.
     */
    public Position(String name) {
        requireAllNonNull(name);
        String trimmed = name.trim();
        checkArgument(isValidPositionName(trimmed), MESSAGE_CONSTRAINTS);
        this.name = trimmed;
    }

    /**
     * Returns true if the given string is a valid position name.
     */
    public static boolean isValidPositionName(String test) {
        return test != null && test.trim().matches(VALIDATION_REGEX);
    }

    /**
     * Returns the normalized name of this position.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns true if both positions have the same name (case-insensitive).
     */
    public boolean isSamePosition(Position other) {
        if (other == this) {
            return true;
        }
        return other != null && other.getName().equalsIgnoreCase(getName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Position)) {
            return false;
        }
        Position o = (Position) other;
        return o.getName().equalsIgnoreCase(getName());
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
