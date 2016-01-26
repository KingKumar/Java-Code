import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import components.array.Array;
import components.array.Array1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachineSecondary;

/**
 * 
 * @author Ronit Kumar
 * 
 * 
 *         {@code SortingMachine} represented as a {@code Queue} and an
 *         {@code Array} (ordered by heap sort), with implementations of primary
 *         methods.
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
 * SUBTREE_IS_HEAP (
 *   a: ARRAY_MODEL,
 *   start: integer,
 *   stop: integer,
 *   r: binary relation on T
 *  ) : boolean is
 *  [the subtree of a (when a is interpreted as a complete binary tree) rooted
 *   at index start and only through entry stop of a satisfies the heap
 *   ordering property according to the relation r]
 * 
 * SUBTREE_ARRAY_ENTRIES (
 *   a: ARRAY_MODEL,
 *   start: integer,
 *   stop: integer
 *  ) : finite multiset of T is
 *  [the multiset of entries in a that belong to the subtree of a
 *   (when a is interpreted as a complete binary tree) rooted at
 *   index start and only through entry stop]}
 * </pre>
 * @convention <pre>
 * {@code IS_TOTAL_PREORDER([relation computed by $this.machineOrder.compare method]  and
 * if $this.insertionMode then
 *   $this.heapSize = 0
 * else
 *   $this.entries = <>  and
 *   |$this.heap.examinableIndices| = |$this.heap.entries|  and
 *   SUBTREE_IS_HEAP($this.heap, 0, $this.heapSize - 1,
 *     [relation computed by $this.machineOrder.compare method])  and
 *   0 <= $this.heapSize <= |$this.heap.entries|}
 * </pre>
 * @correspondence <pre>
 * {@code if $this.insertionMode then
 *   this = (true, $this.machineOrder, multiset_entries($this.entries))
 * else
 *   this = (false, $this.machineOrder,
 *     multiset_entries($this.heap.entries[0, $this.heapSize)))}
 * </pre>
 */
