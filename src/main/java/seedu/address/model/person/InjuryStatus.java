package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's injury status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInjuryStatus(String)}
 */
public class InjuryStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Injury statuses should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the injury status must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String injuryName;

    /**
     * Constructs an {@code InjuryStatus}.
     *
     * @param injuryStatus A valid injury status.
     */
    public InjuryStatus(String injuryStatus) {
        requireNonNull(injuryStatus);
        checkArgument(isValidInjuryStatus(injuryStatus), MESSAGE_CONSTRAINTS);
        this.injuryName = injuryStatus;
    }

    /**
     * Returns true if a given string is a valid injury status.
     */
    public static boolean isValidInjuryStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.injuryName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InjuryStatus)) {
            return false;
        }

        InjuryStatus otherInjuryStatus = (InjuryStatus) other;
        return this.injuryName.equals(otherInjuryStatus.injuryName);
    }

    @Override
    public int hashCode() {
        return this.injuryName.hashCode();
    }
}
