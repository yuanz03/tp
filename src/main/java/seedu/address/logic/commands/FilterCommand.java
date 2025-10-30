package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.FilterByInjuryPredicate;
import seedu.address.model.position.FilterByPositionPredicate;
import seedu.address.model.position.Position;
import seedu.address.model.team.FilterByTeamPredicate;
import seedu.address.model.team.Team;

/**
 * Filters and lists all persons in address book whose team, position or injury matches the argument.
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter players by team, injury and/or position.\n"
            + "At least one filter criteria is required.\n"
            + "Parameters: "
            + "[" + PREFIX_TEAM + "TEAM_NAME] "
            + "[" + PREFIX_INJURY + "INJURY] "
            + "[" + PREFIX_POSITION + "POSITION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TEAM + "U16 "
            + PREFIX_INJURY + "ACL "
            + PREFIX_POSITION + "MF";
    private static final Logger logger = LogsCenter.getLogger(FilterCommand.class);

    private final FilterByTeamPredicate teamPredicate;
    private final FilterByInjuryPredicate injuryPredicate;
    private final FilterByPositionPredicate positionPredicate;

    private final Optional<String> teamArg;
    private final Optional<String> injuryArg;
    private final Optional<String> positionArg;

    /**
     * Creates a FilterCommand to filter the persons with the specified {@code teamPredicate},
     * {@code injuryPredicate}, and {@code positionPredicate}.
     */
    public FilterCommand(FilterByTeamPredicate teamPred, FilterByInjuryPredicate injuryPred,
            FilterByPositionPredicate positionPred, Optional<String> teamArg,
            Optional<String> injuryArg, Optional<String> positionArg) {
        this.teamPredicate = requireNonNull(teamPred);
        this.injuryPredicate = requireNonNull(injuryPred);
        this.positionPredicate = requireNonNull(positionPred);

        this.teamArg = requireNonNull(teamArg);
        this.injuryArg = requireNonNull(injuryArg);
        this.positionArg = requireNonNull(positionArg);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.log(Level.INFO, "Executing filter command with criteria - Team: {0}, Injury: {1}, Position: {2}",
                new Object[]{teamArg.orElse("none"), injuryArg.orElse("none"), positionArg.orElse("none")});

        requireNonNull(model);

        assert teamPredicate != null : "Team predicate should not be null";
        assert injuryPredicate != null : "Injury predicate should not be null";
        assert positionPredicate != null : "Position predicate should not be null";

        // Assert model state
        assert model.getAddressBook() != null : "Model should have address book";

        validateTeamIfPresent(model);
        validatePositionIfPresent(model);

        model.updateFilteredPersonList(person ->
            teamPredicate.test(person) && injuryPredicate.test(person) && positionPredicate.test(person));

        int filteredSize = model.getFilteredPersonList().size();
        logger.log(Level.INFO, "Filtered {0} players matching criteria", filteredSize);

        if (filteredSize == 0) {
            logger.log(Level.WARNING, "No players found matching filter criteria");
            throw createNoMatchingPlayersException();
        }

        logger.log(Level.INFO, "Filter command completed successfully");
        return CommandResult.showPersonCommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, filteredSize));
    }

    /**
     * Validates the team if it is present in the filter criteria.
     *
     * @throws CommandException if no teams match the filter criteria
     */
    private void validateTeamIfPresent(Model model) throws CommandException {
        if (teamArg.isPresent()) {
            String teamName = teamArg.get();

            // Validate team name format first
            if (!Team.isValidTeamName(teamName)) {
                throw new CommandException(String.format("Invalid team name: %s\n%s",
                        teamName, Team.MESSAGE_CONSTRAINTS));
            }

            // Check if any team in the model matches the filter criteria
            boolean hasMatchingTeam = model.getAddressBook().getTeamList().stream()
                    .anyMatch(team -> {
                        FilterByTeamPredicate predicate = new FilterByTeamPredicate(teamName);
                        return predicate.testTeam(team);
                    });

            if (!hasMatchingTeam) {
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_TEAM, teamName));
            }
        }
    }

    /**
     * Validates the position if it is present in the filter criteria.
     *
     * @throws CommandException if the position does not exist
     */
    private void validatePositionIfPresent(Model model) throws CommandException {
        if (positionArg.isPresent()) {
            Position positionToCheck = new Position(positionArg.get());
            if (!model.hasPosition(positionToCheck)) {
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_POSITION, positionToCheck.getName()));
            }
        }
    }

    /**
     * Creates an appropriate exception based on which filter criteria are present.
     */
    private CommandException createNoMatchingPlayersException() {
        if (teamArg.isPresent() && injuryArg.isPresent() && positionArg.isPresent()) {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_MATCHING_TEAM_INJURY_AND_POSITION,
                        formatKeywords(teamArg.get()), formatKeywords(injuryArg.get()), positionArg.get()));
        } else if (teamArg.isPresent() && injuryArg.isPresent()) {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_MATCHING_TEAM_AND_INJURY,
                        formatKeywords(teamArg.get()), formatKeywords(injuryArg.get())));
        } else if (teamArg.isPresent() && positionArg.isPresent()) {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_MATCHING_TEAM_AND_POSITION,
                        formatKeywords(teamArg.get()), positionArg.get()));
        } else if (positionArg.isPresent() && injuryArg.isPresent()) {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_MATCHING_INJURY_AND_POSITION,
                        formatKeywords(injuryArg.get()), positionArg.get()));
        } else if (teamArg.isPresent()) {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_PLAYERS_IN_TEAM, formatKeywords(teamArg.get())));
        } else if (positionArg.isPresent()) {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_PLAYERS_WITH_POSITION, positionArg.get()));
        } else {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_PLAYERS_WITH_INJURY, formatKeywords(injuryArg.get())));
        }
    }

    /**
     * Formats keywords by splitting on whitespace and joining with commas and quotes.
     * Example: "u16 u17" becomes "\"u16\", \"u17\""
     */
    private String formatKeywords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }
        return Arrays.stream(input.trim().split("\\s+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FilterCommand)) {
            return false;
        }
        FilterCommand o = (FilterCommand) other;
        return Objects.equals(teamPredicate, o.teamPredicate)
            && Objects.equals(injuryPredicate, o.injuryPredicate)
            && Objects.equals(positionPredicate, o.positionPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("teamPredicate", teamPredicate)
                .add("injuryPredicate", injuryPredicate)
                .add("positionPredicate", positionPredicate)
                .toString();
    }
}
