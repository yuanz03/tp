package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TEAM;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
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
        // U12 has persons in typical data
        FilterByTeamPredicate predicate = new FilterByTeamPredicate(U12.getName());
        FilterCommand command = new FilterCommand(predicate, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(U12.getName()), Optional.empty());
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentTeam_throwsCommandException() {
        FilterByTeamPredicate predicate = new FilterByTeamPredicate("NoSuchTeam");
        FilterCommand command = new FilterCommand(predicate, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(predicate.getTeamName()), Optional.empty());
        assertCommandFailure(command, model, MESSAGE_INVALID_TEAM);
    }

    @Test
    public void execute_teamHasNoPlayers_throwsCommandException() {
        // Create an empty model
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        // Add a team with no persons
        Team emptyTeam = new TeamBuilder().withName("LoneTeam").build();
        model.addTeam(emptyTeam);

        // Construct and execute the filter command
        FilterCommand command =
            new FilterCommand(new FilterByTeamPredicate(emptyTeam.getName()), FilterByInjuryPredicate.ALWAYS_TRUE,
                    Optional.of(emptyTeam.getName()), Optional.empty());

        assertCommandFailure(command, model,
                String.format(Messages.MESSAGE_NO_PLAYERS_IN_TEAM, emptyTeam.getName()));
    }

    @Test
    public void equals() {
        FilterByTeamPredicate p1 = new FilterByTeamPredicate("A");
        FilterByTeamPredicate p2 = new FilterByTeamPredicate("B");

        FilterCommand cmd1 = new FilterCommand(p1, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(p1.getTeamName()), Optional.empty());
        FilterCommand cmd1Copy = new FilterCommand(p1, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(p1.getTeamName()), Optional.empty());
        FilterCommand cmd2 = new FilterCommand(p2, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(p2.getTeamName()), Optional.empty());

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
        FilterCommand command = new FilterCommand(predicate, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(predicate.getTeamName()), Optional.empty());
        String str = command.toString();
        assertTrue(str.contains("teamPredicate=" + predicate.toString()));
    }

    @Test
    public void execute_validInjuryName_filtersCorrectly() {
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate("ACL");
        FilterCommand command = new FilterCommand(
                FilterByTeamPredicate.ALWAYS_TRUE,
                injPred,
                Optional.empty(),
                Optional.of("ACL"));
        expectedModel.updateFilteredPersonList(person -> injPred.test(person));
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_bothValidFilters_filtersCorrectly() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate(U12.getName());
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate("ACL");
        FilterCommand command = new FilterCommand(teamPred, injPred,
                Optional.of(U12.getName()), Optional.of("ACL"));
        expectedModel.updateFilteredPersonList(
                person -> teamPred.test(person) && injPred.test(person));
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }


    // No one matching both the injury and team
    @Test
    public void execute_noOneMatchingBoth_throwsCommandException() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate("U13");
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate("Nonexistent");
        FilterCommand command = new FilterCommand(
                teamPred,
                injPred,
                Optional.of("U13"),
                Optional.of("Nonexistent"));
        assertCommandFailure(command, model, MESSAGE_INVALID_TEAM);
    }

    // Matching team but no one matching injury (equivalent to both-present failure)
    @Test
    public void execute_matchingTeamButNoOneMatchingInjury_throwsCommandException() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate("U12");
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate("Nonexistent");
        FilterCommand command = new FilterCommand(
                teamPred,
                injPred,
                Optional.of("U12"),
                Optional.of("Nonexistent"));
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(Messages.MESSAGE_NO_MATCHING_TEAM_AND_INJURY,
                 "U12", "Nonexistent"), exception.getMessage());
    }

    // Matching injury but no one matching team
    @Test
    public void execute_matchingInjuryButNoOneMatchingTeam_throwsCommandException() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate("NoSuchTeam");
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate("ACL");
        FilterCommand command = new FilterCommand(
                teamPred,
                injPred,
                Optional.of("NoSuchTeam"),
                Optional.of("ACL"));
        assertCommandFailure(command, model, MESSAGE_INVALID_TEAM);
    }

    // toString: injury-only predicate
    @Test
    public void toString_injuryOnly_containsInjuryPredicate() {
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate("ACL");
        FilterCommand command = new FilterCommand(
            FilterByTeamPredicate.ALWAYS_TRUE,
            injPred,
            Optional.empty(),
            Optional.of("ACL"));
        String str = command.toString();
        assertTrue(str.contains("injuryPredicate=" + injPred.toString()));
    }

    // toString: both team and injury predicates
    @Test
    public void toString_teamAndInjury_containsBothPredicates() {
        FilterByTeamPredicate teamPred = new FilterByTeamPredicate("U12");
        FilterByInjuryPredicate injPred = new FilterByInjuryPredicate("ACL");
        FilterCommand command = new FilterCommand(
                teamPred,
                injPred,
                Optional.of("U12"),
                Optional.of("ACL"));
        String str = command.toString();
        assertTrue(str.contains("teamPredicate=" + teamPred.toString()));
        assertTrue(str.contains("injuryPredicate=" + injPred.toString()));
    }
}
