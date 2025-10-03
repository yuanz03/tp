package seedu.address.model.injury.exceptions;

/**
 * Signals that the operation will result in duplicate Injuries (Injuries are considered duplicates if they have the
 * same identity).
 */
public class DuplicateInjuryException extends RuntimeException {
    public DuplicateInjuryException() {
        super("Operation would result in duplicate injuries");
    }
}
