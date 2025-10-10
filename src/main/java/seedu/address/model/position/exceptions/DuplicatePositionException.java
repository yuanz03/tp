package seedu.address.model.position.exceptions;

/**
 * Signals that the operation will result in duplicate Positions.
 */
public class DuplicatePositionException extends RuntimeException {
    public DuplicatePositionException() {
        super("Operation would result in duplicate positions");
    }
}


