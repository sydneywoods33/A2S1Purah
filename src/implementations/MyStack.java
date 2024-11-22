package implementations;

import java.util.EmptyStackException;
import utilities.Iterator;
import utilities.StackADT;

@SuppressWarnings("serial")
public class MyStack<E> implements StackADT<E> {
    private MyArrayList<E> list;

    public MyStack() {
        list = new MyArrayList<>();
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot add null element.");
        list.add(toAdd);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return list.remove(list.size() - 1);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return list.get(list.size() - 1);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        return list.toArray(holder);
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return list.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        int index = list.indexOf(toFind);
        return index == -1 ? -1 : list.size() - index;
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null || this.size() != that.size()) return false;
        Iterator<E> thisIterator = this.iterator();
        Iterator<E> thatIterator = that.iterator();
        while (thisIterator.hasNext()) {
            if (!thisIterator.next().equals(thatIterator.next())) return false;
        }
        return true;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean stackOverflow() {
        return false;
    }
}
