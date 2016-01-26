package components.waitingline;

/**
 * Layered implementations of secondary methods for {@code WaitingLine}.
 * 
 * <p>
 * Execution-time performance of {@code append} and {@code flip} implemented in
 * this class is O(|{@code this}|). Execution-time performance of {@code sort}
 * implemented in this class is O(|{@code this}| log |{@code this}|) expected,
 * O(|{@code this}|^2) worst case.
 * 
 * @param <T>
 *            type of WaitingLine entries
 */
public abstract class WaitingLineSecondary<T> extends WaitingLineCommon<T> {

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void append(WaitingLine<T> q) {
        assert q != null : "Violation of: q is not null";
        assert q != this : "Violation of: q is not this";

        while (q.length() > 0) {
            T x = q.dequeue();
            this.enqueue(x);
        }
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public T remove(T x) {
        WaitingLine<T> copy = this.newInstance();
        copy.transferFrom(this);
        boolean isFound = false;
        T answer = null;
        while (copy.length() > 0) {
            T temp = copy.dequeue();
            if (temp.equals(x) && !isFound) {
                answer = temp;
                isFound = true;
            } else {
                this.enqueue(temp);
            }
        }
        return answer;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int position(T x) {
        WaitingLine<T> copy = this.newInstance();
        copy.transferFrom(this);
        int answer = 0;
        while (copy.length() > 0) {
            T temp = copy.dequeue();
            if (temp.equals(x)) {
                answer = this.length() + 1;
            }
            this.enqueue(temp);
        }
        return answer;
    }
}
