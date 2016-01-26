package components.list;

import java.util.Iterator;

/**
 * Layered implementations of {@code equals}, {@code hashCode}, and
 * {@code toString} for {@code ListKernel}.
 * 
 * <p>
 * Execution-time performance of {@code equals} and {@code toString} implemented
 * in this class is O(|{@code this}|). Execution-time performance of
 * {@code hashCode} implemented in this class is O(1).
 * 
 * @param <T>
 *            type of {@code ListKernel} entries
 */
public abstract class ListCommon<T> implements List<T> {

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ListKernel<?>)) {
            return false;
        }
        ListKernel<?> list = (ListKernel<?>) obj;
        if (this.rightLength() != list.rightLength()
                || this.leftLength() != list.leftLength()) {
            return false;
        }
        Iterator<T> it1 = this.iterator();
        Iterator<?> it2 = list.iterator();
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
        this.moveToStart();
        final int samples = 2;
        final int a = 37;
        final int b = 17;
        int result = 0;
        /*
         * This code makes hashCode run in O(1) time. It works because of the
         * iterator order string specification, which guarantees that the (at
         * most) samples keys returned by the it.next() calls are the same when
         * the two Lists are equal.
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
        int leftLength = this.leftLength();
        StringBuilder result = new StringBuilder("(<");
        Iterator<T> it = this.iterator();
        int count = 0;
        while (count < leftLength) {
            result.append(it.next());
            count++;
            if (count < leftLength) {
                result.append(",");
            }
        }
        result.append(">,<");
        while (it.hasNext()) {
            result.append(it.next());
            if (it.hasNext()) {
                result.append(",");
            }
        }
        result.append(">)");
        return result.toString();
    }

}
