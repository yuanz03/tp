package seedu.address.model.injury;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.injury.exceptions.DuplicateInjuryException;
import seedu.address.model.injury.exceptions.InjuryNotFoundException;

/**
 * A list of injuries that enforces uniqueness between its elements and does not allow nulls.
 * An injury is considered unique by comparing using {@code Injury#isSameInjury(Injury)}. As such, adding and updating
 * of injuries uses Injury#isSameInjury(Injury) for equality so as to ensure that the injury being added or updated is
 * unique in terms of identity in the UniqueInjuryList. However, the removal of an injury uses Injury#equals(Object)
 * so as to ensure that the injury with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Injury#isSameInjury(Injury)
 */
public class UniqueInjuryList implements Iterable<Injury> {
    private final ObservableList<Injury> internalList = FXCollections.observableArrayList();
    private final ObservableList<Injury> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(this.internalList);

    /**
     * Returns true if the list contains an equivalent injury as the given argument.
     */
    public boolean contains(Injury toCheck) {
        requireNonNull(toCheck);
        return this.internalList.stream().anyMatch(toCheck::isSameInjury);
    }

    /**
     * Adds an injury to the list.
     * The injury must not already exist in the list.
     */
    public void add(Injury toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateInjuryException();
        }
        this.internalList.add(toAdd);
    }

    /**
     * Replaces the injury {@code target} in the list with {@code editedInjury}.
     * {@code target} must exist in the list.
     * The injury identity of {@code editedInjury} must not be the same as another existing injury in the list.
     */
    public void setInjury(Injury target, Injury editedInjury) {
        requireAllNonNull(target, editedInjury);

        int index = this.internalList.indexOf(target);
        if (index == -1) {
            throw new InjuryNotFoundException();
        }

        if (!target.isSameInjury(editedInjury) && contains(editedInjury)) {
            throw new DuplicateInjuryException();
        }

        this.internalList.set(index, editedInjury);
    }

    /**
     * Removes the equivalent injury from the list.
     * The injury must exist in the list.
     */
    public void remove(Injury toRemove) {
        requireNonNull(toRemove);
        if (!this.internalList.remove(toRemove)) {
            throw new InjuryNotFoundException();
        }
    }

    public void setInjuries(UniqueInjuryList replacement) {
        requireNonNull(replacement);
        this.internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code injuries}.
     * {@code injuries} must not contain duplicate injuries.
     */
    public void setInjuries(List<Injury> injuries) {
        requireAllNonNull(injuries);
        if (!injuriesAreUnique(injuries)) {
            throw new DuplicateInjuryException();
        }

        this.internalList.setAll(injuries);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Injury> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
    }

    @Override
    public Iterator<Injury> iterator() {
        return this.internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueInjuryList)) {
            return false;
        }
        UniqueInjuryList otherUniqueInjuryList = (UniqueInjuryList) other;
        return this.internalList.equals(otherUniqueInjuryList.internalList);
    }

    @Override
    public int hashCode() {
        return this.internalList.hashCode();
    }

    @Override
    public String toString() {
        return this.internalList.toString();
    }

    /**
     * Returns true if {@code injuries} contains only unique injuries.
     */
    private boolean injuriesAreUnique(List<Injury> injuries) {
        for (int i = 0; i < injuries.size() - 1; i++) {
            for (int j = i + 1; j < injuries.size(); j++) {
                if (injuries.get(i).isSameInjury(injuries.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
