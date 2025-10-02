package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTeams.U12;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.team.Team;

public class JsonAdaptedTeamTest {
    private static final String INVALID_NAME = "T@am1";

    private static final String VALID_NAME = "U12";

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedTeam team = new JsonAdaptedTeam(INVALID_NAME);
        String expectedMessage = Team.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, team::toModelType);
    }

    @Test
    public void toModelType_validTeamDetails_returnsTeam() throws Exception {
        JsonAdaptedTeam team = new JsonAdaptedTeam(VALID_NAME);
        assertEquals(U12, team.toModelType());
    }

}
