package utilities;

/**
 * <p>
 * The <code>StackADT</code> interface defines the standard operations for a stack data structure.
 * A stack follows a Last-In-First-Out (LIFO) ordering of elements.
 * </p>
 *
 * @param <E> The type of elements in this stack.
 */
public interface StackADT<E> {

    /**
     * Adds an element to the top of the stack.
     * 
     * Preconditions: The stack is not full (if capacity is limited).
     * Postconditions: The element is added to the top of the stack.
     * 
     * @param element The element to be added to the stack.
     * @throws NullPointerException if the element is null.
     */
    public void push(E element) throws NullPointerException;

    /**
     * Removes and returns the element at the top of the stack.
     * 
     * Preconditions: The stack is not empty.
     * Postconditions: The top element is removed from the stack.
     * 
     * @return The element removed from the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    public E pop() throws NoSuchElementException;

    /**
     * Returns the element at the top of the stack without removing it.
     * 
     * Preconditions: The stack is not empty.
     * Postconditions: The stack remains unchanged.
     * 
     * @return The element at the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    public E peek() throws NoSuchElementException;

    /**
     * Returns true if the stack contains no elements.
     * 
     * Preconditions: None.
     * Postconditions: The stack remains unchanged.
     * 
     * @return <code>true</code> if the stack is empty, <code>false</code> otherwise.
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in the stack.
     * 
     * Preconditions: None.
     * Postconditions: The stack remains unchanged.
     * 
     * @return The number of elements in the stack.
     */
    public int size();
}
