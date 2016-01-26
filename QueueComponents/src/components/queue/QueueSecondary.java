package components.queue;

import java.util.Comparator;

/**
 * Layered implementations of secondary methods for {@code Queue}.
 * 
 * <p>
 * Execution-time performance of {@code append} and {@code flip} implemented in
 * this class is O(|{@code this}|). Execution-time performance of {@code sort}
 * implemented in this class is O(|{@code this}| log |{@code this}|) expected,
 * O(|{@code this}|^2) worst case.
 * 
 * @param <T>
 *            type of Queue entries
 */
public abstract class QueueSecondary<T> extends QueueCommon<T> {

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void append(Queue<T> q) {
        assert q != null : "Violation of: q is not null";
        assert q != this : "Violation of: q is not this";

        /*
         * 2221/2231 assignment code deleted.
         */
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void flip() {

        /*
         * 2221/2231 assignment code deleted.
         */
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void sort(Comparator<T> order) {
        assert order != null : "Violation of: order is not null";

        /*
         * 2221/2231 assignment code deleted.
         */
    }

}
