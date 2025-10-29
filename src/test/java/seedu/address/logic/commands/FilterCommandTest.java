package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TEAM;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXISTENT_INJURY;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXISTENT_POSITION;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXISTENT_TEAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INJURY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INJURY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTeams.U12;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FilterByInjuryPredicate;
import seedu.address.model.position.FilterByPositionPredicate;
import seedu.address.model.position.Position;
import seedu.address.model.team.FilterByTeamPredicate;
import seedu.address.model.team.Team;
import seedu.address.testutil.TeamBuilder;

public class FilterCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validTeamName_filtersCorrectly() {
        FilterByTeamPredicate predicate = new FilterByTeamPredicate(VALID_TEAM_AMY);
        FilterCommand command = new FilterCommand(predicate,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(VALID_TEAM_AMY),
                Optional.empty(),
                Optional.empty());
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentTeam_throwsCommandException() {
        FilterByTeamPredicate predicate = new FilterByTeamPredicate(NON_EXISTENT_TEAM);
        FilterCommand command = new FilterCommand(predicate,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(NON_EXISTENT_TEAM),
                Optional.empty(),
                Optional.empty());
        assertCommandFailure(command, model, MESSAGE_INVALID_TEAM);
    }

    @Test
    public void execute_teamHasNoPlayers_throwsCommandException() {
        // Create an empty model
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        // Add a team with no persons
        Team emptyTeam = new TeamBuilder().withName(VALID_TEAM_AMY).build();
        model.addTeam(emptyTeam);

        // Construct and execute the filter command
        FilterCommand command =
            new FilterCommand(new FilterByTeamPredicate(VALID_TEAM_AMY),
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(VALID_TEAM_AMY),
                Optional.empty(),
                Optional.empty());

        assertCommandFailure(command, model,
                String.format(Messages.MESSAGE_NO_PLAYERS_IN_TEAM, VALID_TEAM_AMY));
    }

    @Test
    public void equals() {
        FilterByTeamPredicate p1 = new FilterByTeamPredicate("A");
        FilterByTeamPredicate p2 = new FilterByTeamPredicate("B");

        FilterCommand cmd1 = new FilterCommand(p1,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(p1.getTeamName()),
                Optional.empty(),
                Optional.empty());
        FilterCommand cmd1Copy = new FilterCommand(p1,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(p1.getTeamName()),
                Optional.empty(),
                Optional.empty());
        FilterCommand cmd2 = new FilterCommand(p2,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(p2.getTeamName()),
                Optional.empty(),
                Optional.empty());

        // same object
        assertTrue(cmd1.equals(cmd1));
        // same values
        assertTrue(cmd1.equals(cmd1Copy));
        // different type
        assertFalse(cmd1.equals(1));
        // null
        assertFalse(cmd1.equals(null));
        // different predicate
        assertFalse(cmd1.equals(cmd2));
    }

    @Test
    public void toString_containsPredicate() {
        FilterByTeamPredicate predicate = new FilterByTeamPredicate(U12.getName());
        FilterCommand command = new FilterCommand(predicate,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(predicate.getTeamName()),
                Optional.empty(),
                Optional.empty());
        String str = command.toString();
        assertTrue(str.contains("teamPredicate=" + predicate.toString()));
    }

    @Test
    public void execute_validInjuryName_filtersCorrectly() {
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(VALID_INJURY_BOB);
        FilterCommand command = new FilterCommand(FilterByTeamPredicate.ALWAYS_TRUE,
                injPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.empty(),
                Optional.of(VALID_INJURY_BOB),
                Optional.empty());
        expectedModel.updateFilteredPersonList(person -> injPred.test(person));
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_bothValidFilters_filtersCorrectly() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(VALID_TEAM_BOB);
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(VALID_INJURY_BOB);
        FilterCommand command = new FilterCommand(teamPred,
                injPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(VALID_TEAM_BOB),
                Optional.of(VALID_INJURY_BOB),
                Optional.empty());
        expectedModel.updateFilteredPersonList(
                person -> teamPred.test(person) && injPred.test(person));
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noOneMatchingTeamAndInjury_throwsCommandException() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(NON_EXISTENT_TEAM);
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(NON_EXISTENT_INJURY);
        FilterCommand command = new FilterCommand(
                teamPred,
                injPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(NON_EXISTENT_TEAM),
                Optional.of(NON_EXISTENT_INJURY),
                Optional.empty());
        assertCommandFailure(command, model, MESSAGE_INVALID_TEAM);
    }

