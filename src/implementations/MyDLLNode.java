package implementations;

public class MyDLLNode<E> {
    E data;
    MyDLLNode<E> next;
    MyDLLNode<E> prev;

    public MyDLLNode(E data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
