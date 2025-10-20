package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.position.Position;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TEAM = "U12";
    public static final String DEFAULT_POSITION = "NONE";
    public static final boolean DEFAULT_CAPTAINCY = false;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Injury> injuries;
    private Team team;
    private Set<Tag> tags;
    private Position position;
    private boolean isCaptain;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        injuries = new HashSet<>(Set.of(Person.DEFAULT_INJURY_STATUS));
        team = new Team(DEFAULT_TEAM);
        tags = new HashSet<>();
        isCaptain = DEFAULT_CAPTAINCY;
        position = new Position(DEFAULT_POSITION);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        injuries = personToCopy.getInjuries();
        team = personToCopy.getTeam();
        tags = new HashSet<>(personToCopy.getTags());
        position = personToCopy.getPosition();
        isCaptain = personToCopy.isCaptain();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the
     * {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Parses the {@code injuries} into a {@code Set<Injury>} and set it to the
     * {@code Person} that we are building.
     */
    public PersonBuilder withInjuries(String... injuries) {
        this.injuries = SampleDataUtil.getInjurySet(injuries);
        return this;
    }

    /**
     * Sets the {@code Team} of the {@code Person} that we are building.
     */
    public PersonBuilder withTeam(String team) {
        this.team = new Team(team);
        return this;
    }

    /**
     * Sets the {@code Position} of the {@code Person} that we are building.
     */
    public PersonBuilder withPosition(String positionName) {
        this.position = new Position(positionName);
        return this;
    }

    /**
     * Sets the {@code isCaptain} of the {@code Person} that we are building.
     */
    public PersonBuilder withCaptain(boolean isCaptain) {
        this.isCaptain = isCaptain;
        return this;
    }

    /**
     * Builds a {@link Person} instance with the configured state.
     */
    public Person build() {
        return new Person(name, phone, email, address, team, tags, position, injuries, isCaptain);
    }
}
