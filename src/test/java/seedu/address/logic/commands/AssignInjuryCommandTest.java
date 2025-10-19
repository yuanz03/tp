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
        Person validPerson = new PersonBuilder().withName("Musiala").withInjuries("ACL").build();
        Name name = validPerson.getName();
        Injury duplicateInjury = new Injury("ACL");

        ModelStubAcceptingInjuryAssigned modelStub = new ModelStubAcceptingInjuryAssigned(validPerson);

        assertThrows(CommandException.class, String.format(AssignInjuryCommand.MESSAGE_ASSIGNED_SAME_INJURY,
                validPerson.getName(), validPerson.getInjuries()), () ->
                new AssignInjuryCommand(name, duplicateInjury).execute(modelStub));
    }

    @Test
    public void execute_personFound_assignSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().withName("Musiala").build();
        Name name = validPerson.getName();
        Injury newInjury = new Injury("Broken Foot");

        ModelStubAcceptingInjuryAssigned modelStub = new ModelStubAcceptingInjuryAssigned(validPerson);

        String expectedInjury = "[" + newInjury + "]";
        assertEquals(String.format(AssignInjuryCommand.MESSAGE_ASSIGN_INJURY_SUCCESS,
                validPerson.getName(), expectedInjury),
                new AssignInjuryCommand(name, newInjury).execute(modelStub).getFeedbackToUser());

        assertEquals(newInjury, modelStub.injuryAssigned);
        assertTrue(modelStub.personUpdated.getInjuries().contains(newInjury));
        assertFalse(modelStub.personUpdated.getInjuries().contains(Person.DEFAULT_INJURY_STATUS));
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
        AssignInjuryCommand assignInjuryCommand = new AssignInjuryCommand(ALICE.getName(),
                ALICE.getInjuries().iterator().next());
        String expected = AssignInjuryCommand.class.getCanonicalName() + "{personToAssign=" + ALICE.getName() + ", "
                + "injuryToAssign=" + ALICE.getInjuries().iterator().next() + "}";
        assertEquals(expected, assignInjuryCommand.toString());
    }

    /**
     * A Model stub that always assigns the specified injury to the targeted person.
     */
    private static class ModelStubAcceptingInjuryAssigned extends ModelStub {
        private final Person person;
        private Person personUpdated = null;
        private Injury injuryAssigned = Person.DEFAULT_INJURY_STATUS;

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
        public void addInjury(Person target, Injury injury) {
            requireAllNonNull(target, injury);

            Set<Injury> updatedInjuries = new HashSet<>(target.getInjuries());

            // Remove FIT status when assigning any other injury
            if (updatedInjuries.contains(Person.DEFAULT_INJURY_STATUS)) {
                updatedInjuries.remove(Person.DEFAULT_INJURY_STATUS);
            }
            updatedInjuries.add(injury);

            Person updatedPerson = new Person(target.getName(), target.getPhone(), target.getEmail(),
                    target.getAddress(), target.getTeam(), target.getTags(), target.getPosition(),
                    updatedInjuries, target.isCaptain());

            this.personUpdated = updatedPerson;
            this.injuryAssigned = injury;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
