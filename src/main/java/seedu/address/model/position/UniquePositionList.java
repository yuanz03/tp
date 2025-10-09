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
 */
public class UniquePositionList implements Iterable<Position> {

    private final ObservableList<Position> internalList = FXCollections.observableArrayList();
    private final ObservableList<Position> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(Position toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePosition);
    }

    public void add(Position toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePositionException();
        }
        internalList.add(toAdd);
    }

    public void remove(Position toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PositionNotFoundException();
        }
    }

    public Position getByName(String name) {
        requireNonNull(name);
        for (Position p : internalList) {
            if (p.getName().equalsIgnoreCase(name.trim())) {
                return p;
            }
        }
        throw new PositionNotFoundException();
    }

    public void setPositions(List<Position> positions) {
        requireAllNonNull(positions);
        if (!positionsAreUnique(positions)) {
            throw new DuplicatePositionException();
        }
        internalList.setAll(positions);
    }

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


