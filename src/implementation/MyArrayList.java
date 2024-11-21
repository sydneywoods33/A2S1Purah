package implementation;

import utilities.ListADT;
import utilities.Iterator;

import java.util.NoSuchElementException;

/**
 * This class represents an array-based list that supports basic list
 * operations, like adding, removing, retrieving, and setting elements.
 *
 * @param <E> The type of elements held in this list.
 */
public class MyArrayList<E> implements ListADT<E> {
    private E[] array;
    private int size;
    private static final int INITIAL_CAPACITY = 10;
    
    /**
     * Constructs an empty list with an initial capacity of 10 which has been declared in the variables.
     */
    public MyArrayList() {
        array = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }
    
    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Clears the list, removing all elements that were previously stored.
     */
    @Override
    public void clear() {
        array = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds an element at a specified index in the list.
     *
     * @param index The position to add the element.
     * @param itemToAdd The element to add.
     * @return true If the element is added successfully to the specified index.
     * @throws NullPointerException If the specified element is null.
     * @throws IndexOutOfBoundsException If the index is out of the range expected.
     */
    @Override
    public boolean add(int index, E itemToAdd) {
        if (itemToAdd == null) throw new NullPointerException("Can't add an element that is null to the list.");
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index out of bounds.");
        ensureCapacity();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = itemToAdd;
        size++;
        return true;
    }

    /**
     * Adds an element to the end of the list if it is not null.
     *
     * @param itemToAdd The element to be added.
     * @return true If the element is added successfully.
     * @throws NullPointerException If the specified element is null and cannot be added to the list.
     */
    @Override
    public boolean add(E itemToAdd) {
        if (itemToAdd == null) throw new NullPointerException("Can't add an element that is null to the list.");
        ensureCapacity();
        array[size++] = itemToAdd;
        return true;
    }

    /**
     * Adds all elements from the other list to this list.
     *
     * @param itemToAdd The list of elements that will be added.
     * @return true If the elements were added successfully to the list.
     * @throws NullPointerException If the specified collection is null.
     */
    @Override
    public boolean addAll(ListADT<? extends E> itemToAdd) {
        if (itemToAdd == null) throw new NullPointerException("The element's to be added is null.");
        for (Iterator<? extends E> it = itemToAdd.iterator(); it.hasNext(); ) {
            add(it.next());
        }
        return true;
    }

    /**
     * Gets an element from the specified index in the list.
     *
     * @param index This is the position of the element which will be retrieved. 
     * @return The element from the index which has been specified.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    @Override
    public E get(int index) {
        checkIndex(index);
        return array[index];
    }

    
    /**
     * Removes an element at a specified index in the list.
     *
     * @param index The position of the element that will be removed.
     * @return The removed element.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    @Override
    public E remove(int index) {
        checkIndex(index);
        E removedElement = array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
        return removedElement;
    }
    
    

    /**
     * Removes the first instance of a specified element from the list.
     *
     * @param elementToRemove The element to remove.
     * @return The removed element, or null if unable to find.
     * @throws NullPointerException If the specified element is null.
     */
    @Override
    public E remove(E elementToRemove) {
        if (elementToRemove == null) throw new NullPointerException("Can't remove null element from the list.");
        for (int i = 0; i < size; i++) {
            if (array[i].equals(elementToRemove)) {
                return remove(i);
            }
        }
        return null;
    }

    /**
     * Replaces an element at a specified index with a new element.
     *
     * @param index The position of the element to be replaced.
     * @param toChange This takes the new element to replace the old element.
     * @return The old element that was replaced.
     * @throws NullPointerException If the new element is null.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    @Override
    public E set(int index, E toChange) {
        checkIndex(index);
        if (toChange == null) throw new NullPointerException("Can't set null element inside the list.");
        E oldElement = array[index];
        array[index] = toChange;
        return oldElement;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true If the list is empty, false if not.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the list contains a specific element.
     *
     * @param toFind This will give us the element to search for.
     * @return true If the element is found, false if not.
     * @throws NullPointerException If the specified element is null.
     */
    @Override
    public boolean contains(E toFind) {
        if (toFind == null) throw new NullPointerException("Can't search for a null element inside the list.");
        for (int i = 0; i < size; i++) {
            if (array[i].equals(toFind)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts the list to an array.
     *
     * @param toHold The array to hold the elements, if large enough.
     * @return The array containing all the elements in the list.
     * @throws NullPointerException If the array is null.
     */
    @Override
    public E[] toArray(E[] toHold) {
        if (toHold == null) throw new NullPointerException("The array can't be null.");
        if (toHold.length < size) {
            return (E[]) java.util.Arrays.copyOf(array, size, toHold.getClass());
        }
        System.arraycopy(array, 0, toHold, 0, size);
        if (toHold.length > size) {
            toHold[size] = null;
        }
        return toHold;
    }

    /**
     * Converts the list to an array of Objects.
     *
     * @return An array with all elements in the list.
     */
    @Override
    public Object[] toArray() {
        return java.util.Arrays.copyOf(array, size);
    }

    /**
     * Returns an iterator over the elements in the list.
     *
     * @return An iterator over the elements in the list.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return array[current++];
            }
        };
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list,
     * or -1 if this list does not contain the element.
     *
     * @param element The element to search for.
     * @return The index of the first occurrence of the specified element, or -1 if not found.
     * @throws NullPointerException If the specified element is null.
     */
    public int indexOf(E element) {
        if (element == null) throw new NullPointerException("Can't search for a null element inside the list.");
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Ensures that the array has enough space to store additional elements.
     * If the current limit is reached, it will double.
     */
    private void ensureCapacity() {
        if (size == array.length) {
            int newCapacity = array.length * 2;
            array = java.util.Arrays.copyOf(array, newCapacity);
        }
    }

    /**
     * Checks if an index is valid.
     *
     * @param index The index to check.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds.");
    }
}
