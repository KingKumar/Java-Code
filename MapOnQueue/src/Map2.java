import java.util.Iterator;
import java.util.NoSuchElementException;

import components.map.Map;
import components.map.MapSecondary;
import components.queue.Queue;
import components.queue.Queue1L;

/**
 * {@code Map} represented as a {@code Queue} of pairs with implementations of
 * primary methods.
 * 
 * @param <K>
 *            type of {@code Map} domain (key) entries
 * @param <V>
 *            type of {@code Map} range (associated value) entries
 * @convention <pre>
 * {@code for all key1, key2: K, value1, value2: V
 *     where (<(key1, value1)> is substring of $this.pairsQueue  and
 *            <(key2, value2)> is substring of $this.pairsQueue)
 *   (key1 /= key2)}
 * </pre>
 * @correspondence <pre>
 * {@code this = entries($this.pairsQueue)}
 * </pre>
 */
public class Map2<K, V> extends MapSecondary<K, V> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Pairs included in {@code this}.
     */
    private Queue<Pair<K, V>> pairsQueue;

    /**
     * Finds pair with first component {@code key} and, if such exists, moves it
     * to the front of {@code q}.
     * 
     * @param <K>
     *            type of {@code Pair} key
     * @param <V>
     *            type of {@code Pair} value
     * @param q
     *            the {@code Queue} to be searched
     * @param key
     *            the key to be searched for
     * @updates {@code q}
     * @ensures <pre>
     * {@code perms(q, #q)  and
     * if there exists value: V (<(key, value)> is substring of q)
     *  then there exists value: V (<(key, value)> is prefix of q)}
     * </pre>
     */
    private static <K, V> void moveToFront(Queue<Pair<K, V>> q, K key) {
        assert q != null : "Violation of: q is not null";
        assert key != null : "Violation of: key is not null";
        Queue<Pair<K, V>> q2 = q.newInstance();
        int count = 0, qlength = q.length();
        while (count < qlength) {
            Pair<K, V> sub = new SimplePair<K, V>(key, null);
            sub = q.dequeue();
            count++;
            if (sub.key().equals(key)) {
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

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.pairsQueue = new Queue1L<Pair<K, V>>();
    }

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public Map2() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Map<K, V> newInstance() {
        try {
            return this.getClass().newInstance();
        } catch (Exception e) { // ReflectiveOperationException in Java 7
            throw new AssertionError("Cannot construct object of type "
                    + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Map<K, V> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Map2<?, ?> : ""
                + "Violation of: source is of dynamic type Map2<?,?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Map2<?,?>, and
         * the ?,? must be K,V or the call would not have compiled.
         */
        Map2<K, V> localSource = (Map2<K, V>) source;
        this.pairsQueue = localSource.pairsQueue;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(K key, V value) {
        assert key != null : "Violation of: key is not null";
        assert value != null : "Violation of: value is not null";
        assert !this.hasKey(key) : "Violation of: key is not in DOMAIN(this)";

        Pair<K, V> x = new SimplePair<K, V>(key, value);
        this.pairsQueue.enqueue(x);
    }

    @Override
    public final Pair<K, V> remove(K key) {
        assert key != null : "Violation of: key is not null";
        assert this.hasKey(key) : "Violation of: key is in DOMAIN(this)";

        moveToFront(this.pairsQueue, key);
        return this.pairsQueue.dequeue();
    }

    @Override
    public final V value(K key) {
        assert key != null : "Violation of: key is not null";
        assert this.hasKey(key) : "Violation of: key is in DOMAIN(this)";

        moveToFront(this.pairsQueue, key);
        return this.pairsQueue.front().value();
    }

    @Override
    public final boolean hasKey(K key) {
        assert key != null : "Violation of: key is not null";
        if (this.pairsQueue.length() >= 1) {
            moveToFront(this.pairsQueue, key);
            return this.pairsQueue.front().key().equals(key);
        } else {
            return false;
        }
    }

    @Override
    public final int size() {
        return this.pairsQueue.length();
    }

    @Override
    public final Iterator<Pair<K, V>> iterator() {
        return new Map2Iterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Map2}.
     */
    private final class Map2Iterator implements Iterator<Pair<K, V>> {

        /**
         * Representation iterator.
         */
        private final Iterator<Pair<K, V>> iterator;

        /**
         * Default constructor.
         */
        public Map2Iterator() {
            this.iterator = Map2.this.pairsQueue.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public Pair<K, V> next() {
            assert this.hasNext() : "Violation of: ~this.unseen /= <>";
            if (!this.hasNext()) {
                /*
                 * Exception is supposed to be thrown in this case, but with
                 * assertion-checking enabled it cannot happen because of assert
                 * above.
                 */
                throw new NoSuchElementException();
            }
            return this.iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }

    }

}
