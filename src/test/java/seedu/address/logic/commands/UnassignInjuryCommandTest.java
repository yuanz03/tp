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
        assertThrows(NullPointerException.class, () -> new UnassignInjuryCommand(null));
    }

    @Test
    public void execute_validPersonWithInjury_unassignSuccessful() throws Exception {
        ModelStubWithPerson modelStub = new ModelStubWithPerson(ALICE);
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(ALICE.getName());

        assertEquals(String.format(UnassignInjuryCommand.MESSAGE_UNASSIGN_INJURY_SUCCESS, ALICE.getName()),
                unassignInjuryCommand.execute(modelStub).getFeedbackToUser());
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Model model = new ModelManager();
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(ALICE.getName());

        assertThrows(CommandException.class, String.format(Messages.MESSAGE_PERSON_NOT_FOUND, ALICE.getName()), () ->
                unassignInjuryCommand.execute(model));
    }

    @Test
    public void execute_personWithDefaultInjury_throwsCommandException() {
        Person person = new PersonBuilder().withInjury(Person.DEFAULT_INJURY_STATUS.getInjuryName()).build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(person);
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(person.getName());

        assertThrows(CommandException.class,
                String.format(UnassignInjuryCommand.MESSAGE_INJURY_ALREADY_UNASSIGNED, person.getName()), () ->
                        unassignInjuryCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        UnassignInjuryCommand unassignAliceInjuryCommand = new UnassignInjuryCommand(ALICE.getName());
        UnassignInjuryCommand unassignBensonInjuryCommand = new UnassignInjuryCommand(BENSON.getName());

        // same object -> returns true
        assertTrue(unassignAliceInjuryCommand.equals(unassignAliceInjuryCommand));

        // same values -> returns true
        UnassignInjuryCommand unassignAliceInjuryCommandCopy = new UnassignInjuryCommand(ALICE.getName());
        assertTrue(unassignAliceInjuryCommand.equals(unassignAliceInjuryCommandCopy));

        // different person -> returns false
        assertFalse(unassignAliceInjuryCommand.equals(unassignBensonInjuryCommand));

        // different types -> returns false
        assertFalse(unassignAliceInjuryCommand.equals(1));

        // null -> returns false
        assertFalse(unassignAliceInjuryCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(ALICE.getName());
        String expected = UnassignInjuryCommand.class.getCanonicalName() + "{personToUnassign=" + ALICE.getName() + "}";
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
        public void updatePersonInjuryStatus(Person target, Injury injury) {
            requireAllNonNull(target, injury);
            // Simulate update
        }

        @Override
        public boolean hasInjury(Person target) {
            requireNonNull(target);
            return !target.getInjury().getInjuryName().equalsIgnoreCase(Person.DEFAULT_INJURY_STATUS.getInjuryName());
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
