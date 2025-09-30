package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTeams.U12;
import static seedu.address.testutil.TypicalTeams.U16;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.team.Team;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.TeamBuilder;

public class AddTeamCommandTest {
    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTeamCommand(null));
    }

    @Test
    public void execute_teamAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTeamAdded modelStub = new ModelStubAcceptingTeamAdded();
        Team validTeam = new TeamBuilder().build();

        CommandResult commandResult = new AddTeamCommand(validTeam).execute(modelStub);

        assertEquals(String.format(AddTeamCommand.MESSAGE_SUCCESS, Messages.format(validTeam)),
            commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTeam), modelStub.teamsAdded);
    }

    @Test
    public void execute_duplicateTeam_throwsCommandException() {
        Team validTeam = new TeamBuilder().build();
        AddTeamCommand addCommand = new AddTeamCommand(validTeam);
        ModelStub modelStub = new ModelStubWithTeam(validTeam);

        assertThrows(CommandException.class, AddTeamCommand.MESSAGE_DUPLICATE_TEAM, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddTeamCommand addU12Command = new AddTeamCommand(U12);
        AddTeamCommand addU16Command = new AddTeamCommand(U16);

        // same object -> returns true
        assertTrue(addU12Command.equals(addU12Command));

        // same values -> returns true
        AddTeamCommand addTeamACommandCopy = new AddTeamCommand(U12);
        assertTrue(addU12Command.equals(addTeamACommandCopy));

        // different types -> returns false
        assertFalse(addU12Command.equals(1));

        // null -> returns false
        assertFalse(addU12Command.equals(null));

        // different team -> returns false
        assertFalse(addU12Command.equals(addU16Command));
    }

    @Test
    public void toStringTest() {
        AddTeamCommand addTeamCommand = new AddTeamCommand(U16);
        String expected = AddTeamCommand.class.getCanonicalName() + "{toAdd=" + U16 + "}";
        assertEquals(expected, addTeamCommand.toString());
    }

    /**
     * A Model stub that contains a single team.
     */
    private class ModelStubWithTeam extends ModelStub {
        private final Team team;

        ModelStubWithTeam(Team team) {
            requireNonNull(team);
            this.team = team;
        }

        @Override
        public boolean hasTeam(Team team) {
            requireNonNull(team);
            return this.team.isSameTeam(team);
        }
    }

    /**
     * A Model stub that always accept the team being added.
     */
    private class ModelStubAcceptingTeamAdded extends ModelStub {
        final ArrayList<Team> teamsAdded = new ArrayList<>();

        @Override
        public boolean hasTeam(Team team) {
            requireNonNull(team);
            return teamsAdded.stream().anyMatch(team::isSameTeam);
        }

        @Override
        public void addTeam(Team team) {
            requireNonNull(team);
            teamsAdded.add(team);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
