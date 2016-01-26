import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import components.queue.Queue;
import components.queue.Queue1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachineSecondary;

/**
 * {@code SortingMachine} represented as a Queue (kept ordered by insertion
 * sort), with implementations of primary methods.
 * 
 * @param <T>
 *            type of {@code SortingMachine} entries
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
 * @convention <pre>
 * {@code IS_TOTAL_PREORDER([relation computed by $this.machineOrder.compare method])  and
 * IS_SORTED($this.entries, [relation computed by $this.machineOrder.compare method])}
 * </pre>
 * @correspondence <pre>
 * {@code this = ($this.insertionMode, $this.machineOrder, entries($this.entries))}
 * </pre>
 */
public class SortingMachine3<T> extends SortingMachineSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Insertion mode.
     */
    private boolean insertionMode;

    /**
     * Order.
     */
    private Comparator<T> machineOrder;

    /**
     * Entries.
     */
    private Queue<T> entries;

    /**
     * Creator of initial representation.
     * 
     * @param order
     *            total preorder for sorting
     */
    private void createNewRep(Comparator<T> order) {
        this.insertionMode = true;
        this.machineOrder = order;
        this.entries = new Queue1L<T>();
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

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Constructor from order.
     * 
     * @param order
     *            total preorder for sorting
     */
    public SortingMachine3(Comparator<T> order) {
        this.createNewRep(order);
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final SortingMachine<T> newInstance() {
        try {
            Constructor<?> c = this.getClass().getConstructor(Comparator.class);
            return (SortingMachine<T>) c.newInstance(this.machineOrder);
        } catch (NoSuchMethodException e) { // ReflectiveOperationException in
                                            // Java 7
            throw new AssertionError("Cannot construct object of type "
                    + this.getClass());
        } catch (InstantiationException e) {
            throw new AssertionError("Cannot construct object of type "
                    + this.getClass());
        } catch (IllegalAccessException e) {
            throw new AssertionError("Cannot construct object of type "
                    + this.getClass());
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Cannot construct object of type "
                    + this.getClass());
        } catch (InvocationTargetException e) {
            throw new AssertionError("Cannot construct object of type "
                    + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep(this.machineOrder);
    }

    @Override
    public final void transferFrom(SortingMachine<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof SortingMachine3<?> : ""
                + "Violation of: source is of dynamic type SortingMachine3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type
         * SortingMachine3<?>, and the ? must be T or the call would not have
         * compiled.
         */
        SortingMachine3<T> localSource = (SortingMachine3<T>) source;
        this.insertionMode = localSource.insertionMode;
        this.machineOrder = localSource.machineOrder;
        this.entries = localSource.entries;
        localSource.createNewRep(localSource.machineOrder);
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.isInInsertionMode() : "Violation of: this.insertion_mode";

        insertInOrder(this.entries, x, this.machineOrder);

    }

    @Override
    public final void changeToExtractionMode() {
        assert this.isInInsertionMode() : "Violation of: this.insertion_mode";

        this.insertionMode = false;

    }

    @Override
    public final T removeFirst() {
        assert !this.isInInsertionMode() : "Violation of: not this.insertion_mode";
        assert this.size() > 0 : "Violation of: this.contents /= {}";

        return this.entries.dequeue();
    }

    @Override
    public final boolean isInInsertionMode() {

        return this.insertionMode;
    }

    @Override
    public final Comparator<T> order() {

        return this.machineOrder;
    }

    @Override
    public final int size() {

        return this.entries.length();
    }

    @Override
    public final Iterator<T> iterator() {
        return new SortingMachine3Iterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code SortingMachine3}.
     */
    private final class SortingMachine3Iterator implements Iterator<T> {

        /**
         * Representation iterator.
         */
        private final Iterator<T> iterator;

        /**
         * Default constructor.
         */
        private SortingMachine3Iterator() {
            this.iterator = SortingMachine3.this.entries.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public T next() {
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
