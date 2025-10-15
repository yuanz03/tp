package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTeams.U12;
import static seedu.address.testutil.TypicalTeams.U16;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.team.Team;
import seedu.address.testutil.ModelStub;

public class DeleteTeamCommandTest {

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteTeamCommand(null));
    }

    @Test
    public void execute_emptyTeamAcceptedByModel_deleteSuccessful() throws Exception {
        ModelStubAcceptingTeamDeleted modelStub = new ModelStubAcceptingTeamDeleted();
        Team validTeam = U16;
        modelStub.addTeam(validTeam);

        CommandResult commandResult = new DeleteTeamCommand(validTeam).execute(modelStub);

        assertEquals(String.format(DeleteTeamCommand.MESSAGE_SUCCESS, validTeam.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(0, modelStub.teamsInAddressBook.size());
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() {
        Team validTeam = U16;
        DeleteTeamCommand deleteCommand = new DeleteTeamCommand(validTeam);
        ModelStub modelStub = new ModelStubWithNoTeam();

        assertThrows(CommandException.class,
                String.format(DeleteTeamCommand.MESSAGE_TEAM_NOT_FOUND, validTeam.getName()), () ->
                        deleteCommand.execute(modelStub));
    }

    @Test
    public void execute_teamNotEmpty_throwsCommandException() {
        Team validTeam = U16;
        DeleteTeamCommand deleteCommand = new DeleteTeamCommand(validTeam);
        ModelStub modelStub = new ModelStubWithNonEmptyTeam(validTeam);

        assertThrows(CommandException.class,
                String.format(DeleteTeamCommand.MESSAGE_TEAM_NOT_EMPTY, validTeam.getName()), () ->
                        deleteCommand.execute(modelStub));
    }

    @Test
    public void execute_validEmptyTeam_success() {
        // Create a model with only teams, no persons assigned
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Team teamToDelete = U12;
        model.addTeam(teamToDelete);

        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(teamToDelete);

        String expectedMessage = String.format(DeleteTeamCommand.MESSAGE_SUCCESS, teamToDelete.getName());

        ModelManager expectedModel = new ModelManager(new AddressBook(), new UserPrefs());

        assertCommandSuccess(deleteTeamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_teamWithPersons_throwsCommandException() {
        // Use typical address book which has persons assigned to teams
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Team teamToDelete = U12; // U12 has persons assigned in typical data

        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(teamToDelete);

        String expectedMessage = String.format(DeleteTeamCommand.MESSAGE_TEAM_NOT_EMPTY, teamToDelete.getName());

        CommandTestUtil.assertCommandFailure(deleteTeamCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteTeamCommand deleteU12Command = new DeleteTeamCommand(U12);
        DeleteTeamCommand deleteU16Command = new DeleteTeamCommand(U16);

        // same object -> returns true
        assertTrue(deleteU12Command.equals(deleteU12Command));

        // same values -> returns true
        DeleteTeamCommand deleteU12CommandCopy = new DeleteTeamCommand(U12);
        assertTrue(deleteU12Command.equals(deleteU12CommandCopy));

        // different types -> returns false
        assertFalse(deleteU12Command.equals(1));

        // null -> returns false
        assertFalse(deleteU12Command.equals(null));

        // different team -> returns false
        assertFalse(deleteU12Command.equals(deleteU16Command));
    }

    @Test
    public void toStringMethod() {
        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(U16);
        String expected = DeleteTeamCommand.class.getCanonicalName() + "{toDelete=" + U16 + "}";
        assertEquals(expected, deleteTeamCommand.toString());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    private void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                      Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedMessage, result.getFeedbackToUser());
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * A Model stub that always returns false for hasTeam.
     */
    private static class ModelStubWithNoTeam extends ModelStub {
        @Override
        public boolean hasTeam(Team team) {
            return false;
        }
    }

    /**
     * A Model stub that contains a non-empty team.
     */
    private static class ModelStubWithNonEmptyTeam extends ModelStub {
        private final Team team;

        ModelStubWithNonEmptyTeam(Team team) {
            requireNonNull(team);
            this.team = team;
        }

        @Override
        public boolean hasTeam(Team team) {
            requireNonNull(team);
            return this.team.isSameTeam(team);
        }

        @Override
        public boolean isTeamEmpty(Team team) {
            requireNonNull(team);
            return false; // Team is not empty
        }
    }

    /**
     * A Model stub that always accepts the team being deleted.
     */
    private static class ModelStubAcceptingTeamDeleted extends ModelStub {
        final ArrayList<Team> teamsInAddressBook = new ArrayList<>();

        @Override
        public boolean hasTeam(Team team) {
            requireNonNull(team);
            return teamsInAddressBook.stream().anyMatch(team::isSameTeam);
        }

        @Override
        public void addTeam(Team team) {
            requireNonNull(team);
            teamsInAddressBook.add(team);
        }

        @Override
        public boolean isTeamEmpty(Team team) {
            requireNonNull(team);
            return true;
        }

        @Override
        public void deleteTeam(Team team) {
            requireNonNull(team);
            teamsInAddressBook.removeIf(t -> t.isSameTeam(team));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
