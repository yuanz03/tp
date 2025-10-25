package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalTeams.U12;
import static seedu.address.testutil.TypicalTeams.U16;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.team.Team;
import seedu.address.model.team.exceptions.TeamNotFoundException;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TeamBuilder;

public class AssignTeamCommandTest {

    @Test
    public void constructor_nullPlayerName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignTeamCommand(null, U12));
    }

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignTeamCommand(new Name("Alice"), null));
    }

    @Test
    public void execute_assignTeamSuccessfully_success() throws Exception {
        Model model = new ModelManager();
        model.addTeam(U16);

        // ALICE is already in U12, reassign to U16
        Person alice = new PersonBuilder(ALICE).build();
        model.addPerson(alice);

        AssignTeamCommand assignTeamCommand = new AssignTeamCommand(ALICE.getName(), U16);
        CommandResult commandResult = assignTeamCommand.execute(model);

        assertEquals(String.format(AssignTeamCommand.MESSAGE_SUCCESS, ALICE.getName(), U16.getName()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_playerNotFound_throwsCommandException() {
        Model model = new ModelManager();
        model.addTeam(U12);

        Name nonExistentName = new Name("NonExistentPlayer");
        AssignTeamCommand assignTeamCommand = new AssignTeamCommand(nonExistentName, U12);

        assertThrows(CommandException.class,
                String.format(AssignTeamCommand.MESSAGE_PLAYER_NOT_FOUND, nonExistentName), () ->
                        assignTeamCommand.execute(model));
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() {
        Model model = new ModelManager();
        model.addPerson(ALICE);

        Team nonExistentTeam = new TeamBuilder().withName("NonExistentTeam").build();
        AssignTeamCommand assignTeamCommand = new AssignTeamCommand(ALICE.getName(), nonExistentTeam);

        assertThrows(CommandException.class,
                String.format(AssignTeamCommand.MESSAGE_TEAM_NOT_FOUND, nonExistentTeam.getName()), () ->
                        assignTeamCommand.execute(model));
    }

    @Test
    public void execute_playerAlreadyAssignedToTeam_throwsCommandException() {
        Model model = new ModelManager();
        model.addTeam(U12);

        // ALICE is already in U12 by default
        model.addPerson(ALICE);

        AssignTeamCommand assignTeamCommand = new AssignTeamCommand(ALICE.getName(), U12);

        assertThrows(CommandException.class,
                String.format(AssignTeamCommand.MESSAGE_ALREADY_ASSIGNED, ALICE.getName(), U12.getName()), () ->
                        assignTeamCommand.execute(model));
    }

    @Test
    public void execute_assignTeamWithModelStub_success() throws Exception {
        // Use ALICE (in U12) and assign to U16
        Person alice = new PersonBuilder(ALICE).build();
        ModelStubWithPersonAndTeam modelStub = new ModelStubWithPersonAndTeam(alice, U16);

        AssignTeamCommand assignTeamCommand = new AssignTeamCommand(ALICE.getName(), U16);
        CommandResult commandResult = assignTeamCommand.execute(modelStub);

        assertEquals(String.format(AssignTeamCommand.MESSAGE_SUCCESS, ALICE.getName(), U16.getName()),
                commandResult.getFeedbackToUser());
        assertTrue(modelStub.assignTeamCalled);
    }

    @Test
    public void execute_assignTeamCaseInsensitive_usesCanonicalTeamName() throws Exception {
        // Test that case-insensitive team matching uses the canonical team name from address book
        Model model = new ModelManager();
        Team canonicalTeam = new TeamBuilder().withName("U16").build(); // Canonical name with uppercase
        model.addTeam(canonicalTeam);

        Person alice = new PersonBuilder(ALICE).build();
        model.addPerson(alice);

        // User inputs lowercase "u16" but should get assigned to canonical "U16"
        Team lowercaseTeam = new TeamBuilder().withName("u16").build();
        AssignTeamCommand assignTeamCommand = new AssignTeamCommand(ALICE.getName(), lowercaseTeam);
        CommandResult commandResult = assignTeamCommand.execute(model);

        // Verify the success message uses the canonical team name "U16", not "u16"
        assertEquals(String.format(AssignTeamCommand.MESSAGE_SUCCESS, ALICE.getName(), "U16"),
                commandResult.getFeedbackToUser());

        // Verify the person's team name matches the canonical name
        Person updatedAlice = model.getPersonByName(ALICE.getName());
        assertEquals("U16", updatedAlice.getTeam().getName());
    }

    @Test
    public void equals() {
        AssignTeamCommand assignAliceU12 = new AssignTeamCommand(ALICE.getName(), U12);
        AssignTeamCommand assignAliceU16 = new AssignTeamCommand(ALICE.getName(), U16);
        AssignTeamCommand assignBobU12 = new AssignTeamCommand(BOB.getName(), U12);

        // same object -> returns true
        assertTrue(assignAliceU12.equals(assignAliceU12));

        // same values -> returns true
        AssignTeamCommand assignAliceU12Copy = new AssignTeamCommand(ALICE.getName(), U12);
        assertTrue(assignAliceU12.equals(assignAliceU12Copy));

        // different types -> returns false
        assertFalse(assignAliceU12.equals(1));

        // null -> returns false
        assertFalse(assignAliceU12.equals(null));

        // different player -> returns false
        assertFalse(assignAliceU12.equals(assignBobU12));

        // different team -> returns false
        assertFalse(assignAliceU12.equals(assignAliceU16));
    }

    @Test
    public void toStringTest() {
        AssignTeamCommand assignTeamCommand = new AssignTeamCommand(ALICE.getName(), U12);
        String expected = AssignTeamCommand.class.getCanonicalName()
                + "{team=" + U12 + ", player=" + ALICE.getName() + "}";
        assertEquals(expected, assignTeamCommand.toString());
    }

    /**
     * A Model stub that contains a person and a team for testing.
     */
    private class ModelStubWithPersonAndTeam extends ModelStub {
        private final Person person;
        private final Team team;
        private boolean assignTeamCalled = false;

        ModelStubWithPersonAndTeam(Person person, Team team) {
            requireNonNull(person);
            requireNonNull(team);
            this.person = person;
            this.team = team;
        }

        @Override
        public Person getPersonByName(Name name) {
            requireNonNull(name);
            if (person.getName().equals(name)) {
                return person;
            }
            throw new PersonNotFoundException();
        }

        @Override
        public boolean hasTeam(Team team) {
            requireNonNull(team);
            return this.team.isSameTeam(team);
        }

        @Override
        public Team getTeamByName(Team team) {
            requireNonNull(team);
            if (this.team.isSameTeam(team)) {
                return this.team;
            }
            throw new TeamNotFoundException();
        }

        @Override
        public void assignTeam(Person person, Team team) {
            requireNonNull(person);
            requireNonNull(team);
            this.assignTeamCalled = true;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
