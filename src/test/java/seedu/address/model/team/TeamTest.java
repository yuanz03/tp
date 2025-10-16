package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalTeams.U12;
import static seedu.address.testutil.TypicalTeams.U16;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TeamBuilder;

public class TeamTest {

    @Test
    public void isSameTeam() {
        // same object -> returns true
        assertTrue(U12.isSameTeam(U12));

        // null -> returns false
        assertFalse(U12.isSameTeam(null));

        // different name -> returns false
        assertFalse(U16.isSameTeam(U12));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Team u12Copy = new TeamBuilder(U12).build();
        assertTrue(U12.equals(u12Copy));

        // same object -> returns true
        assertTrue(U12.equals(U12));

        // null -> returns false
        assertFalse(U12.equals(null));

        // different type -> returns false
        assertFalse(U12.equals(5));

        // different team -> returns false
        assertFalse(U12.equals(U16));

        // different name -> returns false
        Team editedU12 = new TeamBuilder(U12).withName("U14").build();
        assertFalse(U12.equals(editedU12));
    }

    @Test
    public void toStringMethod() {
        String expectedString = U12.getName();
        assertEquals(expectedString, U12.toString());
    }
}
