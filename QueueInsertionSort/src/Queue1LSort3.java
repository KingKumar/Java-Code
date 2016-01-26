import java.util.Comparator;

import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Layered implementations of secondary method {@code sort} for
 * {@code Queue<String>}.
 * 
 * @param <T>
 *            type of {@code Queue} entries
 * @mathdefinitions <pre>
 * {@code IS_TOTAL_PREORDER (
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y, z: T
 *   ((r(x, y) or r(y, x))  and
 *    (if (r(x, y) and r(y, z)) then r(x, z)))
 * 
 * IS_SORTED (
 *   s: string of T,
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y: T where (<x, y> is substring of s) (r(x, y))}
 * </pre>
 */
public final class Queue1LSort3<T> extends Queue1L<T> {

    /**
     * Default constructor.
     */
    public Queue1LSort3() {
        super();
    }

    /**
     * Inserts the given {@code T} in the {@code Queue<T>} sorted according to
     * the given {@code Comparator<T>} and maintains the {@code Queue<T>}
     * sorted.
     * 
     * @param <T>
     *            type of {@code Queue} entries
     * @param q
     *            the {@code Queue} to insert into
     * @param x
     *            the {@code T} to insert
     * @param order
     *            the {@code Comparator} defining the order for {@code T}
     * @updates q
     * @requires <pre>
     * {@code IS_TOTAL_PREORDER([relation computed by order.compare method])  and
     * IS_SORTED(q, [relation computed by order.compare method])}
     * </pre>
     * @ensures <pre>
     * {@code perms(q, #q * <x>)  and
     * IS_SORTED(q, [relation computed by order.compare method])}
     * </pre>
     */
    private static <T> void insertInOrder(Queue<T> q, T x, Comparator<T> order) {
        assert q != null : "Violation of: q is not null";
        assert order != null : "Violation of: order is not null";

        Queue<T> q2 = new Queue1L<T>();
        int count = 0;
        int length = q.length();
        boolean added = false;
        while (count < length && added == false) {
            T y = q.dequeue();
            if (order.compare(x, y) <= 0) {
                q2.enqueue(x);
                q2.enqueue(y);
                added = true;
            } else {
                q2.enqueue(y);
            }
            count++;
        }
        q2.append(q);
        if (q2.length() == length) {
            q2.enqueue(x);
        }
        q.transferFrom(q2);
    }

    @Override
    public void sort(Comparator<T> order) {
        assert order != null : "Violation of: order is not null";

        Queue<T> q2 = new Queue1L<T>();
        int count = 0;
        int length = this.length();
        while (count < length) {
            T y = this.dequeue();
            insertInOrder(q2, y, order);
            count++;
        }
        this.transferFrom(q2);

    }

    private static <T> void partition(Queue<T> q, T partitioner,
            Queue<T> front, Queue<T> back, Comparator<T> order) {
        while (q.length() != 0) {
            T temp = q.dequeue();
            if (order.compare(temp, partitioner) <= 0) {
                front.enqueue(temp);
            } else {
                back.enqueue(temp);
            }
        }
    }

    public void sort(Comparator<T> order) {
        if (this.length() > 1) {
            /*
             * Dequeue the partitioning entry from this
             */
            T partitioner = this.dequeue();

            /*
             * Partition this into two queues as discussed above (you will need
             * to declare and initialize two new queues)
             */
            Queue<T> front = this.newInstance();
            Queue<T> back = this.newInstance();

            partition(this, partitioner, front, back, order);
            /*
             * Recursively sort the two queues
             */
            front.sort(order);
            back.sort(order);
            /*
             * Reconstruct this by combining the two sorted queues and the
             * partitioning entry in the proper order
             */
            this.append(front);
            this.append(back);

        }
    }

}
