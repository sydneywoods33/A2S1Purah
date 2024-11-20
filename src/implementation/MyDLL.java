package implementation;

import utilities.ListADT;
import utilities.Iterator;

import java.util.NoSuchElementException;


/**
 * This class represents a doubly linked list that allows you to store and work with elements.
 * It supports basic list operations like add, remove, get, set, and iteration.
 * 
 * @param <E> The type of elements held in this list.
 */
public class MyDLL<E> implements ListADT<E> {
    private MyDLLNode<E> head;
    private MyDLLNode<E> tail;
    private int size;

    
    /**
     * Creates an empty doubly linked list.
     */
    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }

    
    /**
     * Returns the number of elements in the list.
     *
     * @return The number of elements in the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Here we will clear the list by removing all elements.
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    
    /**
     * Adds a new element at a specified index in the list.
     * This will shift everything at and after the index to make space for the new element.
     * 
     * @param index The position to be inserted at. Must be within the bounds.
     * @param itemToAdd The element to add which cannot be null.
     * @return true If the element is added successfully.
     * @throws NullPointerException If the itemToAdd is null.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Override
    public boolean add(int index, E itemToAdd) {
        if (itemToAdd == null) throw new NullPointerException("Can't add null element.");
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index out of bounds.");
        
        MyDLLNode<E> newNode = new MyDLLNode<>(itemToAdd);
        if (index == 0) {
            newNode.next = head;
            if (head != null) head.prev = newNode;
            head = newNode;
            if (size == 0) tail = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            MyDLLNode<E> current = getNode(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
        return true;
    }

    
    /**
     * Adds an element to the end of the list.
     * 
     * @param itemToAdd The element to add.
     * @return true If the element is added successfully.
     * @throws NullPointerException If the specified element is null.
     */
    @Override
    public boolean add(E itemToAdd) {
        if (itemToAdd == null) throw new NullPointerException("Can't add null element.");
        MyDLLNode<E> newNode = new MyDLLNode<>(itemToAdd);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        return true;
    }

    
    /**
     * Adds all elements from another list to this list.
     * 
     * @param itemToAdd The list of elements to add.
     * @return true If the elements are added successfully.
     * @throws NullPointerException If the list is null.
     */
    @Override
    public boolean addAll(ListADT<? extends E> itemToAdd) {
        if (itemToAdd == null) throw new NullPointerException("List to add is null.");
        for (Iterator<? extends E> it = itemToAdd.iterator(); it.hasNext(); ) {
            add(it.next());
        }
        return true;
    }

    
    /**
     * Gets an element at a specified index in the list.
     * 
     * @param index The position of the element to retrieve.
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    @Override
    public E get(int index) {
        checkIndex(index);
        return getNode(index).data;
    }

    
    /**
     * Removes an element at a specific index in the list.
     * 
     * @param index The position of the element to remove.
     * @return The removed element.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    @Override
    public E remove(int index) {
        checkIndex(index);
        MyDLLNode<E> nodeToRemove = getNode(index);
        if (nodeToRemove.prev != null) {
            nodeToRemove.prev.next = nodeToRemove.next;
        } else {
            head = nodeToRemove.next;
        }
        if (nodeToRemove.next != null) {
            nodeToRemove.next.prev = nodeToRemove.prev;
        } else {
            tail = nodeToRemove.prev;
        }
        size--;
        return nodeToRemove.data;
    }
    
    /**
     * Removes the first occurrence of a specified element from the list.
     * 
     * @param elementToRemove The element to be removed.
     * @return The removed element, or null if not found.
     * @throws NullPointerException if the specified element is null.
     */
    @Override
    public E remove(E elementToRemove) {
        if (elementToRemove == null) throw new NullPointerException("Cannot remove null element.");
        MyDLLNode<E> current = head;
        while (current != null) {
            if (current.data.equals(elementToRemove)) {
                E data = current.data;
                if (current.prev != null) current.prev.next = current.next;
                else head = current.next;
                if (current.next != null) current.next.prev = current.prev;
                else tail = current.prev;
                size--;
                return data;
            }
            current = current.next;
        }
        return null;
    }
    
    

    /**
     * Replaces an element at a specific index with a new element.
     * 
     * @param index The position of the element to replace.
     * @param toChange The new element to replace the old element.
     * @return The old element that was replaced.
     * @throws NullPointerException If the new element is null.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    @Override
    public E set(int index, E toChange) {
        checkIndex(index);
        if (toChange == null) throw new NullPointerException("Can't set null element.");
        MyDLLNode<E> node = getNode(index);
        E oldData = node.data;
        node.data = toChange;
        return oldData;
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
     * Checks if the list contains a specified element.
     * 
     * @param toFind The element to search for.
     * @return true If the element is found, false if not.
     * @throws NullPointerException If the specified element is null.
     */
    @Override
    public boolean contains(E toFind) {
        if (toFind == null) throw new NullPointerException("Can't search for a null element.");
        MyDLLNode<E> current = head;
        while (current != null) {
            if (current.data.equals(toFind)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    
    /**
     * Converts the list to an array.
     * 
     * @param toHold The array to hold the elements, if large enough.
     * @return An array with all elements in the list.
     * @throws NullPointerException If the array is null.
     */
    @Override
    public E[] toArray(E[] toHold) {
        if (toHold == null) throw new NullPointerException("Array can't be null.");
        if (toHold.length < size) {
            toHold = (E[]) java.util.Arrays.copyOf(toHold, size, toHold.getClass());
        }
        MyDLLNode<E> current = head;
        for (int i = 0; i < size; i++) {
            toHold[i] = current.data;
            current = current.next;
        }
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
        Object[] array = new Object[size];
        MyDLLNode<E> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.data;
            current = current.next;
        }
        return array;
    }

    
    /**
     * Returns an iterator over the elements in the list.
     * 
     * @return An iterator over the elements in the list.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private MyDLLNode<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    
    /**
     * Gets the node at a specific index.
     * 
     * @param index The index of the node to get.
     * @return The node at the given index.
     */
    private MyDLLNode<E> getNode(int index) {
        MyDLLNode<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
    

    /**
     * Checks if an index is within the bounds of the list.
     * 
     * @param index the index to check
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds.");
    }
}
