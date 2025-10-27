package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

class FilterInjuredPredicateTest {
    @Test
    void test_defaultInjury_returnsFalse() {
        FilterInjuredPredicate pred = new FilterInjuredPredicate();
        assertFalse(pred.test(new PersonBuilder().withInjuries(Injury.DEFAULT_INJURY_STATUS.getInjuryName()).build()));
    }
    @Test
    void test_nonDefaultInjury_returnsTrue() {
        FilterInjuredPredicate pred = new FilterInjuredPredicate();
        assertTrue(pred.test(new PersonBuilder().withInjuries("sprain").build()));
    }
}
