package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.position.Position;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_INJURY_STATUS = "@nkle sprain";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final JsonAdaptedTeam VALID_TEAM = new JsonAdaptedTeam(BENSON.getTeam().toString());
    private static final JsonAdaptedPosition VALID_POSITION = new JsonAdaptedPosition(BENSON.getPosition());

    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedInjury> VALID_INJURIES = BENSON.getInjuries().stream()
            .map(JsonAdaptedInjury::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_INJURIES, VALID_TEAM, VALID_POSITION, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_INJURIES, VALID_TEAM, VALID_POSITION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_INJURIES, VALID_TEAM, VALID_POSITION, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_INJURIES, VALID_TEAM, VALID_POSITION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                VALID_INJURIES, VALID_TEAM, VALID_POSITION, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_INJURIES, VALID_TEAM, VALID_POSITION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                VALID_INJURIES, VALID_TEAM, VALID_POSITION, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_INJURIES, VALID_TEAM, VALID_POSITION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidInjuries_throwsIllegalValueException() {
        List<JsonAdaptedInjury> invalidInjuries = new ArrayList<>(VALID_INJURIES);
        invalidInjuries.add(new JsonAdaptedInjury(INVALID_INJURY_STATUS));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                invalidInjuries, VALID_TEAM, VALID_POSITION, VALID_TAGS);
        assertThrows(IllegalValueException.class, Injury.MESSAGE_CONSTRAINTS, person::toModelType);
    }

    @Test
    public void toModelType_emptyInjuryList_addsDefaultInjury() throws Exception {
        List<JsonAdaptedInjury> emptyInjuries = new ArrayList<>();
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                emptyInjuries, VALID_TEAM, VALID_POSITION, VALID_TAGS);
        Person modelPerson = person.toModelType();

        assertTrue(modelPerson.getInjuries().contains(Person.DEFAULT_INJURY_STATUS));
        assertEquals(1, modelPerson.getInjuries().size());
    }

    @Test
    public void toModelType_nonEmptyInjuryList_preservesAllInjuries() throws Exception {
        List<JsonAdaptedInjury> validInjuries = new ArrayList<>(VALID_INJURIES);
        validInjuries.add(new JsonAdaptedInjury(new Injury("ACL")));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                validInjuries, VALID_TEAM, VALID_POSITION, VALID_TAGS);
        Person modelPerson = person.toModelType();

        // Should contain all provided injuries
        assertTrue(modelPerson.getInjuries().contains(new Injury("Broken foot")));
        assertTrue(modelPerson.getInjuries().contains(new Injury("ACL")));
        assertEquals(2, modelPerson.getInjuries().size());

        // Should not contain the default "FIT" injury status when other injuries are present
        assertFalse(modelPerson.getInjuries().contains(Person.DEFAULT_INJURY_STATUS));
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_INJURIES, VALID_TEAM, VALID_POSITION, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullTeam_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_INJURIES, null, VALID_POSITION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Team");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPosition_defaultsToNone() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_INJURIES, VALID_TEAM, null, VALID_TAGS);
        Person model = person.toModelType();
        assertEquals(new Position("NONE"), model.getPosition());
    }

    @Test
    public void toModelType_nullTags_allowsEmpty() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_INJURIES, VALID_TEAM, VALID_POSITION, null);
        Person model = person.toModelType();
        assertTrue(model.getTags().isEmpty());
    }

    @Test
    public void roundTrip_preservesCaptainTrue_andInjuries() throws Exception {
        Person source = new seedu.address.testutil.PersonBuilder(BENSON)
                .withCaptain(true)
                .withInjuries("ACL", "BrokenFoot")
                .build();
        JsonAdaptedPerson adapted = new JsonAdaptedPerson(source);
        Person restored = adapted.toModelType();
        assertTrue(restored.isCaptain());
        assertTrue(restored.getInjuries().contains(new Injury("ACL")));
        assertTrue(restored.getInjuries().contains(new Injury("BrokenFoot")));
    }

    @Test
    public void toModelType_captainNull_defaultsFalse() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_INJURIES, VALID_TEAM, VALID_POSITION, VALID_TAGS, null);
        Person model = person.toModelType();
        assertFalse(model.isCaptain());
    }
}
