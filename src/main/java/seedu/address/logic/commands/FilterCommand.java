package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.Objects;
import java.util.Optional;

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
        requireNonNull(model);

        validateTeamIfPresent(model);
        validatePositionIfPresent(model);

        model.updateFilteredPersonList(person ->
            teamPredicate.test(person) && injuryPredicate.test(person) && positionPredicate.test(person));

        int filteredSize = model.getFilteredPersonList().size();

        if (filteredSize == 0) {
            throw createNoMatchingPlayersException();
        }

        return CommandResult.showPersonCommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, filteredSize));
    }

    /**
     * Validates the team if it is present in the filter criteria.
     *
     * @throws CommandException if the team does not exist
     */
    private void validateTeamIfPresent(Model model) throws CommandException {
        if (teamArg.isPresent()) {
            Team teamToCheck = new Team(teamArg.get());
            if (!model.hasTeam(teamToCheck)) {
                throw new CommandException(Messages.MESSAGE_INVALID_TEAM);
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
                throw new CommandException(Messages.MESSAGE_INVALID_POSITION);
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
                        teamArg.get(), injuryArg.get(), positionArg.get()));
        } else if (teamArg.isPresent() && injuryArg.isPresent()) {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_MATCHING_TEAM_AND_INJURY, teamArg.get(), injuryArg.get()));
        } else if (teamArg.isPresent() && positionArg.isPresent()) {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_MATCHING_TEAM_AND_POSITION, teamArg.get(), positionArg.get()));
        } else if (positionArg.isPresent() && injuryArg.isPresent()) {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_MATCHING_INJURY_AND_POSITION, injuryArg.get(), positionArg.get()));
        } else if (teamArg.isPresent()) {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_PLAYERS_IN_TEAM, teamArg.get()));
        } else if (positionArg.isPresent()) {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_PLAYERS_WITH_POSITION, positionArg.get()));
        } else {
            return new CommandException(
                String.format(Messages.MESSAGE_NO_PLAYERS_WITH_INJURY, injuryArg.get()));
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
