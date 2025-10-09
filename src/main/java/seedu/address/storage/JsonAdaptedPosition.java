package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.position.Position;

/**
 * Jackson-friendly version of {@link Position}.
 */
public class JsonAdaptedPosition {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Position's %s field is missing!";

    private final String name;

    @JsonCreator
    public JsonAdaptedPosition(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Position} into this class for Jackson use.
     */
    public JsonAdaptedPosition(Position source) {
        this.name = source.getName();
    }

    /**
     * Converts this Jackson-friendly adapted position object into the model's {@code Position} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted position.
     */
    public Position toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name"));
        }
        if (!Position.isValidPositionName(name)) {
            throw new IllegalValueException(Position.MESSAGE_CONSTRAINTS);
        }
        return new Position(name);
    }
}


