package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FilterByTeamPredicateTest {

    @Test
    public void equals_sameObject_returnsTrue() {
        FilterByTeamPredicate pred = new FilterByTeamPredicate("U12");
        assertTrue(pred.equals(pred));
    }

    @Test
    public void equals_sameTeamName_returnsTrue() {
        FilterByTeamPredicate pred1 = new FilterByTeamPredicate("U12");
        FilterByTeamPredicate pred2 = new FilterByTeamPredicate("U12");
        assertTrue(pred1.equals(pred2));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        FilterByTeamPredicate pred = new FilterByTeamPredicate("U12");
        assertFalse(pred.equals("not a predicate"));
    }

    @Test
    public void equals_null_returnsFalse() {
        FilterByTeamPredicate pred = new FilterByTeamPredicate("U12");
        assertFalse(pred.equals(null));
    }

    @Test
    public void equals_differentTeamName_returnsFalse() {
        FilterByTeamPredicate pred1 = new FilterByTeamPredicate("U12");
        FilterByTeamPredicate pred2 = new FilterByTeamPredicate("U16");
        assertFalse(pred1.equals(pred2));
    }

    @Test
    public void toString_containsTeamName() {
        FilterByTeamPredicate pred = new FilterByTeamPredicate("U12");
        String str = pred.toString();
        assertTrue(str.contains("team name"));
        assertTrue(str.contains("U12"));
    }

    @Test
    public void getTeamName_returnsTeamName() {
        FilterByTeamPredicate pred = new FilterByTeamPredicate("U12");
        assertEquals("U12", pred.getTeamName());
    }
}
