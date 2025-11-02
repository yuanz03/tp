package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.FilterByInjuryPredicate;
import seedu.address.model.position.FilterByPositionPredicate;
import seedu.address.model.position.Position;
import seedu.address.model.team.FilterByTeamPredicate;

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
        assert model.getAddressBook() != null : "Model should have address book";

        validateTeamIfPresent(model);
        validatePositionIfPresent(model);

        model.updateFilteredPersonList(person ->
            teamPredicate.test(person) && injuryPredicate.test(person) && positionPredicate.test(person));

        int filteredSize = model.getFilteredPersonList().size();
        logger.log(Level.INFO, "Filtered {0} players matching criteria", filteredSize);

        if (filteredSize == 0) {
            logger.log(Level.WARNING, "No players found matching filter criteria");
            throw createNoMatchingPlayersException(model);
        }

        logger.log(Level.INFO, "Filter command completed successfully");
        return CommandResult.showPersonCommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, filteredSize));
    }

    /**
     * Validates that the team exists in the model.
     */
    private void validateTeamIfPresent(Model model) throws CommandException {
        if (teamArg.isPresent()) {
            String teamName = teamArg.get();
            if (!teamExistsInModel(model, teamName)) {
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_TEAM, teamName));
            }
        }
    }

    /**
     * Validates the position if it is present in the filter criteria.
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
     * Creates an appropriate exception when no players match the filter criteria.
     */
    private CommandException createNoMatchingPlayersException(Model model) {
        if (teamArg.isPresent() && !teamExistsInModel(model, teamArg.get())) {
            return new CommandException(String.format(Messages.MESSAGE_INVALID_TEAM, teamArg.get()));
        }

        if (positionArg.isPresent() && !positionExistsInModel(model, positionArg.get())) {
            return new CommandException(String.format(Messages.MESSAGE_INVALID_POSITION, positionArg.get()));
        }

        return createNoCombinationMatchException();
    }

    /**
     * Checks if a team exists in the model.
     */
    private boolean teamExistsInModel(Model model, String teamName) {
        return model.getAddressBook().getTeamList().stream()
                .anyMatch(team -> team.getName().equalsIgnoreCase(teamName));
    }

    /**
     * Checks if a position exists in the model.
     */
    private boolean positionExistsInModel(Model model, String positionName) {
        return model.getAddressBook().getPositionList().stream()
                .anyMatch(position -> position.getName().equalsIgnoreCase(positionName));
    }

    /**
     * Creates exception for when criteria exist but no player matches the combination.
     */
    private CommandException createNoCombinationMatchException() {
        if (teamArg.isPresent() && injuryArg.isPresent() && positionArg.isPresent()) {
            return new CommandException(String.format(Messages.MESSAGE_NO_MATCHING_TEAM_INJURY_AND_POSITION,
                    teamArg.get(), injuryArg.get(), positionArg.get()));
        } else if (teamArg.isPresent() && injuryArg.isPresent()) {
            return new CommandException(String.format(Messages.MESSAGE_NO_MATCHING_TEAM_AND_INJURY,
                    teamArg.get(), injuryArg.get()));
        } else if (teamArg.isPresent() && positionArg.isPresent()) {
            return new CommandException(String.format(Messages.MESSAGE_NO_MATCHING_TEAM_AND_POSITION,
                    teamArg.get(), positionArg.get()));
        } else if (injuryArg.isPresent() && positionArg.isPresent()) {
            return new CommandException(String.format(Messages.MESSAGE_NO_MATCHING_INJURY_AND_POSITION,
                    injuryArg.get(), positionArg.get()));
        } else if (teamArg.isPresent()) {
            return new CommandException(String.format(Messages.MESSAGE_NO_PLAYERS_IN_TEAM, teamArg.get()));
        } else if (injuryArg.isPresent()) {
            return new CommandException(String.format(Messages.MESSAGE_NO_PLAYERS_WITH_INJURY, injuryArg.get()));
        } else if (positionArg.isPresent()) {
            return new CommandException(String.format(Messages.MESSAGE_NO_PLAYERS_WITH_POSITION, positionArg.get()));
        } else {
            // This should never happen due to validation, but handle gracefully
            return new CommandException("No filter criteria specified");
        }
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
