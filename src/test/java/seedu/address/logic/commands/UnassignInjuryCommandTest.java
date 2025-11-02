package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

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

public class UnassignInjuryCommandTest {

    // Common injury constant used across tests
    private static final Injury ACL = new Injury("ACL");
    private static final Injury MCL = new Injury("MCL");

    //=========== Constructor null-check tests ========================================================
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignInjuryCommand(null, ACL));
    }

    @Test
    public void constructor_nullInjury_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignInjuryCommand(new Name("Messi"), null));
    }

    //=========== Execution exception thrown tests ========================================================
    @Test
    public void execute_personNotFound_throwsCommandException() {
        Model model = new ModelManager();
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(ALICE.getName(), ACL);

        String expectedMessage = formatPlayerMessage(Messages.MESSAGE_PERSON_NOT_FOUND, ALICE.getName());
        assertThrows(CommandException.class, expectedMessage, () -> unassignInjuryCommand.execute(model));
    }

    @Test
    public void execute_personWithNoInjuries_throwsCommandException() {
        Person personWithDefaultInjury = new PersonBuilder().build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personWithDefaultInjury);
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(personWithDefaultInjury.getName(),
                Injury.DEFAULT_INJURY_STATUS);

        String expectedMessage = formatPlayerMessage(Messages.MESSAGE_INJURY_ALREADY_UNASSIGNED,
                personWithDefaultInjury.getName());
        assertThrows(CommandException.class, expectedMessage, () -> unassignInjuryCommand.execute(modelStub));
    }

    @Test
    public void execute_injuryNotFound_throwsCommandException() {
        Person personWithInjury = new PersonBuilder().withName("Musiala").withInjuries("ACL").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personWithInjury);
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(personWithInjury.getName(), MCL);

        String expectedMessage = formatPlayerInjuryMessage(Messages.MESSAGE_INJURY_NOT_FOUND,
                personWithInjury.getName(), MCL);
        assertThrows(CommandException.class, expectedMessage, () -> unassignInjuryCommand.execute(modelStub));
    }

    //=========== Execution successful tests ========================================================
    @Test
    public void execute_validPersonWithInjury_unassignSuccessful() throws Exception {
        Person personWithInjury = new PersonBuilder().withName("Musiala").withInjuries("ACL").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personWithInjury);
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(personWithInjury.getName(), ACL);

        String expectedMessage = formatPlayerInjuryMessage(Messages.MESSAGE_UNASSIGN_INJURY_SUCCESS,
                personWithInjury.getName(), personWithInjury.getInjuries().iterator().next());
        assertEquals(expectedMessage, unassignInjuryCommand.execute(modelStub).getFeedbackToUser());

        assertEquals(ACL, modelStub.injuryUnassigned);
        assertTrue(modelStub.personUpdated.getInjuries().contains(Injury.DEFAULT_INJURY_STATUS));
        assertFalse(modelStub.personUpdated.getInjuries().contains(ACL));
    }

    @Test
    public void execute_personWithMultipleInjuries_unassignsSpecificInjury() throws Exception {
        Person personWithMultipleInjuries = new PersonBuilder().withName("Musiala").withInjuries("ACL", "MCL").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personWithMultipleInjuries);
        UnassignInjuryCommand unassignInjuryCommand = new UnassignInjuryCommand(personWithMultipleInjuries.getName(),
                MCL);

        String expectedMessage = formatPlayerInjuryMessage(Messages.MESSAGE_UNASSIGN_INJURY_SUCCESS,
                personWithMultipleInjuries.getName(), personWithMultipleInjuries.getInjuries().iterator().next());
        assertEquals(expectedMessage, unassignInjuryCommand.execute(modelStub).getFeedbackToUser());
    }

    //=========== equals() and toString() ========================================================
    @Test
    public void equals() {
        UnassignInjuryCommand unassignAliceInjuryCommand = new UnassignInjuryCommand(ALICE.getName(), ACL);

        // same object -> returns true
        assertTrue(unassignAliceInjuryCommand.equals(unassignAliceInjuryCommand));

        // same values -> returns true
        UnassignInjuryCommand unassignAliceInjuryCommandCopy = new UnassignInjuryCommand(ALICE.getName(), ACL);
        assertTrue(unassignAliceInjuryCommand.equals(unassignAliceInjuryCommandCopy));

        // different person -> returns false
        UnassignInjuryCommand unassignBensonInjuryCommand = new UnassignInjuryCommand(BENSON.getName(), ACL);
        assertFalse(unassignAliceInjuryCommand.equals(unassignBensonInjuryCommand));

        // different injuries -> returns false
        UnassignInjuryCommand unassignAliceInjuryCommandDifferent = new UnassignInjuryCommand(ALICE.getName(), MCL);
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
        String expected = UnassignInjuryCommand.class.getCanonicalName() + "{personNameToUnassign=" + ALICE.getName()
                + ", injuryToUnassign=" + ALICE.getInjuries().iterator().next().getInjuryName() + "}";
        assertEquals(expected, unassignInjuryCommand.toString());
    }

    /**
     * A Model stub that contains a single person.
     */
    private static class ModelStubWithPerson extends ModelStub {
        private final Person person;
        private Person personUpdated = null;
        private Injury injuryUnassigned = Injury.DEFAULT_INJURY_STATUS;

        ModelStubWithPerson(Person person) {
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
        public Person deleteInjury(Person target, Injury injury) {
            requireAllNonNull(target, injury);

            Set<Injury> updatedInjuries = new HashSet<>(target.getInjuries());
            updatedInjuries.remove(injury);

            // Ensure that the person has at least the default injury status
            if (updatedInjuries.isEmpty()) {
                updatedInjuries.add(Injury.DEFAULT_INJURY_STATUS);
            }

            Person updatedPerson = target.withInjuries(updatedInjuries);

            this.personUpdated = updatedPerson;
            this.injuryUnassigned = injury;
            return updatedPerson;
        }

        @Override
        public boolean hasNonDefaultInjury(Person target) {
            requireNonNull(target);
            return target.getInjuries().stream().anyMatch(injury -> !injury.equals(Injury.DEFAULT_INJURY_STATUS));
        }

        @Override
        public boolean hasSpecificInjury(Person target, Injury injury) {
            requireAllNonNull(target, injury);
            return target.getInjuries().contains(injury);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    //=========== Helper Methods ========================================================
    private static String formatPlayerMessage(String expectedMessage, Name name) {
        return String.format(expectedMessage, name);
    }

    private static String formatPlayerInjuryMessage(String expectedMessage, Name name, Injury injury) {
        return String.format(expectedMessage, name, injury.getInjuryName());
    }
}