public class SortingMachine5<T> extends SortingMachineSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Order.
     */
    private Comparator<T> machineOrder;

    /**
     * Insertion mode.
     */
    private boolean insertionMode;

    /**
     * Entries.
     */
    private Queue<T> entries;

    /**
     * Heap.
     */
    private Array<T> heap;

    /**
     * Heap size.
     */
    private int heapSize;

    /**
     * Given an {@code Array} that represents a complete binary tree and an
     * index referring to the root of a subtree that would be a heap except for
     * its root, sifts the root down to turn that whole subtree into a heap.
     * 
     * @param <T>
     *            type of array entries
     * @param array
     *            the complete binary tree
     * @param top
     *            the index of the root of the "subtree"
     * @param last
     *            the index of the last entry in the heap
     * @param order
     *            total preorder for sorting
     * @updates {@code array.entries}
     * @requires <pre>
     * {@code 0 <= top  and  last < |array.entries|  and
     * |array.examinableIndices| = |array.entries|  and
     * [subtree rooted at {@code top} is a complete binary tree]  and
     * SUBTREE_IS_HEAP(array, 2 * top + 1, last,
     *     [relation computed by order.compare method])  and
     * SUBTREE_IS_HEAP(array, 2 * top + 2, last,
     *     [relation computed by order.compare method])  and
     * IS_TOTAL_PREORDER([relation computed by order.compare method])}
     * </pre>
     * @ensures <pre>
     * {@code SUBTREE_IS_HEAP(array, top, last,
     *     [relation computed by order.compare method])  and
     * perms(array.entries, #array.entries)  and
     * SUBTREE_ARRAY_ENTRIES(array, top, last) =
     *  SUBTREE_ARRAY_ENTRIES(#array, top, last)  and
     * [the other entries in array.entries are the same as in #array.entries]}
     * </pre>
     */
    private static <T> void siftDown(Array<T> array, int top, int last,
            Comparator<T> order) {
        assert array != null : "Violation of: array is not null";
        assert order != null : "Violation of: order is not null";
        assert 0 <= top : "Violation of: 0 <= top";
        assert last < array.length() : "Violation of: last < |array.entries|";
        for (int i = 0; i < array.length(); i++) {
            assert array.mayBeExaminedAt(i) : ""
                    + "Violation of: |array.examinableIndices| = |array.entries|";
        }
        assert isHeap(array, 2 * top + 1, last, order) : ""
                + "Violation of: SUBTREE_IS_HEAP(array, 2 * top + 1, last,"
                + " [relation computed by order.compare method])";
        assert isHeap(array, 2 * top + 2, last, order) : ""
                + "Violation of: SUBTREE_IS_HEAP(array, 2 * top + 2, last,"
                + " [relation computed by order.compare method])";
        /*
         * Impractical to check last requires clause; no need to check the other
         * requires clause, because it must be true when using the Array
         * representation for a complete binary tree.
         */

        int leftchild = 2 * top + 1;
        int rightchild = 2 * top + 2;
        if (rightchild <= last) {
            T first = array.entryAt(top);
            T ch1ld = array.entryAt(2 * top + 1);
            T ch2ld = array.entryAt(2 * top + 2);
            if (order.compare(ch1ld, ch2ld) <= 0
                    && order.compare(first, ch1ld) > 0) {
                array.exchangeEntriesAt(top, 2 * top + 1);
                siftDown(array, 2 * top + 1, last, order);
            }
            if (order.compare(ch2ld, ch1ld) <= 0
                    && order.compare(first, ch2ld) > 0) {
                array.exchangeEntriesAt(top, 2 * top + 2);
                siftDown(array, 2 * top + 2, last, order);
            }
        } else if (leftchild <= last && rightchild > last) {
            T first = array.entryAt(top);
            T ch1ld = array.entryAt(2 * top + 1);
            if (order.compare(first, ch1ld) > 0) {
                array.exchangeEntriesAt(top, 2 * top + 1);
                siftDown(array, 2 * top + 1, last, order);
            }
        }
    }

    /**
     * Heapifies the subtree of the given {@code Array} rooted at the given
     * {@code top}.
     * 
     * @param <T>
     *            type of {@code Array} entries
     * @param array
     *            the {@code Array} to be turned into a heap
     * @param top
     *            the index of the root of the "subtree" to heapify
     * @param order
     *            the total preorder for sorting
     * @updates {@code array.entries}
     * @requires <pre>
     * {@code 0 <= top  and
     * |array.examinableIndices| = |array.entries|  and
     * [subtree rooted at {@code top} is a complete binary tree]  and
     * IS_TOTAL_PREORDER([relation computed by order.compare method])}
     * </pre>
     * @ensures <pre>
     * {@code SUBTREE_IS_HEAP(array, top, |array.entries| - 1,
     *     [relation computed by order.compare method])  and
     * perms(array.entries, #array.entries)}
     * </pre>
     */
    private static <T> void heapify(Array<T> array, int top, Comparator<T> order) {
        assert array != null : "Violation of: array is not null";
        assert order != null : "Violation of: order is not null";
        assert 0 <= top : "Violation of: 0 <= top";
        for (int i = 0; i < array.length(); i++) {
            assert array.mayBeExaminedAt(i) : ""
                    + "Violation of: |array.examinableIndices| = |array.entries|";
        }
        /*
         * Impractical to check last requires clause; no need to check the other
         * requires clause, because it must be true when using the Array
         * representation for a complete binary tree.
         */

        int left = 2 * top + 1;
        int right = 2 * top + 2;
        if (right < array.length()) {
            heapify(array, left, order);
            heapify(array, right, order);
        } else if (left < array.length()) {
            heapify(array, left, order);
        }
        siftDown(array, top, array.length() - 1, order);
    }

    /**
     * Constructs and returns an {@code Array} representing a heap with the
     * entries from the given {@code Queue}.
     * 
     * @param <T>
     *            type of {@code Queue} and {@code Array} entries
     * @param q
     *            the {@code Queue} with the entries for the heap
     * @param order
     *            the total preorder for sorting
     * @return the {@code Array} representation of a heap
     * @clears {@code q}
     * @requires <pre>
     * {@code IS_TOTAL_PREORDER([relation computed by order.compare method])}
     * </pre>
     * @ensures <pre>
     * {@code SUBTREE_IS_HEAP(buildHeap, 0, |buildHeap.entries| - 1)  and
     * perms(buildHeap.entries, #q)  and
     * |buildHeap.examinableIndices| = |buildHeap.entries|}
     * </pre>
     */
    private static <T> Array<T> buildHeap(Queue<T> q, Comparator<T> order) {
        assert q != null : "Violation of: q is not null";
        assert order != null : "Violation of: order is not null";

        Array1L<T> heap = new Array1L<T>(q.length());
        for (int index = 0; index < q.length(); index++) {
            heap.replaceEntryAt(index, q.dequeue());
        }
        heapify(heap, 0, order);
        return heap;
    }

    /**
     * Checks if the subtree of the given {@code Array} rooted at the given
     * {@code top} is a heap.
     * 
     * @param <T>
     *            type of {@code Array} entries
     * @param array
     *            the complete binary tree
     * @param top
     *            the index of the root of the "subtree"
     * @param last
     *            the index of the last entry in the heap
     * @param order
     *            total preorder for sorting
     * @return true if the subtree of the given {@code Array} rooted at the
     *         given {@code top} is a heap; false otherwise
     * @requires <pre>
     * {@code 0 <= top  and  last < |array.entries|  and
     * |array.examinableIndices| = |array.entries|  and
     * [subtree rooted at {@code top} is a complete binary tree]}
     * </pre>
     * @ensures <pre>
     * {@code isHeap = SUBTREE_IS_HEAP(heap, top, last,
     *     [relation computed by order.compare method])}
     * </pre>
     */
    private static <T> boolean isHeap(Array<T> array, int top, int last,
            Comparator<T> order) {
        assert array != null : "Violation of: array is not null";
        assert 0 <= top : "Violation of: 0 <= top";
        assert last < array.length() : "Violation of: last < |array.entries|";
        for (int i = 0; i < array.length(); i++) {
            assert array.mayBeExaminedAt(i) : ""
                    + "Violation of: |array.examinableIndices| = |array.entries|";
        }
        /*
         * No need to check the other requires clause, because it must be true
         * when using the Array representation for a complete binary tree.
         */
        int left = 2 * top + 1;
        boolean isHeap = true;
        if (left <= last) {
            isHeap = (order.compare(array.entryAt(top), array.entryAt(left)) <= 0)
                    && isHeap(array, left, last, order);
            int right = left + 1;
            if (isHeap && (right <= last)) {
                isHeap = (order.compare(array.entryAt(top),
                        array.entryAt(right)) <= 0)
                        && isHeap(array, right, last, order);
            }
        }
        return isHeap;
    }

    /**
     * Checks that the part of the convention repeated below holds for the
     * current representation.
     * 
     * @return true if the convention holds (or if assertion checking is off);
     *         otherwise reports a violated assertion
     * @convention <pre>
     * {@code
     * if $this.insertionMode then
     *   $this.heapSize = 0
     * else
     *   $this.entries = <>  and
     *   |$this.heap.examinableIndices| = |$this.heap.entries|  and
     *   SUBTREE_IS_HEAP($this.heap, 0, $this.heapSize - 1,
     *     [relation computed by $this.machineOrder.compare method])  and
     *   0 <= $this.heapSize <= |$this.heap.entries|}
     * </pre>
     */
    private boolean conventionHolds() {
        if (this.insertionMode) {
            assert this.heapSize == 0 : ""
                    + "Violation of: if $this.insertionMode then $this.heapSize = 0";
        } else {
            assert this.entries.length() == 0 : ""
                    + "Violation of: if not $this.insertionMode then $this.entries = <>";
            assert 0 <= this.heapSize : ""
                    + "Violation of: if not $this.insertionMode then 0 <= $this.heapSize";
            assert this.heapSize <= this.heap.length() : ""
                    + "Violation of: if not $this.insertionMode then"
                    + " $this.heapSize <= |$this.heap.entries|";
            for (int i = 0; i < this.heap.length(); i++) {
                assert this.heap.mayBeExaminedAt(i) : ""
                        + "Violation of: if not $this.insertionMode then"
                        + " |$this.heap.examinableIndices| = |$this.heap.entries|";
            }
            assert isHeap(this.heap, 0, this.heapSize - 1, this.machineOrder) : ""
                    + "Violation of: if not $this.insertionMode then"
                    + " SUBTREE_IS_HEAP($this.heap, 0, $this.heapSize - 1,"
                    + " [relation computed by $this.machineOrder.compare method])";
        }
        return true;
    }

    /**
     * Creator of initial representation.
     * 
     * @param order
     *            total preorder for sorting
     */
    private void createNewRep(Comparator<T> order) {

        this.machineOrder = order;
        this.insertionMode = true;
        this.entries = new Queue1L<T>();
        this.heapSize = 0;
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
    public SortingMachine5(Comparator<T> order) {
        this.createNewRep(order);
        assert this.conventionHolds();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final SortingMachine<T> newInstance() {
        try {
            Constructor<?> c = this.getClass().getConstructor(Comparator.class);
            assert this.conventionHolds();
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
        assert this.conventionHolds();
    }

    @Override
    public final void transferFrom(SortingMachine<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof SortingMachine5<?> : ""
                + "Violation of: source is of dynamic type SortingMachine5<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type
         * SortingMachine5<?>, and the ? must be T or the call would not have
         * compiled.
         */
        SortingMachine5<T> localSource = (SortingMachine5<T>) source;
        this.insertionMode = localSource.insertionMode;
        this.machineOrder = localSource.machineOrder;
        this.entries = localSource.entries;
        this.heap = localSource.heap;
        this.heapSize = localSource.heapSize;
        localSource.createNewRep(localSource.machineOrder);
        assert this.conventionHolds();
        assert localSource.conventionHolds();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.isInInsertionMode() : "Violation of: this.insertion_mode";

        this.entries.enqueue(x);
        assert this.conventionHolds();
    }

    @Override
    public final void changeToExtractionMode() {
        assert this.isInInsertionMode() : "Violation of: this.insertion_mode";

        this.insertionMode = false;
        this.heap = buildHeap(this.entries, this.machineOrder);
        this.heapSize = this.heap.length();

        assert this.conventionHolds();
    }

    @Override
    public final T removeFirst() {
        assert !this.isInInsertionMode() : "Violation of: not this.insertion_mode";
        assert this.size() > 0 : "Violation of: this.contents /= {}";

        T first = this.heap.entryAt(0);
        this.heap.exchangeEntriesAt(0, this.heapSize - 1);
        this.heapSize--;
        siftDown(this.heap, 0, this.heapSize - 1, this.machineOrder);
        // Return the result after checking the convention.
        assert this.conventionHolds();
        return first;
    }

    @Override
    public final boolean isInInsertionMode() {
        assert this.conventionHolds();
        return this.insertionMode;
    }

    @Override
    public final Comparator<T> order() {
        assert this.conventionHolds();
        return this.machineOrder;
    }

    @Override
    public final int size() {

        assert this.conventionHolds();
        return this.heapSize;
    }

    @Override
    public final Iterator<T> iterator() {
        return new SortingMachine5Iterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code SortingMachine5}.
     */
    private final class SortingMachine5Iterator implements Iterator<T> {

        /**
         * Representation iterator.
         */
        private final Iterator<T> iterator;

        /**
         * Iterator count.
         */
        private int notSeenCount;

        /**
         * Default constructor.
         */
        private SortingMachine5Iterator() {
            if (SortingMachine5.this.insertionMode) {
                this.iterator = SortingMachine5.this.entries.iterator();
            } else {
                this.iterator = SortingMachine5.this.heap.iterator();
                this.notSeenCount = SortingMachine5.this.heapSize;
            }
            assert SortingMachine5.this.conventionHolds();
        }

        @Override
        public boolean hasNext() {
            if (!SortingMachine5.this.insertionMode && (this.notSeenCount == 0)) {
                assert SortingMachine5.this.conventionHolds();
                return false;
            }
            assert SortingMachine5.this.conventionHolds();
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
            if (!SortingMachine5.this.insertionMode) {
                this.notSeenCount--;
            }
            assert SortingMachine5.this.conventionHolds();
            return this.iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }

    }

}
