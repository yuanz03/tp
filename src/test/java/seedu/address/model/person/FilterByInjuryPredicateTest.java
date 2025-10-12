package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FilterByInjuryPredicateTest {

    @Test
    public void equals_sameObject_returnsTrue() {
        FilterByInjuryPredicate pred = new FilterByInjuryPredicate("ACL");
        assertTrue(pred.equals(pred));
    }

    @Test
    public void equals_sameKeyword_returnsTrue() {
        FilterByInjuryPredicate pred1 = new FilterByInjuryPredicate("ACL");
        FilterByInjuryPredicate pred2 = new FilterByInjuryPredicate("ACL");
        assertTrue(pred1.equals(pred2));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        FilterByInjuryPredicate pred = new FilterByInjuryPredicate("ACL");
        assertFalse(pred.equals("not a predicate"));
    }

    @Test
    public void equals_null_returnsFalse() {
        FilterByInjuryPredicate pred = new FilterByInjuryPredicate("ACL");
        assertFalse(pred.equals(null));
    }

    @Test
    public void equals_differentKeyword_returnsFalse() {
        FilterByInjuryPredicate pred1 = new FilterByInjuryPredicate("ACL");
        FilterByInjuryPredicate pred2 = new FilterByInjuryPredicate("MCL");
        assertFalse(pred1.equals(pred2));
    }

    @Test
    public void toString_containsKeyword() {
        FilterByInjuryPredicate pred = new FilterByInjuryPredicate("ACL");
        String str = pred.toString();
        assertTrue(str.contains("injury keyword"));
        assertTrue(str.contains("ACL"));
    }

    @Test
    public void getKeyword_returnsKeyword() {
        FilterByInjuryPredicate pred = new FilterByInjuryPredicate("ACL");
        assertEquals("ACL", pred.getKeyword());
    }
}
