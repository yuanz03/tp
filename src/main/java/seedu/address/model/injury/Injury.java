package seedu.address.model.injury;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Injury in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Injury {
    public static final String MESSAGE_CONSTRAINTS =
            "Injuries should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    // Identity fields
    private final String injuryName;

    /**
     * Constructs a {@code Injury}.
     * Every field must be present and not null.
     *
     * @param name A valid injury name.
     */
    public Injury(String name) {
        requireAllNonNull(name);
        checkArgument(isValidInjuryName(name), MESSAGE_CONSTRAINTS);
        this.injuryName = name;
    }

    /**
     * Returns true if a given string is a valid injury name.
     */
    public static boolean isValidInjuryName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getName() {
        return this.injuryName;
    }

    /**
     * Returns true if both injuries have the same name.
     * This defines a weaker notion of equality between two injuries.
     */
    public boolean isSameInjury(Injury otherInjury) {
        if (otherInjury == this) {
            return true;
        }

        return otherInjury != null
                && otherInjury.getName().equals(getName());
    }

    /**
     * Returns true if both injuries have the same identity and data fields.
     * This defines a stronger notion of equality between two injuries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Injury)) {
            return false;
        }
        Injury otherInjury = (Injury) other;
        return otherInjury.injuryName.equalsIgnoreCase(getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.injuryName.toLowerCase());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("injury status", this.injuryName)
                .toString();
    }
}
