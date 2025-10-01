package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTeams.U12;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.team.exceptions.DuplicateTeamException;

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
