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
     * Creates a FilterCommand to filter the persons with the specified {@code teamPredicate}
     * and {@code injuryPredicate}.
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

        Optional<String> tArg = teamArg;
        Optional<String> iArg = injuryArg;
        Optional<String> psArg = positionArg;

        if (tArg.isPresent()) {
            Team check = new Team(tArg.get());
            if (!model.hasTeam(check)) {
                throw new CommandException(Messages.MESSAGE_INVALID_TEAM);
            }
        }

        model.updateFilteredPersonList(person ->
            teamPredicate.test(person) && injuryPredicate.test(person) && positionPredicate.test(person));

        int size = model.getFilteredPersonList().size();
        if (size == 0) {
            if (tArg.isPresent() && iArg.isPresent() && psArg.isPresent()) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_NO_MATCHING_TEAM_INJURY_AND_POSITION,
                            tArg.get(), iArg.get(), psArg.get()));
            } else if (tArg.isPresent() && iArg.isPresent()) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_NO_MATCHING_TEAM_AND_INJURY, tArg.get(), iArg.get()));
            } else if (tArg.isPresent() && psArg.isPresent()) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_NO_MATCHING_TEAM_AND_POSITION, tArg.get(), psArg.get()));
            } else if (psArg.isPresent() && iArg.isPresent()) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_NO_MATCHING_INJURY_AND_POSITION, iArg.get(), psArg.get()));
            } else if (tArg.isPresent()) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_NO_PLAYERS_IN_TEAM, tArg.get()));
            } else if (psArg.isPresent()) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_NO_PLAYERS_WITH_POSITION, psArg.get()));
            } else {
                throw new CommandException(
                    String.format(Messages.MESSAGE_NO_PLAYERS_WITH_INJURY, iArg.get()));
            }
        }

        return CommandResult.showPersonCommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, size));
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
