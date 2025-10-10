package seedu.address.model.position;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.position.exceptions.DuplicatePositionException;
import seedu.address.model.position.exceptions.PositionNotFoundException;

/**
 * A list of positions that enforces uniqueness between its elements and does not allow nulls.
 * A position is considered unique by {@link Position#isSamePosition(Position)}.
 */
public class UniquePositionList implements Iterable<Position> {

    private final ObservableList<Position> internalList = FXCollections.observableArrayList();
    private final ObservableList<Position> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent position as the given argument.
     */
    public boolean contains(Position toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePosition);
    }

    /**
     * Adds a position to the list. The position must not already exist in the list.
     */
    public void add(Position toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePositionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent position from the list. The position must exist in the list.
     */
    public void remove(Position toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PositionNotFoundException();
        }
    }

    /**
     * Retrieves a position by name (case-insensitive).
     *
     * @throws PositionNotFoundException if no such position exists.
     */
    public Position getByName(String name) {
        requireNonNull(name);
        for (Position p : internalList) {
            if (p.getName().equalsIgnoreCase(name.trim())) {
                return p;
            }
        }
        throw new PositionNotFoundException();
    }

    /**
     * Replaces the contents of this list with {@code positions}. {@code positions} must be unique.
     */
    public void setPositions(List<Position> positions) {
        requireAllNonNull(positions);
        if (!positionsAreUnique(positions)) {
            throw new DuplicatePositionException();
        }
        internalList.setAll(positions);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Position> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Position> iterator() {
        return internalList.iterator();
    }

    private boolean positionsAreUnique(List<Position> positions) {
        for (int i = 0; i < positions.size() - 1; i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                if (positions.get(i).isSamePosition(positions.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}


