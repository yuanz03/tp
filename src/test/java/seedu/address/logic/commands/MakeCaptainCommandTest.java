package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains unit tests for {@code MakeCaptainCommand}.
 */
public class MakeCaptainCommandTest {

    @Test
    public void execute_personExistsAndNotCaptain_marksAsCaptain() throws Exception {
        Person person = new PersonBuilder().build();
        // Ensure starting state is not captain
        person.stripCaptain();
        Name name = person.getName();

        ModelStub modelStub = new ModelStub() {
            @Override
            public Person getPersonByName(Name queryName) {
                if (!queryName.equals(name)) {
                    throw new seedu.address.model.person.exceptions.PersonNotFoundException();
                }
                return person;
            }
        };

        MakeCaptainCommand command = new MakeCaptainCommand(name);
        CommandResult result = command.execute(modelStub);

        assertTrue(person.isCaptain());
        String expectedSuccess = String.format(MakeCaptainCommand.MESSAGE_SUCCESS,
                person.getName(), person.getTeam().getName());
        assertEquals(CommandResult.showPersonCommandResult(expectedSuccess), result);
    }

    @Test
    public void execute_personDoesNotExist_throwsCommandException() {
        Name name = new Name("Non Existent");
        ModelStub modelStub = new ModelStub() {
            @Override
            public Person getPersonByName(Name queryName) {
                throw new seedu.address.model.person.exceptions.PersonNotFoundException();
            }
        };

        MakeCaptainCommand command = new MakeCaptainCommand(name);
        try {
            command.execute(modelStub);
        } catch (CommandException e) {
            assertEquals(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, name), e.getMessage());
            return;
        } catch (Exception e) {
            throw new AssertionError("Expected CommandException for missing person.");
        }
        throw new AssertionError("Expected CommandException for missing person.");
    }

    @Test
    public void execute_personAlreadyCaptain_throwsCommandException() {
        Person person = new PersonBuilder().withCaptain(true).build();
        Name name = person.getName();

        ModelStub modelStub = new ModelStub() {
            @Override
            public Person getPersonByName(Name queryName) {
                if (!queryName.equals(name)) {
                    throw new seedu.address.model.person.exceptions.PersonNotFoundException();
                }
                // ModelStub.makeCaptain will throw AlreadyCaptainException when execute() calls
                // model.makeCaptain
                return person;
            }
        };

        MakeCaptainCommand command = new MakeCaptainCommand(name);

        try {
            command.execute(modelStub);
        } catch (CommandException e) {
            assertEquals(MakeCaptainCommand.MESSAGE_ALREADY_CAPTAIN, e.getMessage());
            return;
        } catch (Exception e) {
            throw new AssertionError("Expected CommandException for already captain.");
        }
        throw new AssertionError("Expected CommandException for already captain.");
    }

    @Test
    public void equals() {
        Name sergio = new Name("Sergio Ramos");
        Name leo = new Name("Lionel Messi");
        MakeCaptainCommand makeSergio = new MakeCaptainCommand(sergio);
        MakeCaptainCommand makeSergioCopy = new MakeCaptainCommand(sergio);
        MakeCaptainCommand makeLeo = new MakeCaptainCommand(leo);

        // same object -> true
        assertTrue(makeSergio.equals(makeSergio));
        // same values -> true
        assertTrue(makeSergio.equals(makeSergioCopy));
        // null -> false
        assertFalse(makeSergio.equals(null));
        // different type -> false
        assertFalse(makeSergio.equals(1));
        // different name -> false
        assertFalse(makeSergio.equals(makeLeo));
    }
}
