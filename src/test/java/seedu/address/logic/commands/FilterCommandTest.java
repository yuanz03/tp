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
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTeams.U12;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FilterByInjuryPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.position.FilterByPositionPredicate;
import seedu.address.model.position.Position;
import seedu.address.model.team.FilterByTeamPredicate;
import seedu.address.model.team.Team;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TeamBuilder;

public class FilterCommandTest {

    private Model model;
    private Model expectedModel;

    /**
     * Formats keywords by splitting on whitespace and joining with commas and quotes.
     * Used to match the formatting in FilterCommand.
     */
    private String formatKeywords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }

        return Arrays.stream(input.trim().split("\\s+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(", "));
    }


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
        assertCommandFailure(command, model, String.format(MESSAGE_INVALID_TEAM, NON_EXISTENT_TEAM));
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
                String.format(Messages.MESSAGE_NO_PLAYERS_IN_TEAM, formatKeywords(VALID_TEAM_AMY)));
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
        assertCommandFailure(command, model, String.format(MESSAGE_INVALID_TEAM, NON_EXISTENT_TEAM));
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
                formatKeywords(NON_EXISTENT_INJURY)), exception.getMessage());
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
        assertEquals(String.format(Messages.MESSAGE_INVALID_POSITION, NON_EXISTENT_POSITION), exception.getMessage());
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
        assertEquals(String.format(Messages.MESSAGE_INVALID_POSITION, NON_EXISTENT_POSITION), exception.getMessage());
    }

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
                formatKeywords(VALID_TEAM_AMY), formatKeywords(NON_EXISTENT_INJURY)), exception.getMessage());
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
        assertCommandFailure(command, model, String.format(MESSAGE_INVALID_TEAM, NON_EXISTENT_TEAM));
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
        assertEquals(String.format(Messages.MESSAGE_INVALID_POSITION, NON_EXISTENT_POSITION), exception.getMessage());
    }

    @Test
    public void execute_validPositionNoPlayers_throwsCommandException() {
        // Create a position that exists but no players have it
        Position emptyPosition = new Position(NON_EXISTENT_POSITION);
        model.addPosition(emptyPosition);

        FilterCommand command = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                new FilterByPositionPredicate(NON_EXISTENT_POSITION),
                Optional.empty(),
                Optional.empty(),
                Optional.of(NON_EXISTENT_POSITION));
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(Messages.MESSAGE_NO_PLAYERS_WITH_POSITION, NON_EXISTENT_POSITION),
                exception.getMessage());
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
                formatKeywords(VALID_TEAM_AMY), VALID_POSITION_BOB), exception.getMessage());
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

        // Update to match new message format with comma-separated keywords
        String expectedMessage = String.format(Messages.MESSAGE_NO_MATCHING_TEAM_INJURY_AND_POSITION,
                formatKeywords(VALID_TEAM_AMY), formatKeywords(VALID_INJURY_AMY), VALID_POSITION_AMY);
        assertEquals(expectedMessage, exception.getMessage());
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
        assertEquals(String.format(Messages.MESSAGE_NO_PLAYERS_WITH_INJURY, formatKeywords(NON_EXISTENT_INJURY)),
                exception.getMessage());
    }

    @Test
    public void execute_validPositionOnly_filtersCorrectly() {
        Model customModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedCustomModel = new ModelManager(new AddressBook(), new UserPrefs());

        // Add the position to both models first
        Position position = new Position(VALID_POSITION_AMY);
        customModel.addPosition(position);
        expectedCustomModel.addPosition(position);

        // Add a player with position
        Person playerWithPosition = new PersonBuilder(AMY).withPosition(VALID_POSITION_AMY).build();
        customModel.addPerson(playerWithPosition);
        expectedCustomModel.addPerson(playerWithPosition);

        FilterByPositionPredicate posPred = new FilterByPositionPredicate(VALID_POSITION_AMY);
        FilterCommand command = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                posPred,
                Optional.empty(),
                Optional.empty(),
                Optional.of(VALID_POSITION_AMY));

        // Update the expected model with the filter predicate
        expectedCustomModel.updateFilteredPersonList(posPred);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedCustomModel.getFilteredPersonList().size());

        // Use customModel for execution, expectedCustomModel for comparison
        assertCommandSuccess(command, customModel, expectedMessage, expectedCustomModel);

        assertEquals(1, expectedCustomModel.getFilteredPersonList().size());
        assertEquals(playerWithPosition, expectedCustomModel.getFilteredPersonList().get(0));
    }

    @Test
    public void execute_allThreeCriteria_filtersCorrectly() {
        // Create custom models
        Model customModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedCustomModel = new ModelManager(new AddressBook(), new UserPrefs());

        // Add position to both models
        Position position = new Position(VALID_POSITION_BOB);
        customModel.addPosition(position);
        expectedCustomModel.addPosition(position);

        Team team = new Team(VALID_TEAM_BOB);
        customModel.addTeam(team);
        expectedCustomModel.addTeam(team);

        // Build BOB with all three criteria: team, injury, and position
        Person bobWithAllCriteria = new PersonBuilder(BOB)
                .withPosition(VALID_POSITION_BOB)
                .build();

        customModel.addPerson(bobWithAllCriteria);
        expectedCustomModel.addPerson(bobWithAllCriteria);

        // Create predicates for all three criteria
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(VALID_TEAM_BOB);
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate(VALID_INJURY_BOB);
        FilterByPositionPredicate posPred = new FilterByPositionPredicate(VALID_POSITION_BOB);

        FilterCommand command = new FilterCommand(
                teamPred,
                injPred,
                posPred,
                Optional.of(VALID_TEAM_BOB),
                Optional.of(VALID_INJURY_BOB),
                Optional.of(VALID_POSITION_BOB));

        // Update expected model with the combined filter
        expectedCustomModel.updateFilteredPersonList(
                person -> teamPred.test(person) && injPred.test(person) && posPred.test(person));
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedCustomModel.getFilteredPersonList().size());

        // Execute command on customModel and compare with expectedCustomModel
        assertCommandSuccess(command, customModel, expectedMessage, expectedCustomModel);

        // Verify only BOB is shown (matches all three criteria)
        assertEquals(1, expectedCustomModel.getFilteredPersonList().size());
        assertEquals(bobWithAllCriteria, expectedCustomModel.getFilteredPersonList().get(0));
    }

    @Test
    public void execute_caseInsensitiveTeamName_filtersCorrectly() {
        FilterByTeamPredicate predicate = new FilterByTeamPredicate("u16"); // Lowercase team name
        FilterCommand command = new FilterCommand(predicate,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of("u16"), // Lowercase
                Optional.empty(),
                Optional.empty());
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleKeywordsTeam_filtersCorrectly() {
        Model customModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedCustomModel = new ModelManager(new AddressBook(), new UserPrefs());

        // Create teams
        Team u16Team = new Team("U16 Boys");
        Team u18Team = new Team("U18 Girls");
        customModel.addTeam(u16Team);
        customModel.addTeam(u18Team);
        expectedCustomModel.addTeam(u16Team);
        expectedCustomModel.addTeam(u18Team);

        // Create players with different teams
        Person u16Player = new PersonBuilder(AMY).withTeam(u16Team.getName()).build();
        Person u18Player = new PersonBuilder(BOB).withTeam(u18Team.getName()).build();

        customModel.addPerson(u16Player);
        customModel.addPerson(u18Player);
        expectedCustomModel.addPerson(u16Player);
        expectedCustomModel.addPerson(u18Player);

        // Test filtering by "U16 Boys" - should match U16 team
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate("U16 Boys");
        FilterCommand command = new FilterCommand(
                teamPred,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of("U16 Boys"),
                Optional.empty(),
                Optional.empty());

        expectedCustomModel.updateFilteredPersonList(teamPred);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedCustomModel.getFilteredPersonList().size());

        assertCommandSuccess(command, customModel, expectedMessage, expectedCustomModel);
        // Verify only U16 player is shown
        assertEquals(1, expectedCustomModel.getFilteredPersonList().size());
        assertEquals(u16Player, expectedCustomModel.getFilteredPersonList().get(0));
    }

    @Test
    public void execute_partialTeamKeywords_filtersCorrectly() {
        Model customModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedCustomModel = new ModelManager(new AddressBook(), new UserPrefs());

        // Create teams
        Team u16Team = new Team("U16 Boys");
        Team u18Team = new Team("U18 Girls");
        customModel.addTeam(u16Team);
        customModel.addTeam(u18Team);
        expectedCustomModel.addTeam(u16Team);
        expectedCustomModel.addTeam(u18Team);

        // Create players
        Person u16Player = new PersonBuilder(AMY).withTeam(u16Team.getName()).build();
        Person u18Player = new PersonBuilder(BOB).withTeam(u18Team.getName()).build();

        customModel.addPerson(u16Player);
        customModel.addPerson(u18Player);
        expectedCustomModel.addPerson(u16Player);
        expectedCustomModel.addPerson(u18Player);

        // Test filtering by "U16" - should match U16 team
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate("U16");
        FilterCommand command = new FilterCommand(
                teamPred,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of("U16"),
                Optional.empty(),
                Optional.empty());

        expectedCustomModel.updateFilteredPersonList(teamPred);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedCustomModel.getFilteredPersonList().size());

        assertCommandSuccess(command, customModel, expectedMessage, expectedCustomModel);
        // Verify only U16 player is shown
        assertEquals(1, expectedCustomModel.getFilteredPersonList().size());
        assertEquals(u16Player, expectedCustomModel.getFilteredPersonList().get(0));
    }

    @Test
    public void execute_caseInsensitiveTeam_filtersCorrectly() {
        Model customModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedCustomModel = new ModelManager(new AddressBook(), new UserPrefs());

        // Create team
        Team u16Team = new Team("U16 Boys");
        customModel.addTeam(u16Team);
        expectedCustomModel.addTeam(u16Team);

        // Create player
        Person u16Player = new PersonBuilder(AMY).withTeam(u16Team.getName()).build();
        customModel.addPerson(u16Player);
        expectedCustomModel.addPerson(u16Player);

        // Test filtering by "u16 boys" (lowercase) - should match U16 Boys team
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate("u16 boys");
        FilterCommand command = new FilterCommand(
                teamPred,
                FilterByInjuryPredicate.ALWAYS_TRUE,
                FilterByPositionPredicate.ALWAYS_TRUE,
                Optional.of("u16 boys"),
                Optional.empty(),
                Optional.empty());

        expectedCustomModel.updateFilteredPersonList(teamPred);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedCustomModel.getFilteredPersonList().size());

        assertCommandSuccess(command, customModel, expectedMessage, expectedCustomModel);
        // Verify player is shown (case insensitive match)
        assertEquals(1, expectedCustomModel.getFilteredPersonList().size());
        assertEquals(u16Player, expectedCustomModel.getFilteredPersonList().get(0));
    }
}
