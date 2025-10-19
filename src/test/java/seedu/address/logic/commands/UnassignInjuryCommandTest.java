package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;

public class UnassignInjuryCommandTest {
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignInjuryCommand(null, new Injury("ACL")));
    }

    @Test
    public void constructor_nullInjury_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignInjuryCommand(new Name("Messi"), null));
    }

    @Test
    public void execute_validPersonWithInjury_unassignSuccessful() throws Exception {
        Person personWithInjury = new PersonBuilder().withName("Musiala").withInjuries("ACL").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personWithInjury);
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(personWithInjury.getName(),
                new Injury("ACL"));

        assertEquals(String.format(UnassignInjuryCommand.MESSAGE_UNASSIGN_INJURY_SUCCESS, personWithInjury.getName()),
                unassignInjuryCommand.execute(modelStub).getFeedbackToUser());
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Model model = new ModelManager();
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(ALICE.getName(), new Injury("ACL"));

        assertThrows(CommandException.class, String.format(Messages.MESSAGE_PERSON_NOT_FOUND, ALICE.getName()), () ->
                unassignInjuryCommand.execute(model));
    }

    @Test
    public void execute_personWithNoInjuries_throwsCommandException() {
        Person personWithDefaultInjury = new PersonBuilder()
                .withInjuries(Person.DEFAULT_INJURY_STATUS.getInjuryName()).build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personWithDefaultInjury);
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(personWithDefaultInjury.getName(),
                new Injury("ACL"));

        assertThrows(CommandException.class,
                String.format(UnassignInjuryCommand.MESSAGE_INJURY_ALREADY_UNASSIGNED,
                        personWithDefaultInjury.getName()), () -> unassignInjuryCommand.execute(modelStub));
    }

    @Test
    public void execute_injuryNotFound_throwsCommandException() {
        Person personWithInjury = new PersonBuilder().withName("Musiala").withInjuries("ACL").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personWithInjury);
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(personWithInjury.getName(),
                new Injury("Concussion"));

        assertThrows(CommandException.class,
                String.format(UnassignInjuryCommand.MESSAGE_INJURY_NOT_FOUND, personWithInjury.getName(),
                        "Concussion"), () -> unassignInjuryCommand.execute(modelStub));
    }

    @Test
    public void execute_personWithMultipleInjuries_removesSpecificInjury() throws Exception {
        Person personWithMultipleInjuries = new PersonBuilder().withName("Musiala").withInjuries("ACL", "MCL").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personWithMultipleInjuries);
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(personWithMultipleInjuries.getName(),
                new Injury("MCL"));

        assertEquals(String.format(UnassignInjuryCommand.MESSAGE_UNASSIGN_INJURY_SUCCESS,
                personWithMultipleInjuries.getName()), unassignInjuryCommand.execute(modelStub).getFeedbackToUser());
    }

    @Test
    public void equals() {
        UnassignInjuryCommand unassignAliceInjuryCommand = new UnassignInjuryCommand(ALICE.getName(),
                new Injury("ACL"));

        // same object -> returns true
        assertTrue(unassignAliceInjuryCommand.equals(unassignAliceInjuryCommand));

        // same values -> returns true
        UnassignInjuryCommand unassignAliceInjuryCommandCopy = new UnassignInjuryCommand(ALICE.getName(),
                new Injury("ACL"));
        assertTrue(unassignAliceInjuryCommand.equals(unassignAliceInjuryCommandCopy));

        // different person -> returns false
        UnassignInjuryCommand unassignBensonInjuryCommand = new UnassignInjuryCommand(BENSON.getName(),
                new Injury("ACL"));
        assertFalse(unassignAliceInjuryCommand.equals(unassignBensonInjuryCommand));

        // different injuries -> returns false
        UnassignInjuryCommand unassignAliceInjuryCommandDifferent = new UnassignInjuryCommand(ALICE.getName(),
                new Injury("MCL"));
        assertFalse(unassignAliceInjuryCommand.equals(unassignAliceInjuryCommandDifferent));

        // different types -> returns false
        assertFalse(unassignAliceInjuryCommand.equals(1));

        // null -> returns false
        assertFalse(unassignAliceInjuryCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(ALICE.getName(),
                ALICE.getInjuries().iterator().next());
        String expected = UnassignInjuryCommand.class.getCanonicalName() + "{personToUnassign=" + ALICE.getName()
                + ", injuryToUnassign=" + ALICE.getInjuries().iterator().next() + "}";
        assertEquals(expected, unassignInjuryCommand.toString());
    }

    /**
     * A Model stub that contains a single person.
     */
    private static class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public Person getPersonByName(Name name) {
            requireNonNull(name);
            if (person.getName().equals(name)) {
                return this.person;
            }
            throw new PersonNotFoundException();
        }

        @Override
        public void deleteInjury(Person target, Injury injury) {
            requireAllNonNull(target, injury);
            // Simulate update
        }

        @Override
        public boolean hasInjury(Person target) {
            requireNonNull(target);
            return target.getInjuries().stream().anyMatch(injury -> !injury.equals(Person.DEFAULT_INJURY_STATUS));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
