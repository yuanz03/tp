package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in trying to makeCaptain on an already existing captain
 */
public class AlreadyNotCaptainException extends RuntimeException {
    public AlreadyNotCaptainException() {
        super("Person already not a captain");
    }
}
