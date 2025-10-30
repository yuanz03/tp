package seedu.address.model.team;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s team name matches the given team name (case-insensitive).
 */
public class FilterByTeamPredicate implements Predicate<Person> {
    public static final FilterByTeamPredicate ALWAYS_TRUE = new FilterByTeamPredicate("");
    private final String teamName;
    private final List<String> keywords;

    /**
     * Constructs a FilterByTeamPredicate to filter persons by team name.
     */
    public FilterByTeamPredicate(String teamName) {
        requireNonNull(teamName);
        this.teamName = teamName;
        this.keywords = Arrays.asList(teamName.split("\\s+"));
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        if (teamName.isEmpty()) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(person.getTeam().getName(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterByTeamPredicate)) {
            return false;
        }

        FilterByTeamPredicate otherFilterByTeamPredicate = (FilterByTeamPredicate) other;
        return teamName.equals(otherFilterByTeamPredicate.teamName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("team name", teamName).toString();
    }

    public String getTeamName() {
        return teamName;
    }

    /**
     * Tests if a {@code Team} matches the given team name (case-insensitive).
     * This is used for validation purposes.
     */
    public boolean testTeam(Team team) {
        requireNonNull(team);

        if (teamName.isEmpty()) {
            return true;
        }

        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(team.getName(), keyword));
    }
}
