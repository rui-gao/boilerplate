import java.util.ArrayList;
import java.util.List;

public class ImmutableQueue<T extends ImmutableElement> implements Queue<T> {

    // capacity helps to avoid potential memory overflow when the ImmutableQueue been added to many elements
    private final long capacity;
    private final List<T> list;

    private ImmutableQueue(long capacity, ArrayList list) {
        this.capacity = capacity;
        this.list = list;
    }

    public ImmutableQueue(long capacity) {
        this.capacity = capacity;
        list = new ArrayList<>();
    }

    // Deep copy the original list to a new list, add the new element in the end of the new list and return the new list, instead of modify the original list.
    @Override
    public synchronized Queue<T>  enQueue(T t) {
        if (list.size() == capacity) {
            throw new QueueFullException(String.format("ImmutableQueue capacity exceed, can NOT add more element, capacity is %d", capacity));
        }
        // Deep copy the original list to a new list
        final ArrayList<T> newList = new ArrayList();
        list.forEach(element -> newList.add((T) element.clone()));

        // add the new element in the end of the new list
        newList.add(t);

        return new ImmutableQueue(capacity, newList);
    }

    // Removes the element at the beginning of the immutable queue, and returns the new queue.
    @Override
    public synchronized Queue<T> deQueue() {
        if (list.size() == 0) {
            return new ImmutableQueue(0l);
        }

        // Deep copy the original list to a new list
        final ArrayList<T> newList = new ArrayList();
        list.forEach(element -> newList.add((T) element.clone()));

        // remove the first element in the beginning of the new list
        newList.remove(0);

        return new ImmutableQueue(capacity, newList);
    }

    @Override
    public synchronized T head() {
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public synchronized boolean isEmpty() {
        return list.size() == 0;
    }
}
