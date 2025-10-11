package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class FilterByTeamPredicateTest {

    @Test
    public void equals_differentObjectType_returnsFalse() {
        FilterByTeamPredicate predicate = new FilterByTeamPredicate("U12");
        assertFalse(predicate.equals("not a predicate"));
    }
}
