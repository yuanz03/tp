package seedu.address.model.team.exceptions;

/**
 * Signals that the operation will result in duplicate Teams being added to the address book.
 */
public class DuplicateTeamException extends RuntimeException {
    public DuplicateTeamException() {
        super("Operation would result in duplicate teams");
    }
}
