import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableQueueTest {

    private class MyImmutableElement implements ImmutableElement {
        private final int value;

        private MyImmutableElement(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public ImmutableElement clone() {
            return new MyImmutableElement(value);
        }
    }

    @Test
    public void enQueue() {
        ImmutableQueue<MyImmutableElement> immutableQueue = new ImmutableQueue<>(2);

        // enQueue one element and assert the element is in the head of the queue
        ImmutableQueue<MyImmutableElement> immutableQueueWithOneElement =
                (ImmutableQueue<MyImmutableElement>) immutableQueue.enQueue(new MyImmutableElement(0));
        assertEquals(0, immutableQueueWithOneElement.head().getValue());

        // enQueue the second element
        ImmutableQueue<MyImmutableElement> immutableQueueWithTwoElement =
                (ImmutableQueue<MyImmutableElement>) immutableQueueWithOneElement.enQueue(new MyImmutableElement(1));
        // assert throw RuntimeException(QueueFullException) and exception message when try to enQueue the third element
        QueueFullException expectedQueueFullException =
                assertThrows(QueueFullException.class,
                        () -> immutableQueueWithTwoElement.enQueue(new MyImmutableElement(2)));
        assertEquals(
                "ImmutableQueue capacity exceed, can NOT add more element, capacity is 2",
                expectedQueueFullException.getMessage());
    }

    @Test
    public void deQueue() {
        // Make a ImmutableQueue with two elements
        ImmutableQueue<MyImmutableElement> immutableQueue = new ImmutableQueue<>(10);
        ImmutableQueue<MyImmutableElement> immutableQueueWithOneElement =
                (ImmutableQueue<MyImmutableElement>) immutableQueue.enQueue(new MyImmutableElement(0));
        ImmutableQueue<MyImmutableElement> immutableQueueWithTwoElement =
                (ImmutableQueue<MyImmutableElement>) immutableQueueWithOneElement.enQueue(new MyImmutableElement(1));

        // DeQueue the first element
        // assert the second element in original queue becomes the first element in the new queue
        ImmutableQueue<MyImmutableElement> immutableQueueDeQueueOneElement =
                (ImmutableQueue<MyImmutableElement>) immutableQueueWithTwoElement.deQueue();
        assertEquals(1, immutableQueueDeQueueOneElement.head().getValue());

        // DeQueue the first element
        // assert the new queue is empty
        ImmutableQueue<MyImmutableElement> immutableQueueDeQueueTwoElement =
                (ImmutableQueue<MyImmutableElement>) immutableQueueDeQueueOneElement.deQueue();
        assertTrue(immutableQueueDeQueueTwoElement.isEmpty());

        // DeQueue empty queue
        ImmutableQueue<MyImmutableElement> immutableQueueDeQueueEmptyQueue =
                (ImmutableQueue<MyImmutableElement>) immutableQueueDeQueueTwoElement.deQueue();
        assertTrue(immutableQueueDeQueueEmptyQueue.isEmpty());
    }

    @Test
    public void head() {
        // Make a empty queue
        // assert head() return null
        ImmutableQueue<MyImmutableElement> immutableQueue = new ImmutableQueue<>(10);
        assertNull(immutableQueue.head());

        // assert head() return the right element in a no empty queue
        ImmutableQueue<MyImmutableElement> immutableQueueWithOneElement =
                (ImmutableQueue<MyImmutableElement>) immutableQueue.enQueue(new MyImmutableElement(0));
        ImmutableQueue<MyImmutableElement> immutableQueueWithTwoElement =
                (ImmutableQueue<MyImmutableElement>) immutableQueueWithOneElement.enQueue(new MyImmutableElement(1));
        assertEquals(0, immutableQueueWithTwoElement.head().getValue());
    }

    @Test
    public void isEmpty() {
        // Make a empty queue
        // assert isEmpty() return true
        ImmutableQueue<MyImmutableElement> immutableQueue = new ImmutableQueue<>(10);
        assertTrue(immutableQueue.isEmpty());

        // assert isEmpty() return false for no empty queue
        ImmutableQueue<MyImmutableElement> immutableQueueWithOneElement =
                (ImmutableQueue<MyImmutableElement>) immutableQueue.enQueue(new MyImmutableElement(0));
        ImmutableQueue<MyImmutableElement> immutableQueueWithTwoElement =
                (ImmutableQueue<MyImmutableElement>) immutableQueueWithOneElement.enQueue(new MyImmutableElement(1));
        assertFalse(immutableQueueWithTwoElement.isEmpty());
    }
}
