package seedu.address.model.position;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FilterByPositionPredicateTest {

    @Test
    public void test_alwaysTrue_returnsTrueForAnyPerson() {
        FilterByPositionPredicate alwaysTrue = FilterByPositionPredicate.ALWAYS_TRUE;
        Person alice = new PersonBuilder().withPosition("GK").build();
        assertTrue(alwaysTrue.test(alice));
    }

    @Test
    public void test_validPosition_matchesOnlyThatPosition() {
        FilterByPositionPredicate pred = new FilterByPositionPredicate("FW");
        Person fw = new PersonBuilder().withPosition("FW").build();
        Person mf = new PersonBuilder().withPosition("MF").build();
        assertTrue(pred.test(fw));
        assertFalse(pred.test(mf));
    }

    @Test
    public void test_invalidPosition_returnsFalseForAnyPerson() {
        FilterByPositionPredicate pred = new FilterByPositionPredicate("123");
        Person alice = new PersonBuilder().withPosition("GK").build();
        assertFalse(pred.test(alice));
    }
}
