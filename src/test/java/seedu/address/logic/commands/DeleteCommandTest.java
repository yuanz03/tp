package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXISTENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXISTENT_TEAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME_16;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPlayerName_throwsCommandException() {
        Name nonExistentName = new Name(NON_EXISTENT_NAME);
        DeleteCommand deleteCommand = DeleteCommand.createDeletePlayerCommand(nonExistentName);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_PERSON_NOT_FOUND,
                nonExistentName));
    }

    @Test
    public void execute_validPlayerName_success() {
        Person personToDelete = ALICE;
        DeleteCommand deleteCommand = DeleteCommand.createDeletePlayerCommand(personToDelete.getName());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() {
        Team nonExistentTeam = new Team(NON_EXISTENT_TEAM);
        DeleteCommand deleteCommand = DeleteCommand.createDeleteTeamCommand(nonExistentTeam);

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_TEAM_NOT_FOUND, nonExistentTeam.getName()));
    }

    @Test
    public void execute_teamNotEmpty_throwsCommandException() {
        Team teamWithPlayers = ALICE.getTeam();
        DeleteCommand deleteCommand = DeleteCommand.createDeleteTeamCommand(teamWithPlayers);

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_TEAM_NOT_EMPTY, teamWithPlayers.getName()));
    }

    @Test
    public void equals() {
        Name aliceName = ALICE.getName();
        Name bensonName = BENSON.getName();
        Team teamAmy = new Team(VALID_TEAM_AMY);
        Team teamU16 = new Team(VALID_TEAM_NAME_16);

        DeleteCommand deleteAliceCommand = DeleteCommand.createDeletePlayerCommand(aliceName);
        DeleteCommand deleteBensonCommand = DeleteCommand.createDeletePlayerCommand(bensonName);
        DeleteCommand deleteTeamAmyCommand = DeleteCommand.createDeleteTeamCommand(teamAmy);
        DeleteCommand deleteTeamU16Command = DeleteCommand.createDeleteTeamCommand(teamU16);

        // same object -> returns true
        assertTrue(deleteAliceCommand.equals(deleteAliceCommand));

        // same values (player) -> returns true
        DeleteCommand deleteAliceCommandCopy = DeleteCommand.createDeletePlayerCommand(aliceName);
        assertTrue(deleteAliceCommand.equals(deleteAliceCommandCopy));

        // same values (team) -> returns true
        DeleteCommand deleteTeamAmyCommandCopy = DeleteCommand.createDeleteTeamCommand(teamAmy);
        assertTrue(deleteTeamAmyCommand.equals(deleteTeamAmyCommandCopy));

        // different types -> returns false
        assertFalse(deleteAliceCommand.equals(1));

        // null -> returns false
        assertFalse(deleteAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteAliceCommand.equals(deleteBensonCommand));

        // different team -> returns false
        assertFalse(deleteTeamAmyCommand.equals(deleteTeamU16Command));

        // player vs team -> returns false
        assertFalse(deleteAliceCommand.equals(deleteTeamAmyCommand));
    }

    @Test
    public void toStringMethod() {
        Name targetName = new Name(VALID_NAME_AMY);
        DeleteCommand deleteCommand = DeleteCommand.createDeletePlayerCommand(targetName);
        String expected = DeleteCommand.class.getCanonicalName()
                + "{personToDelete=" + targetName + ", teamToDelete=null}";
        assertEquals(expected, deleteCommand.toString());

        Team targetTeam = new Team(VALID_TEAM_AMY);
        DeleteCommand deleteTeamCommand = DeleteCommand.createDeleteTeamCommand(targetTeam);
        String expectedTeam = DeleteCommand.class.getCanonicalName()
                + "{personToDelete=null, teamToDelete=" + targetTeam + "}";
        assertEquals(expectedTeam, deleteTeamCommand.toString());
    }

    @Test
    public void createDeletePlayerCommand_validName_success() {
        Name validName = new Name(VALID_NAME_AMY);
        DeleteCommand deleteCommand = DeleteCommand.createDeletePlayerCommand(validName);

        // Verify the command was created successfully
        assertTrue(deleteCommand instanceof DeleteCommand);

        // Verify it's a delete player command (not a delete team command)
        DeleteCommand anotherPlayerCommand = DeleteCommand.createDeletePlayerCommand(validName);
        assertEquals(deleteCommand, anotherPlayerCommand);
    }

    @Test
    public void createDeletePlayerCommand_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                DeleteCommand.createDeletePlayerCommand(null));
    }

    @Test
    public void createDeleteTeamCommand_validTeam_success() {
        Team validTeam = new Team(VALID_TEAM_AMY);
        DeleteCommand deleteCommand = DeleteCommand.createDeleteTeamCommand(validTeam);
        assertTrue(deleteCommand instanceof DeleteCommand);
    }

    @Test
    public void createDeleteTeamCommand_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                DeleteCommand.createDeleteTeamCommand(null));
    }

    @Test
    public void createDeletePlayerCommand_differentNames_createsDistinctCommands() {
        Name name1 = new Name(VALID_NAME_AMY);
        Name name2 = new Name(VALID_NAME_BOB);

        DeleteCommand command1 = DeleteCommand.createDeletePlayerCommand(name1);
        DeleteCommand command2 = DeleteCommand.createDeletePlayerCommand(name2);

        // Verify commands are not equal
        assertFalse(command1.equals(command2));
    }

    @Test
    public void createDeleteTeamCommand_differentTeams_createsDistinctCommands() {
        Team team1 = new Team(VALID_TEAM_AMY);
        Team team2 = new Team(VALID_TEAM_NAME_16);

        DeleteCommand command1 = DeleteCommand.createDeleteTeamCommand(team1);
        DeleteCommand command2 = DeleteCommand.createDeleteTeamCommand(team2);

        // Verify commands are not equal
        assertFalse(command1.equals(command2));
    }

    @Test
    public void createMethods_playerVsTeam_createsDistinctCommandTypes() {
        Name playerName = new Name(VALID_NAME_AMY);
        Team team = new Team(VALID_TEAM_AMY);

        DeleteCommand playerCommand = DeleteCommand.createDeletePlayerCommand(playerName);
        DeleteCommand teamCommand = DeleteCommand.createDeleteTeamCommand(team);

        // Player and team delete commands should not be equal
        assertFalse(playerCommand.equals(teamCommand));

        // Their string representations should be different
        assertFalse(playerCommand.toString().equals(teamCommand.toString()));
    }

}
