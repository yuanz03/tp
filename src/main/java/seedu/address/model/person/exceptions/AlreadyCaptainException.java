package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in trying to assignCaptain on an already existing captain
 */
public class AlreadyCaptainException extends RuntimeException {
    public AlreadyCaptainException() {
        super("Person already a captain");
    }
}
