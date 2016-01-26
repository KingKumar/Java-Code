package components.waitingline;

import java.util.Iterator;

/**
 * Layered implementations of {@code equals}, {@code hashCode}, and
 * {@code toString} for {@code WaitingLineKernel}.
 * 
 * <p>
 * Execution-time performance of {@code equals} and {@code toString} implemented
 * in this class is O(|{@code this}|). Execution-time performance of
 * {@code hashCode} implemented in this class is O(1).
 * 
 * @param <T>
 *            type of {@code WaitingLineKernel} entries
 */
public abstract class WaitingLineCommon<T> implements WaitingLine<T> {

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WaitingLineKernel<?>)) {
            return false;
        }
        WaitingLineKernel<?> q = (WaitingLineKernel<?>) obj;
        if (this.length() != q.length()) {
            return false;
        }
        Iterator<T> it1 = this.iterator();
        Iterator<?> it2 = q.iterator();
        while (it1.hasNext()) {
            T x1 = it1.next();
            Object x2 = it2.next();
            if (!x1.equals(x2)) {
                return false;
            }
        }
        return true;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int hashCode() {
        final int samples = 2;
        final int a = 37;
        final int b = 17;
        int result = 0;
        /*
         * This code makes hashCode run in O(1) time. It works because of the
         * iterator order string specification, which guarantees that the (at
         * most) samples keys returned by the it.next() calls are the same when
         * the two Queues are equal.
         */
        int n = 0;
        Iterator<T> it = this.iterator();
        while (n < samples && it.hasNext()) {
            n++;
            T x = it.next();
            result = a * result + b * x.hashCode();
        }
        return result;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<");
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            result.append(it.next());
            if (it.hasNext()) {
                result.append(",");
            }
        }
        result.append(">");
        return result.toString();
    }

}
