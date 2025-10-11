package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTeams.U12;
import static seedu.address.testutil.TypicalTeams.U16;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.team.exceptions.DuplicateTeamException;
import seedu.address.model.team.exceptions.TeamNotFoundException;

public class UniqueTeamListTest {

    private final UniqueTeamList uniqueTeamList = new UniqueTeamList();

    @Test
    public void add_duplicateTeam_throwsDuplicateTeamException() {
        uniqueTeamList.add(U12);
        assertThrows(DuplicateTeamException.class, () -> uniqueTeamList.add(U12));
    }

    @Test
    public void contains_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.contains(null));
    }

    @Test
    public void contains_teamNotInList_returnsFalse() {
        assertFalse(uniqueTeamList.contains(U12));
    }

    @Test
    public void contains_teamInList_returnsTrue() {
        uniqueTeamList.add(U12);
        assertTrue(uniqueTeamList.contains(U12));
    }

    @Test
    public void add_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.add(null));
    }

    @Test
    public void remove_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.remove(null));
    }

    @Test
    public void remove_teamNotInList_throwsTeamNotFoundException() {
        assertThrows(TeamNotFoundException.class, () -> uniqueTeamList.remove(U12));
    }

    @Test
    public void remove_teamInList_success() {
        uniqueTeamList.add(U12);
        assertTrue(uniqueTeamList.contains(U12));
        uniqueTeamList.remove(U12);
        assertFalse(uniqueTeamList.contains(U12));
    }

    @Test
    public void remove_teamFromListWithMultipleTeams_success() {
        uniqueTeamList.add(U12);
        uniqueTeamList.add(U16);
        assertTrue(uniqueTeamList.contains(U12));
        assertTrue(uniqueTeamList.contains(U16));

        uniqueTeamList.remove(U12);
        assertFalse(uniqueTeamList.contains(U12));
        assertTrue(uniqueTeamList.contains(U16));
    }

    @Test
    public void remove_sameTeamTwice_throwsTeamNotFoundException() {
        uniqueTeamList.add(U12);
        uniqueTeamList.remove(U12);
        assertThrows(TeamNotFoundException.class, () -> uniqueTeamList.remove(U12));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueTeamList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void setTeams_duplicateTeams_throwsDuplicateTeamException() {
        assertThrows(DuplicateTeamException.class, () -> uniqueTeamList
                .setTeams(Arrays.asList(U12, U12)));
    }

    @Test
    public void equalsMethod() {
        UniqueTeamList anotherList = new UniqueTeamList();
        assertEquals(uniqueTeamList, anotherList);
        anotherList.add(U12);
        assertNotEquals(uniqueTeamList, anotherList);
        uniqueTeamList.add(U12);
        assertEquals(uniqueTeamList, anotherList);
        assertNotEquals(null, uniqueTeamList);
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueTeamList.asUnmodifiableObservableList().toString(), uniqueTeamList.toString());
    }
}
