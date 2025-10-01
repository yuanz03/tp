package seedu.address.testutil;

import seedu.address.model.team.Team;

/**
 * A utility class containing a list of {@code Team} objects to be used in tests.
 */
public class TypicalTeams {
    public static final Team U12 = new TeamBuilder().withName("U12").build();
    public static final Team U16 = new TeamBuilder().withName("U16").build();

    private TypicalTeams() {
    } // prevents instantiation
}
