package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Container for user visible messages.
 */
public class Messages {
    // General Command Messages
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_EMPTY_COMMAND = "Your %1$s command cannot be empty!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This player already exists in the address book.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_NON_EMPTY_PREAMBLE =
            "Unexpected parameters detected at the start of your %1$s command!";

    // General Error Messages for Input Validation
    public static final String MESSAGE_TOO_MANY_PREFIXES = "There are too many prefixes specified. \n%1$s";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";

    // Player Command Messages
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person '%1$s' does not exist.";
    public static final String MESSAGE_MISSING_PLAYER_PREFIX = "Your %1$s command is missing the player parameter!";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Player: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_EDIT_NO_CHANGES = "No changes detected. Please modify at least one field.";

    // Team Command Messages
    public static final String MESSAGE_INVALID_TEAM = "No such team in the PlayBook.";

    // Position Command Messages
    public static final String MESSAGE_INVALID_POSITION = "No such position in the PlayBook.";

    // Injury Command Messages
    public static final String MESSAGE_MISSING_INJURY_PREFIX = "Your %1$s command is missing the injury parameter!";
    public static final String MESSAGE_MISSING_BOTH_PREFIXES =
            "Your %1$s command is missing both player and injury parameters!";
    public static final String MESSAGE_ASSIGN_INJURY_SUCCESS = "%1$s's injury status has been set to: %2$s";
    public static final String MESSAGE_UNASSIGN_INJURY_SUCCESS = "%1$s's %2$s injury status has been removed!";
    public static final String MESSAGE_ASSIGNED_SAME_INJURY = "%1$s is already assigned the injury status: %2$s";
    public static final String MESSAGE_INJURY_NOT_FOUND = "%1$s has no record of the injury status: %2$s";
    public static final String MESSAGE_INJURY_ALREADY_UNASSIGNED =
            "%1$s's injury status has already been set to the default 'FIT' status!";
    public static final String MESSAGE_INVALID_INJURY_ASSIGNMENT = "'FIT' cannot be assigned as an injury status!\n"
            + "Please use the unassigninjury command instead to restore 'FIT' status.";

    // Filter Command Messages
    public static final String MESSAGE_NO_PLAYERS_IN_TEAM = "No players assigned to team \"%1$s\".";
    public static final String MESSAGE_NO_PLAYERS_WITH_INJURY = "No players with injury \"%1$s\".";
    public static final String MESSAGE_NO_PLAYERS_WITH_POSITION = "No players with position \"%1$s\".";
    public static final String MESSAGE_NO_MATCHING_TEAM_INJURY_AND_POSITION =
            "No players matching team \"%1$s\", injury \"%2$s\" and position \"%3$s\".";
    public static final String MESSAGE_NO_MATCHING_TEAM_AND_INJURY =
            "No players matching team \"%1$s\" and injury \"%2$s\".";
    public static final String MESSAGE_NO_MATCHING_TEAM_AND_POSITION =
            "No players matching team \"%1$s\" and position \"%2$s\".";
    public static final String MESSAGE_NO_MATCHING_INJURY_AND_POSITION =
            "No players matching injury \"%1$s\" and position \"%2$s\".";

    // List Command Messages
    public static final String MESSAGE_NO_TEAMS = "There are currently no teams in the PlayBook.";
    public static final String MESSAGE_NO_POSITIONS = "There are currently no positions in the PlayBook.";
    public static final String MESSAGE_NO_INJURED = "There are currently no injured players in the PlayBook.";
    public static final String MESSAGE_NO_PLAYERS = "There are currently no players in the PlayBook.";
    public static final String MESSAGE_NO_CAPTAINS = "There are currently no captains in the PlayBook.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Team: ")
                .append(person.getTeam())
                .append("; Position: ")
                .append(person.getPosition())
                .append("; Injuries: ")
                .append(person.getInjuries())
                .append("; Captain Status: ")
                .append(person.isCaptain() ? "Active" : "Inactive")
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        builder.append("; ");
        return builder.toString();
    }

    /**
     * Formats the {@code team} for display to the user.
     */
    public static String format(Team team) {
        final StringBuilder builder = new StringBuilder();
        builder.append(team.getName());
        return builder.toString();
    }
}