    @Test
    public void execute_noOneMatchingInjury_throwsCommandException() {
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(NON_EXISTENT_INJURY);
        FilterCommand command = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                injPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.empty(),
                Optional.of(NON_EXISTENT_INJURY),
                Optional.empty());
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(Messages.MESSAGE_NO_PLAYERS_WITH_INJURY,
                NON_EXISTENT_INJURY), exception.getMessage());
    }

    @Test
    public void execute_noOneMatchingPosition_throwsCommandException() {
        FilterByPositionPredicate posPred = new FilterByPositionPredicate(NON_EXISTENT_POSITION);
        FilterCommand command = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                posPred,
                Optional.empty(),
                Optional.empty(),
                Optional.of(NON_EXISTENT_POSITION));
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_POSITION, exception.getMessage());
    }

    @Test
    public void execute_noOneMatchingPositionTeam_throwsCommandException() {
        FilterByPositionPredicate posPred = new FilterByPositionPredicate(NON_EXISTENT_POSITION);
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(VALID_TEAM_AMY);
        FilterCommand command = new FilterCommand(
                teamPred,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                posPred,
                Optional.of(VALID_TEAM_AMY),
                Optional.empty(),
                Optional.of(NON_EXISTENT_POSITION));
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_POSITION, exception.getMessage());
    }

    // Matching team but no one matching injury (equivalent to both-present failure)
    @Test
    public void execute_matchingTeamButNoOneMatchingInjury_throwsCommandException() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(VALID_TEAM_AMY);
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(NON_EXISTENT_INJURY);
        FilterCommand command = new FilterCommand(
                teamPred,
                injPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(VALID_TEAM_AMY),
                Optional.of(NON_EXISTENT_INJURY),
                Optional.empty());
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(Messages.MESSAGE_NO_MATCHING_TEAM_AND_INJURY,
                 VALID_TEAM_AMY, NON_EXISTENT_INJURY), exception.getMessage());
    }

    @Test
    public void execute_matchingInjuryButNoOneMatchingTeam_throwsCommandException() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(NON_EXISTENT_TEAM);
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(VALID_INJURY_BOB);
        FilterCommand command = new FilterCommand(
                teamPred,
                injPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(NON_EXISTENT_TEAM),
                Optional.of(VALID_INJURY_BOB),
                Optional.empty());
        assertCommandFailure(command, model, MESSAGE_INVALID_TEAM);
    }

    // toString: injury-only predicate
    @Test
    public void toString_injuryOnly_containsInjuryPredicate() {
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(VALID_INJURY_BOB);
        FilterCommand command = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                injPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.empty(),
                Optional.of(VALID_INJURY_BOB),
                Optional.empty());
        String str = command.toString();
        assertTrue(str.contains("injuryPredicate=" + injPred.toString()));
    }

    // toString: both team and injury predicates
    @Test
    public void toString_teamAndInjury_containsBothPredicates() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(VALID_TEAM_AMY);
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(VALID_INJURY_AMY);
        FilterCommand command = new FilterCommand(
                teamPred,
                injPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(VALID_TEAM_AMY),
                Optional.of(VALID_INJURY_AMY),
                Optional.empty());
        String str = command.toString();
        assertTrue(str.contains("teamPredicate=" + teamPred.toString()));
        assertTrue(str.contains("injuryPredicate=" + injPred.toString()));
    }

    // toString: both team and injury predicates
    @Test
    public void toString_teamInjuryAndPosition_containsAllPredicates() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(VALID_TEAM_AMY);
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(VALID_INJURY_AMY);
        FilterByPositionPredicate posPred = new FilterByPositionPredicate(VALID_POSITION_AMY);
        FilterCommand command = new FilterCommand(
                teamPred,
                injPred,
                posPred,
                Optional.of(VALID_TEAM_AMY),
                Optional.of(VALID_INJURY_AMY),
                Optional.of(VALID_POSITION_AMY));
        String str = command.toString();
        assertTrue(str.contains("teamPredicate=" + teamPred.toString()));
        assertTrue(str.contains("injuryPredicate=" + injPred.toString()));
        assertTrue(str.contains("positionPredicate=" + posPred.toString()));
    }

    @Test
    public void execute_allNotMatching_throwsCommandException() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(VALID_TEAM_AMY);
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(NON_EXISTENT_INJURY);
        FilterByPositionPredicate posPred = new FilterByPositionPredicate(NON_EXISTENT_POSITION);
        FilterCommand command = new FilterCommand(
                teamPred,
                injPred,
                posPred,
                Optional.of(VALID_TEAM_AMY),
                Optional.of(NON_EXISTENT_INJURY),
                Optional.of(NON_EXISTENT_POSITION));
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_POSITION, exception.getMessage());
    }

    @Test
    public void execute_validPositionNoPlayers_throwsCommandException() {
        // Create a position that exists but no players have it
        Position emptyPosition = new Position("GK"); // Assuming GK doesn't exist in typical data
        model.addPosition(emptyPosition);

        FilterCommand command = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                new FilterByPositionPredicate("GK"),
                Optional.empty(),
                Optional.empty(),
                Optional.of("GK"));
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(Messages.MESSAGE_NO_PLAYERS_WITH_POSITION, "GK"), exception.getMessage());
    }

    @Test
    public void execute_validTeamAndPositionNoOverlap_throwsCommandException() {
        model.addPosition(new Position(VALID_POSITION_BOB));

        // Both team and position exist, but no player has both
        FilterCommand command = new FilterCommand(
                new FilterByTeamPredicate(VALID_TEAM_AMY),
                FilterByInjuryPredicate.ALWAYS_TRUE,
                new FilterByPositionPredicate(VALID_POSITION_BOB),
                Optional.of(VALID_TEAM_AMY),
                Optional.empty(),
                Optional.of(VALID_POSITION_BOB));
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(Messages.MESSAGE_NO_MATCHING_TEAM_AND_POSITION,
                VALID_TEAM_AMY, VALID_POSITION_BOB), exception.getMessage());
    }

    @Test
    public void execute_allValidNoMatchingPlayer_throwsCommandException() {
        model.addPosition(new Position(VALID_POSITION_AMY));

        // All criteria valid but no single player matches all three
        FilterCommand command = new FilterCommand(
                new FilterByTeamPredicate(VALID_TEAM_AMY),
                new FilterByInjuryPredicate(VALID_INJURY_AMY),
                new FilterByPositionPredicate(VALID_POSITION_AMY),
                Optional.of(VALID_TEAM_AMY),
                Optional.of(VALID_INJURY_AMY),
                Optional.of(VALID_POSITION_AMY));
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(Messages.MESSAGE_NO_MATCHING_TEAM_INJURY_AND_POSITION,
                VALID_TEAM_AMY, VALID_INJURY_AMY, VALID_POSITION_AMY), exception.getMessage());
    }

    @Test
    public void execute_teamNotPresent_noValidation() {
        // Test that when team is not present, no team validation occurs
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(VALID_INJURY_BOB);
        FilterCommand command = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                injPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.empty(), // Team NOT present
                Optional.of(VALID_INJURY_BOB),
                Optional.empty());

        // Should execute without team validation errors
        expectedModel.updateFilteredPersonList(injPred);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_positionNotPresent_noValidation() {
        // Test that when position is not present, no position validation occurs
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(VALID_TEAM_AMY);
        FilterCommand command = new FilterCommand(
                teamPred,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of(VALID_TEAM_AMY),
                Optional.empty(),
                Optional.empty()); // Position NOT present

        // Should execute without position validation errors
        expectedModel.updateFilteredPersonList(teamPred);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_injuryOnlyNoMatchingPlayers_throwsCommandException() {
        // Test the final else branch in createNoMatchingPlayersException()
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(NON_EXISTENT_INJURY);
        FilterCommand command = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                injPred,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.empty(), // No team
                Optional.of(NON_EXISTENT_INJURY),
                Optional.empty()); // No position

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(Messages.MESSAGE_NO_PLAYERS_WITH_INJURY, NON_EXISTENT_INJURY),
                        exception.getMessage());
    }

    @Test
    public void execute_emptyFilters_coversAssertions() {
        FilterCommand command = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        assertCommandSuccess(command, model,
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                expectedModel);
    }
}
