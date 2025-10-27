package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Injury;

/**
 * Jackson-friendly version of {@link Injury}.
 */
class JsonAdaptedInjury {

    private final String injuryName;

    /**
     * Constructs a {@code JsonAdaptedInjury} with the given {@code injuryName}.
     */
    @JsonCreator
    public JsonAdaptedInjury(String injuryName) {
        this.injuryName = injuryName;
    }

    /**
     * Converts a given {@code Injury} into this class for Jackson use.
     */
    public JsonAdaptedInjury(Injury source) {
        this.injuryName = source.getInjuryName();
    }

    @JsonValue
    public String getInjuryName() {
        return this.injuryName;
    }

    /**
     * Converts this Jackson-friendly adapted Injury object into the model's {@code Injury} object.
     *
     * @throws IllegalValueException If there were any data constraints violated in the adapted injury.
     */
    public Injury toModelType() throws IllegalValueException {
        if (!Injury.isValidInjuryName(this.injuryName)) {
            throw new IllegalValueException(Injury.MESSAGE_CONSTRAINTS);
        }
        return new Injury(this.injuryName);
    }
}
