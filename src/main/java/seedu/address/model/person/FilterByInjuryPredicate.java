package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s injuries contain the exact injury name (case-insensitive).
 */
public class FilterByInjuryPredicate implements Predicate<Person> {
    public static final FilterByInjuryPredicate ALWAYS_TRUE = new FilterByInjuryPredicate("");
    private final String injuryName;

    /**
     * Constructs a FilterByInjuryPredicate to filter persons by exact injury name match.
     */
    public FilterByInjuryPredicate(String injuryName) {
        requireNonNull(injuryName);
        this.injuryName = injuryName;
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        return isEmptyInjuryName() || hasExactInjury(person);
    }

    /**
     * Checks if the injury name is empty (always matches).
     */
    private boolean isEmptyInjuryName() {
        return injuryName.isEmpty();
    }

    /**
     * Checks if the person has the exact injury (case-insensitive).
     */
    private boolean hasExactInjury(Person person) {
        return person.getInjuries().stream()
                .anyMatch(injury -> injury.toString().equalsIgnoreCase(injuryName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterByInjuryPredicate)) {
            return false;
        }

        FilterByInjuryPredicate otherFilterByInjuryPredicate = (FilterByInjuryPredicate) other;
        return injuryName.equals(otherFilterByInjuryPredicate.injuryName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("injury name", injuryName).toString();
    }

    public String getInjuryName() {
        return injuryName;
    }
}
