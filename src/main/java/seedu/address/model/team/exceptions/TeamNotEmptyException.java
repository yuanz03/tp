package seedu.address.model.team.exceptions;

/**
 * Signals that operation could not delete the team as it is not empty.
 */
public class TeamNotEmptyException extends RuntimeException {
    public TeamNotEmptyException() {
        super("Operation could not delete the team as it is not empty");
    }
}
