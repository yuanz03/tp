package seedu.address.testutil;

import seedu.address.model.team.Team;

/**
 * A utility class to help with building Team objects.
 */
public class TeamBuilder {

    public static final String DEFAULT_TEAM_NAME = "TeamA";

    private String name;

    /**
     * Creates a {@code TeamBuilder} with the default details.
     */
    public TeamBuilder() {
        this.name = DEFAULT_TEAM_NAME;
    }

    /**
     * Initializes the TeamBuilder with the data of {@code teamToCopy}.
     */
    public TeamBuilder(Team teamToCopy) {
        this.name = teamToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Team} that we are building.
     */
    public TeamBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Team build() {
        return new Team(name);
    }
}
