package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;
import seedu.address.model.util.SampleDataUtil;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsCorrectNumberOfPersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(6, samplePersons.length);
    }

    @Test
    public void getSamplePersons_allPersonsNotNull() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        for (Person person : samplePersons) {
            assertNotNull(person);
            assertNotNull(person.getName());
            assertNotNull(person.getPhone());
            assertNotNull(person.getEmail());
            assertNotNull(person.getAddress());
            assertNotNull(person.getTeam());
            assertNotNull(person.getInjuries());
            assertNotNull(person.getTags());
        }
    }

    @Test
    public void getSamplePersons_containsExpectedPersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        boolean alexYeohFound = Arrays.stream(samplePersons)
                .anyMatch(person -> person.getName().toString().equals("Alex Yeoh"));
        assertTrue(alexYeohFound);

        boolean berniceYuFound = Arrays.stream(samplePersons)
                .anyMatch(person -> person.getName().toString().equals("Bernice Yu"));
        assertTrue(berniceYuFound);

        boolean charlotteFound = Arrays.stream(samplePersons)
                .anyMatch(person -> person.getName().toString().equals("Charlotte Oliveiro"));
        assertTrue(charlotteFound);
    }

    @Test
    public void getSampleTeams_returnsCorrectNumberOfTeams() {
        Team[] sampleTeams = SampleDataUtil.getSampleTeams();
        assertEquals(3, sampleTeams.length);
    }

    @Test
    public void getSampleTeams_allTeamsNotNull() {
        Team[] sampleTeams = SampleDataUtil.getSampleTeams();
        for (Team team : sampleTeams) {
            assertNotNull(team);
            assertNotNull(team.getName());
        }
    }

    @Test
    public void getSampleTeams_containsExpectedTeams() {
        Team[] sampleTeams = SampleDataUtil.getSampleTeams();

        boolean u12Found = Arrays.stream(sampleTeams)
                .anyMatch(team -> team.getName().equals("U12"));
        assertTrue(u12Found);

        boolean u16Found = Arrays.stream(sampleTeams)
                .anyMatch(team -> team.getName().equals("U16"));
        assertTrue(u16Found);

        boolean u21Found = Arrays.stream(sampleTeams)
                .anyMatch(team -> team.getName().equals("U21"));
        assertTrue(u21Found);
    }

    @Test
    public void getSampleAddressBook_notNull() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleAddressBook);
    }

    @Test
    public void getSampleAddressBook_containsCorrectNumberOfPersons() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();
        assertEquals(6, sampleAddressBook.getPersonList().size());
    }

    @Test
    public void getSampleAddressBook_containsCorrectNumberOfTeams() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();
        assertEquals(3, sampleAddressBook.getTeamList().size());
    }

    @Test
    public void getSampleAddressBook_teamsAddedBeforePersons() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();

        // Verify that all teams referenced by persons exist in the address book
        for (Person person : sampleAddressBook.getPersonList()) {
            assertTrue(sampleAddressBook.getTeamList().contains(person.getTeam()));
        }
    }

    @Test
    public void getTagSet_emptyStrings_returnsEmptySet() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet();
        assertTrue(tagSet.isEmpty());
    }

    @Test
    public void getTagSet_singleString_returnsSingleTagSet() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friends");
        assertEquals(1, tagSet.size());
        assertTrue(tagSet.contains(new Tag("friends")));
    }

    @Test
    public void getTagSet_multipleStrings_returnsCorrectTagSet() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friends", "colleagues", "family");
        assertEquals(3, tagSet.size());
        assertTrue(tagSet.contains(new Tag("friends")));
        assertTrue(tagSet.contains(new Tag("colleagues")));
        assertTrue(tagSet.contains(new Tag("family")));
    }

    @Test
    public void getTagSet_duplicateStrings_returnsUniqueTagSet() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friends", "friends", "colleagues");
        assertEquals(2, tagSet.size());
        assertTrue(tagSet.contains(new Tag("friends")));
        assertTrue(tagSet.contains(new Tag("colleagues")));
    }

}
