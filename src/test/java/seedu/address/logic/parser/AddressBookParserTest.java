package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.INJURY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INJURY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTeams.U16;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTeamCommand;
import seedu.address.logic.commands.AssignInjuryCommand;
import seedu.address.logic.commands.AssignTeamCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCaptainCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListTeamCommand;
import seedu.address.logic.commands.MakeCaptainCommand;
import seedu.address.logic.commands.StripCaptainCommand;
import seedu.address.logic.commands.UnassignInjuryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TeamUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        Name name = new Name(VALID_NAME_AMY);
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + PLAYER_DESC_AMY);
        assertEquals(new DeleteCommand(name), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + PLAYER_DESC_AMY + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(new Name(VALID_NAME_AMY), descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addTeam() throws Exception {
        AddTeamCommand command = (AddTeamCommand) parser.parseCommand(TeamUtil.getAddCommand(U16));
        assertEquals(new AddTeamCommand(U16), command);
    }

    @Test
    public void parseCommand_assignTeam() throws Exception {
        Name name = new Name(VALID_NAME_AMY);
        AssignTeamCommand command = (AssignTeamCommand)
                parser.parseCommand(TeamUtil.getAssignTeamCommand(name, U16));
        assertEquals(new AssignTeamCommand(name, U16), command);
    }

    @Test
    public void parseCommand_deleteTeam() throws Exception {
        Team team = U16;
        DeleteTeamCommand command = (DeleteTeamCommand) parser.parseCommand(TeamUtil.getDeleteTeamCommand(team));
        assertEquals(new DeleteTeamCommand(team), command);
    }

    @Test
    public void parseCommand_listTeams() throws Exception {
        assertTrue(parser.parseCommand(ListTeamCommand.COMMAND_WORD) instanceof ListTeamCommand);
    }

    @Test
    public void parseCommand_listTeams_withExtraArgs() throws Exception {
        assertTrue(parser.parseCommand(ListTeamCommand.COMMAND_WORD + " 3") instanceof ListTeamCommand);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        // no args
        assertTrue(parser.parseCommand("filter tm/" + U16.getName()) instanceof FilterCommand);
        // with extra whitespace and args
        assertTrue(parser.parseCommand("  filter tm/" + U16.getName() + "  ") instanceof FilterCommand);
    }

    @Test
    public void parseCommand_assignInjury() throws Exception {
        Name name = new Name(VALID_NAME_AMY);
        Injury injury = new Injury(VALID_INJURY_AMY);
        AssignInjuryCommand command = (AssignInjuryCommand) parser.parseCommand(
                AssignInjuryCommand.COMMAND_WORD + PLAYER_DESC_AMY + INJURY_DESC_AMY);

        assertEquals(new AssignInjuryCommand(name, injury), command);
    }

    @Test
    public void parseCommand_unassignInjury() throws Exception {
        Name name = new Name(VALID_NAME_AMY);
        Injury injury = new Injury(VALID_INJURY_AMY);
        UnassignInjuryCommand command = (UnassignInjuryCommand) parser.parseCommand(
                UnassignInjuryCommand.COMMAND_WORD + PLAYER_DESC_AMY + INJURY_DESC_AMY);

        assertEquals(new UnassignInjuryCommand(name, injury), command);
    }

    @Test
    public void parseCommand_makeCaptain() throws Exception {
        Name name = new Name(VALID_NAME_AMY);
        assertEquals(new MakeCaptainCommand(name),
                parser.parseCommand(MakeCaptainCommand.COMMAND_WORD + PLAYER_DESC_AMY));
    }

    @Test
    public void parseCommand_stripCaptain() throws Exception {
        Name name = new Name(VALID_NAME_AMY);
        assertEquals(new StripCaptainCommand(name),
                parser.parseCommand(StripCaptainCommand.COMMAND_WORD + PLAYER_DESC_AMY));
    }

    @Test
    public void parseCommand_filterCaptains() throws Exception {
        assertTrue(parser.parseCommand(FilterCaptainCommand.COMMAND_WORD) instanceof FilterCaptainCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
