package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Tests that a Person’s injury is not the default.
 */
public class FilterInjuredPredicate implements Predicate<Person> {
    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        return person.getInjuries().stream().anyMatch(injury -> !injury.equals(Injury.DEFAULT_INJURY_STATUS));
    }
}
