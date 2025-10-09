package seedu.address.model.position.exceptions;

/**
 * Signals that the operation is unable to find the specified Position.
 */
public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException() {
        super("Position not found");
    }
}


