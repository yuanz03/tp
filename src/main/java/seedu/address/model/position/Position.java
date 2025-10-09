package seedu.address.model.position;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Position in the address book.
 */
public class Position {

    public static final String MESSAGE_CONSTRAINTS = "Position name can only contain alphanumeric characters.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    private final String name;

    public Position(String name) {
        requireAllNonNull(name);
        String trimmed = name.trim();
        checkArgument(isValidPositionName(trimmed), MESSAGE_CONSTRAINTS);
        this.name = trimmed;
    }

    public static boolean isValidPositionName(String test) {
        return test != null && test.trim().matches(VALIDATION_REGEX);
    }

    public String getName() {
        return name;
    }

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
        return new ToStringBuilder(this)
                .add("name", name)
                .toString();
    }
}


