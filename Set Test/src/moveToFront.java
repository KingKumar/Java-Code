import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;

    /**
     * Finds {@code x} in {@code q} and, if such exists, moves it to the front
     * of {@code q}.
     * 
     * @param <T>
     *            type of {@code Queue} entries
     * @param q
     *            the {@code Queue} to be searched
     * @param x
     *            the entry to be searched for
     * @updates {@code q}
     * @ensures <pre>
     * {@code perms(q, #q)  and
     * if <x> is substring of q
     *  then <x> is prefix of q}
     * </pre>
     */
    private static <T> void moveToFront(Queue<T> q, T x) {
        Queue<T> q2 = q.newInstance();
        int count = 0, qlength = q.length();
        while (count < qlength) {
            T sub = q.dequeue();
            count++;
            if (sub.equals(x)) {
                q2.enqueue(sub);
                while (q.length() != 0) {
                    sub = q.dequeue();
                    q2.enqueue(sub);
                }
                q.transferFrom(q2);
            } else {
                q.enqueue(sub);
            }
        }
    }