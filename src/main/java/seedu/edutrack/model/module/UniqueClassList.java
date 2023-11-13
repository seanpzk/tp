package seedu.edutrack.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.edutrack.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.edutrack.commons.core.index.Index;
import seedu.edutrack.model.module.exceptions.ClassNotFoundException;
import seedu.edutrack.model.module.exceptions.DuplicateClassException;

/**
 * A list of classes that enforces uniqueness between its elements and does not allow nulls.
 * A class is considered unique by comparing using {@code Class#isSameClass(Class)}. As such, adding and updating of
 * classes uses Class#isSameClass(Class) for equality to ensure that the class being added or updated is
 * unique in terms of identity in the UniqueClassList. However, the removal of a class uses Class#equals(Object)
 * to ensure that the class with exactly the same fields will be removed.
 * Supports a minimal set of list operations.
 *
 * @see Class#isSameClass(Class)
 */
public class UniqueClassList implements Iterable<Class> {

    private final ObservableList<Class> internalList = FXCollections.observableArrayList();
    private final ObservableList<Class> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent class as the given argument.
     *
     * @param toCheck The class to check for in the list.
     * @return True if the list contains an equivalent class, false otherwise.
     */
    public boolean contains(Class toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameClass);
    }

    /**
     * Adds a class to the list.
     * The class must not already exist in the list.
     *
     * @param toAdd The class to add.
     * @throws DuplicateClassException If adding the class would result in duplicate classes.
     */
    public void add(Class toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateClassException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes a class from the list
     * The Class must already exist in the list.
     *
     * @param toRemove The class to remove.
     * @throws ClassNotFoundException If the class to be removed is not found in the existing list.
     */
    public void remove(Class toRemove) throws ClassNotFoundException {
        requireNonNull(toRemove);
        if (!this.contains(toRemove)) {
            throw new ClassNotFoundException();
        }
        this.internalList.remove(toRemove);
    }

    /**
     * Removes the class at the specified index in the list.
     * The index must be within the bounds of the list.
     * @param index The index of the class to remove.
     */
    public void remove(Index index) {
        if (index.getZeroBased() >= internalList.size()) {
            throw new IndexOutOfBoundsException();
        }
        internalList.remove(index.getZeroBased());
    }

    public void setClasses(UniqueClassList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code classes}.
     * {@code classes} must not contain duplicate classes.
     */
    public void setClasses(List<Class> classes) {
        requireAllNonNull(classes);
        if (!classesAreUnique(classes)) {
            throw new DuplicateClassException();
        }

        internalList.setAll(classes);
    }

    /**
     * Returns the class at the specified index in the list.
     * The index must be within the bounds of the list.
     * @param index The index of the class to retrieve.
     */
    public Class getClassAtIndex(Index index) {
        requireNonNull(index);
        if (index.getZeroBased() >= internalList.size()) {
            throw new IndexOutOfBoundsException();
        }
        return internalList.get(index.getZeroBased());
    }

    /**
     * Returns an unmodifiable view of the list of classes.
     *
     * @return An unmodifiable view of the list of classes.
     */
    public ObservableList<Class> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Class> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueClassList)) {
            return false;
        }

        UniqueClassList otherUniqueClassList = (UniqueClassList) other;
        return internalList.equals(otherUniqueClassList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    public int getSize() {
        return internalList.size();
    }

    /**
     * Sets the class at the specified index in the list to the specified class.
     * @param index The index of the class to set.
     * @param classToSet The class to set.
     */

    public void setClass(Index index, Class classToSet) {
        requireNonNull(classToSet);
        if (index.getZeroBased() >= internalList.size()) {
            throw new IndexOutOfBoundsException();
        }
        internalList.set(index.getZeroBased(), classToSet);
    }
    /**
     * Returns true if {@code classes} contains only unique classes.
     */
    public boolean classesAreUnique(List<Class> classes) {
        for (int i = 0; i < classes.size() - 1; i++) {
            for (int j = i + 1; j < classes.size(); j++) {
                if (classes.get(i).isSameClass(classes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
