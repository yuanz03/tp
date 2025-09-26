package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

public class TeamBuilder {

    public static final String DEFAULT_TEAM_NAME = "TeamA";

    private Name name;
    private Set<Person> persons;

    /**
     * Creates a {@code TeamBuilder} with the default details.
     */
    public TeamBuilder() {
        this.name = new Name(DEFAULT_TEAM_NAME);
        this.persons = new HashSet<>();
    }

    /**
     * Initializes the TeamBuilder with the data of {@code teamToCopy}.
     */
    public TeamBuilder(Team teamToCopy) {
        this.name = teamToCopy.getName();
        this.persons = new HashSet<>(teamToCopy.getMembers());
    }

    /**
     * Sets the {@code Name} of the {@code Team} that we are building.
     */
    public TeamBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public Team build() {
        return new Team(name, persons);
    }
}
