package seedu.address.model.injury;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an InjuryStatus in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInjuryName(String)}
 */
public class InjuryStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Injury statuses should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String injuryName;

    /**
     * Constructs a {@code InjuryStatus}.
     * Every field must be present and not null.
     *
     * @param name A valid injury name.
     */
    public InjuryStatus(String name) {
        requireNonNull(name);
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InjuryStatus)) {
            return false;
        }
        InjuryStatus otherInjuryStatus = (InjuryStatus) other;
        return otherInjuryStatus.injuryName.equalsIgnoreCase(getName());
    }

    @Override
    public int hashCode() {
        return this.injuryName.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("injury status", this.injuryName)
                .toString();
    }
}
