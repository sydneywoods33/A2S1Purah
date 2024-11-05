package utilities;

/**
 * <p>
 * The <code>QueueADT</code> interface defines the standard operations for a queue data structure.
 * A queue follows a First-In-First-Out (FIFO) ordering of elements.
 * </p>
 *
 * @param <E> The type of elements in this queue.
 */
public interface QueueADT<E> {

    /**
     * Adds an element to the end of the queue.
     * 
     * Preconditions: The queue is not full (if capacity is limited).
     * Postconditions: The element is added to the end of the queue.
     * 
     * @param element The element to be added to the queue.
     * @throws NullPointerException if the element is null.
     */
    public void enqueue(E element) throws NullPointerException;

    /**
     * Removes and returns the element at the front of the queue.
     * 
     * Preconditions: The queue is not empty.
     * Postconditions: The front element is removed from the queue.
     * 
     * @return The element removed from the front of the queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    public E dequeue() throws NoSuchElementException;

    /**
     * Returns the element at the front of the queue without removing it.
     * 
     * Preconditions: The queue is not empty.
     * Postconditions: The queue remains unchanged.
     * 
     * @return The element at the front of the queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    public E peek() throws NoSuchElementException;

    /**
     * Returns true if the queue contains no elements.
     * 
     * Preconditions: None.
     * Postconditions: The queue remains unchanged.
     * 
     * @return <code>true</code> if the queue is empty, <code>false</code> otherwise.
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in the queue.
     * 
     * Preconditions: None.
     * Postconditions: The queue remains unchanged.
     * 
     * @return The number of elements in the queue.
     */
    public int size();
}
