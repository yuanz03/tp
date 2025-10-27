package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in an illegal injury status.
 */
public class IllegalInjuryException extends RuntimeException {
    public IllegalInjuryException(String message) {
        super(message);
    }
}
