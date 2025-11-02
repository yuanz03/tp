package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.HashSet;
import java.util.Set;

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

public class AssignInjuryCommandTest {

    // Common injury constants used across tests
    private static final Injury ACL = new Injury("ACL");
    private static final Injury MCL = new Injury("MCL");

    //=========== Constructor null-check tests ========================================================

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignInjuryCommand(null, ACL));
    }

    @Test
    public void constructor_nullInjury_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignInjuryCommand(new Name("Messi"), null));
    }

    //=========== Execution exception thrown tests ========================================================

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Model model = new ModelManager();
        Name nonExistentName = new Name("Missing");

        assertThrows(CommandException.class, String.format(Messages.MESSAGE_PERSON_NOT_FOUND, nonExistentName), () ->
                new AssignInjuryCommand(nonExistentName, ACL).execute(model));
    }

    @Test
    public void execute_duplicateInjuryAssignment_throwsCommandException() {
        Person validPerson = new PersonBuilder().withName("Musiala").withInjuries("ACL").build();
        Name name = validPerson.getName();

        ModelStubAcceptingInjuryAssigned modelStub = new ModelStubAcceptingInjuryAssigned(validPerson);

        assertThrows(CommandException.class, String.format(Messages.MESSAGE_ASSIGNED_SAME_INJURY,
                validPerson.getName(), ACL.getInjuryName()), () ->
                new AssignInjuryCommand(name, ACL).execute(modelStub));
    }

    @Test
    public void execute_defaultInjuryAssignment_throwsCommandException() {
        Person validPerson = new PersonBuilder().withName("Musiala").withInjuries("ACL").build();
        Name name = validPerson.getName();

        ModelStubAcceptingInjuryAssigned modelStub = new ModelStubAcceptingInjuryAssigned(validPerson);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_INJURY_ASSIGNMENT, () ->
                new AssignInjuryCommand(name, Injury.DEFAULT_INJURY_STATUS).execute(modelStub));
    }

    //=========== Execution successful test ========================================================

    @Test
    public void execute_personFound_assignSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().withName("Musiala").build();
        Name name = validPerson.getName();

        ModelStubAcceptingInjuryAssigned modelStub = new ModelStubAcceptingInjuryAssigned(validPerson);

        String expectedInjury = "[" + MCL.getInjuryName() + "]";
        assertEquals(String.format(Messages.MESSAGE_ASSIGN_INJURY_SUCCESS,
                validPerson.getName(), expectedInjury),
                new AssignInjuryCommand(name, MCL).execute(modelStub).getFeedbackToUser());

        assertEquals(MCL, modelStub.injuryAssigned);
        assertTrue(modelStub.personUpdated.getInjuries().contains(MCL));
        assertFalse(modelStub.personUpdated.getInjuries().contains(Injury.DEFAULT_INJURY_STATUS));
    }

    //=========== equals() and toString() ========================================================

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();

        AssignInjuryCommand assignAliceInjuryCommand = new AssignInjuryCommand(alice.getName(), ACL);
        AssignInjuryCommand assignBobInjuryCommand = new AssignInjuryCommand(bob.getName(), ACL);

        // same object -> returns true
        assertTrue(assignAliceInjuryCommand.equals(assignAliceInjuryCommand));

        // same values -> returns true
        AssignInjuryCommand assignAliceInjuryCommandCopy = new AssignInjuryCommand(alice.getName(), ACL);
        assertTrue(assignAliceInjuryCommand.equals(assignAliceInjuryCommandCopy));

        // different injuries -> returns false
        AssignInjuryCommand assignAliceInjuryCommandDifferent = new AssignInjuryCommand(alice.getName(), MCL);
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
        AssignInjuryCommand assignInjuryCommand = new AssignInjuryCommand(ALICE.getName(),
                ALICE.getInjuries().iterator().next());
        String expected = AssignInjuryCommand.class.getCanonicalName() + "{personNameToAssign=" + ALICE.getName() + ", "
                + "injuryToAssign=" + ALICE.getInjuries().iterator().next().getInjuryName() + "}";
        assertEquals(expected, assignInjuryCommand.toString());
    }

    /**
     * A Model stub that always assigns the specified injury to the targeted person.
     */
    private static class ModelStubAcceptingInjuryAssigned extends ModelStub {
        private final Person person;
        private Person personUpdated = null;
        private Injury injuryAssigned = Injury.DEFAULT_INJURY_STATUS;

        ModelStubAcceptingInjuryAssigned(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public Person getPersonByName(Name name) {
            requireNonNull(name);
            if (personUpdated != null && personUpdated.getName().equals(name)) {
                return this.personUpdated;
            }

            if (person.getName().equals(name)) {
                return this.person;
            }
            throw new PersonNotFoundException();
        }

        @Override
        public Person addInjury(Person target, Injury injury) {
            requireAllNonNull(target, injury);

            Set<Injury> updatedInjuries = new HashSet<>(target.getInjuries());

            // Remove FIT status when assigning any other injury
            if (updatedInjuries.contains(Injury.DEFAULT_INJURY_STATUS)) {
                updatedInjuries.remove(Injury.DEFAULT_INJURY_STATUS);
            }
            updatedInjuries.add(injury);

            Person updatedPerson = target.withInjuries(updatedInjuries);

            this.personUpdated = updatedPerson;
            this.injuryAssigned = injury;
            return updatedPerson;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
