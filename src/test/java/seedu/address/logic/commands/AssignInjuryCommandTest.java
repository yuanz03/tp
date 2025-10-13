package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

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
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;

public class AssignInjuryCommandTest {
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AssignInjuryCommand(null, new Injury("ACL")));
    }

    @Test
    public void constructor_nullInjury_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AssignInjuryCommand(new Name("Messi"), null));
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Model model = new ModelManager();
        Name nonExistentName = new Name("Missing");
        Injury injury = new Injury("ACL");

        assertThrows(CommandException.class, String.format(Messages.MESSAGE_PERSON_NOT_FOUND, nonExistentName), () ->
                new AssignInjuryCommand(nonExistentName, injury).execute(model));
    }

    @Test
    public void execute_duplicateInjuryAssignment_throwsCommandException() {
        Person validPerson = new PersonBuilder().withName("Musiala").withInjury("ACL").build();
        Name name = validPerson.getName();
        Injury duplicateInjury = new Injury("ACL");

        ModelStubAcceptingInjuryAssigned modelStub = new ModelStubAcceptingInjuryAssigned(validPerson);

        assertThrows(CommandException.class,
                String.format(AssignInjuryCommand.MESSAGE_ASSIGNED_SAME_INJURY, name, duplicateInjury), () ->
                        new AssignInjuryCommand(name, duplicateInjury).execute(modelStub));
    }

    @Test
    public void execute_personFound_assignSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().withName("Musiala").withInjury("ACL").build();
        Name name = validPerson.getName();
        Injury newInjury = new Injury("Broken Foot");

        ModelStubAcceptingInjuryAssigned modelStub = new ModelStubAcceptingInjuryAssigned(validPerson);

        assertEquals(String.format(AssignInjuryCommand.MESSAGE_ASSIGN_INJURY_SUCCESS, name, newInjury),
                new AssignInjuryCommand(name, newInjury).execute(modelStub).getFeedbackToUser());

        assertEquals(validPerson, modelStub.personUpdated);
        assertEquals(newInjury, modelStub.injuryAssigned);
    }

    @Test
    public void execute_lowercaseDefaultInjury_assignsDefaultInjurySuccessful() throws Exception {
        Person validPerson = new PersonBuilder().withName("Musiala").withInjury("ACL").build();
        Name name = validPerson.getName();
        Injury lowercaseFitInjuryStatus = new Injury("fit");

        ModelStubAcceptingInjuryAssigned modelStub = new ModelStubAcceptingInjuryAssigned(validPerson);

        // Execute command with lowercase "fit", which should assign the default injury status
        assertEquals(String.format(AssignInjuryCommand.MESSAGE_ASSIGN_INJURY_SUCCESS, name, lowercaseFitInjuryStatus),
                new AssignInjuryCommand(name, lowercaseFitInjuryStatus).execute(modelStub).getFeedbackToUser());

        // Verify that the default injury status was assigned and not the lowercase "fit"
        assertEquals(validPerson, modelStub.personUpdated);
        assertEquals(new Injury(Person.DEFAULT_INJURY_STATUS), modelStub.injuryAssigned);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();

        AssignInjuryCommand assignAliceInjuryCommand = new AssignInjuryCommand(alice.getName(), new Injury("ACL"));
        AssignInjuryCommand assignBobInjuryCommand = new AssignInjuryCommand(bob.getName(), new Injury("Dead leg"));

        // same object -> returns true
        assertTrue(assignAliceInjuryCommand.equals(assignAliceInjuryCommand));

        // same values -> returns true
        AssignInjuryCommand assignAliceInjuryCommandCopy =
                new AssignInjuryCommand(alice.getName(), new Injury("ACL"));
        assertTrue(assignAliceInjuryCommand.equals(assignAliceInjuryCommandCopy));

        // different injuries -> returns false
        AssignInjuryCommand assignAliceInjuryCommandDifferent =
                new AssignInjuryCommand(alice.getName(), new Injury("Concussion"));
        assertFalse(assignAliceInjuryCommand.equals(assignAliceInjuryCommandDifferent));

        // different person -> returns false
        assertFalse(assignAliceInjuryCommand.equals(assignBobInjuryCommand));

        // different types -> returns false
        assertFalse(assignAliceInjuryCommand.equals(1));

        // null -> returns false
        assertFalse(assignAliceInjuryCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        AssignInjuryCommand assignInjuryCommand = new AssignInjuryCommand(ALICE.getName(), ALICE.getInjury());
        String expected = AssignInjuryCommand.class.getCanonicalName() + "{personToAssign=" + ALICE.getName() + ", "
                + "injuryToAssign=" + ALICE.getInjury() + "}";
        assertEquals(expected, assignInjuryCommand.toString());
    }

    /**
     * A Model stub that always assigns the specified injury to the targeted person.
     */
    private static class ModelStubAcceptingInjuryAssigned extends ModelStub {
        private final Person person;
        private Person personUpdated = null;
        private Injury injuryAssigned = new Injury(Person.DEFAULT_INJURY_STATUS);

        ModelStubAcceptingInjuryAssigned(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public Person getPersonByName(Name name) {
            requireNonNull(name);
            return this.person;
        }

        @Override
        public void updatePersonInjuryStatus(Person target, Injury injury) {
            requireAllNonNull(target, injury);
            this.personUpdated = target;
            this.injuryAssigned = injury;
        }

        @Override
        public boolean isDuplicateInjuryAssigned(Person target, Injury injury) {
            requireAllNonNull(target, injury);
            return target.getInjury().equals(injury);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
