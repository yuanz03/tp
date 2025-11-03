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
 * Contains unit tests for {@code UnassignCaptainCommand}.
 */
public class UnassignCaptainCommandTest {

    @Test
    public void execute_personExistsAndIsCaptain_stripsCaptaincy() throws Exception {
        Person person = new PersonBuilder().withCaptain(true).build();
        Name name = person.getName();

        ModelStub modelStub = new ModelStub() {
            private boolean unassignCaptainCalled = false;

            @Override
            public Person getPersonByName(Name queryName) {
                if (!queryName.equals(name)) {
                    throw new seedu.address.model.person.exceptions.PersonNotFoundException();
                }
                return person;
            }

            @Override
            public void unassignCaptain(Person person) {
                unassignCaptainCalled = true;
            }
        };

        UnassignCaptainCommand command = new UnassignCaptainCommand(name);
        CommandResult result = command.execute(modelStub);

        assertEquals(CommandResult.showPersonCommandResult(String.format(UnassignCaptainCommand.MESSAGE_SUCCESS,
                person.getName())), result);
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

        UnassignCaptainCommand command = new UnassignCaptainCommand(name);
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
    public void execute_personNotCaptain_throwsCommandException() {
        Person person = new PersonBuilder().withCaptain(false).build();
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

        UnassignCaptainCommand command = new UnassignCaptainCommand(name);
        try {
            command.execute(modelStub);
        } catch (CommandException e) {
            String expected = String.format(UnassignCaptainCommand.MESSAGE_NOT_CAPTAIN, person.getName());
            assertEquals(expected, e.getMessage());
            return;
        } catch (Exception e) {
            throw new AssertionError("Expected CommandException for person not captain.");
        }
        throw new AssertionError("Expected CommandException for person not captain.");
    }

    @Test
    public void execute_personNotInTeam_throwsCommandException() {
        Person base = new PersonBuilder().withCaptain(true).build();
        Person noTeam = new PersonWithNullTeam(base);

        Name name = base.getName();

        ModelStub modelStub = new ModelStub() {
            @Override
            public Person getPersonByName(Name queryName) {
                if (!queryName.equals(name)) {
                    throw new seedu.address.model.person.exceptions.PersonNotFoundException();
                }
                return noTeam;
            }
        };

        UnassignCaptainCommand command = new UnassignCaptainCommand(name);
        try {
            command.execute(modelStub);
        } catch (CommandException e) {
            String expected = String.format(UnassignCaptainCommand.MESSAGE_NOT_IN_TEAM, base.getName());
            assertEquals(expected, e.getMessage());
            return;
        } catch (Exception e) {
            throw new AssertionError("Expected CommandException for person not in team.");
        }
        throw new AssertionError("Expected CommandException for person not in team.");
    }

    private static class PersonWithNullTeam extends Person {
        PersonWithNullTeam(Person p) {
            super(p.getName(), p.getPhone(), p.getEmail(), p.getAddress(),
                    p.getTeam(), p.getTags(), p.getPosition(), p.getInjuries(), p.isCaptain());
        }

        @Override
        public seedu.address.model.team.Team getTeam() {
            return null;
        }
    }

    @Test
    public void equals() {
        Name sergio = new Name("Sergio Ramos");
        Name leo = new Name("Lionel Messi");
        UnassignCaptainCommand stripSergio = new UnassignCaptainCommand(sergio);
        UnassignCaptainCommand stripSergioCopy = new UnassignCaptainCommand(sergio);
        UnassignCaptainCommand stripLeo = new UnassignCaptainCommand(leo);

        // same object -> true
        assertTrue(stripSergio.equals(stripSergio));
        // same values -> true
        assertTrue(stripSergio.equals(stripSergioCopy));
        // null -> false
        assertFalse(stripSergio.equals(null));
        // different type -> false
        assertFalse(stripSergio.equals(1));
        // different name -> false
        assertFalse(stripSergio.equals(stripLeo));
    }
}
