package seedu.address.model.team.exceptions;

/**
 * Signals that operation could not find the specified team.
 */
public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {
        super("Operation could not find the specified team");
    }
}
