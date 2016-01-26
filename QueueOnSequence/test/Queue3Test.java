import components.queue.Queue;

/**
 * Customized JUnit test fixture for {@code Queue3}.
 */
public class Queue3Test extends QueueTest {

    @Override
    protected final Queue<String> constructor() {
        return new Queue3<String>();
    }

}
