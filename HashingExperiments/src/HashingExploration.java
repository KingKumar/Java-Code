import java.util.Comparator;

import components.array.Array;
import components.array.Array1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Lets the user test the {@code hashCode(String)} method, by reading text lines
 * from a file (whose name is supplied by the user), and then outputting the
 * distribution of lines into buckets.
 * 
 * @author Ronit Kumar
 * 
 */
public final class HashingExploration {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HashingExploration() {
    }

    /**
     * Computes {@code a} mod {@code b} as % should have been defined to work.
     * 
     * @param a
     *            the number being reduced
     * @param b
     *            the modulus
     * @return the result of a mod b, which satisfies 0 <= {@code mod} < b
     * @requires <pre>
     * {@code b > 0}
     * </pre>
     * @ensures <pre>
     * {@code 0 <= mod  and  mod < b  and
     * there exists k: integer (a = k * b + mod)}
     * </pre>
     */
    public static int mod(int a, int b) {
        assert b > 0 : "Violation of: b > 0";
        int result = a % b;
        if (result < 0) {
            result += b;
        }
        return result;
    }

    /**
     * Returns a hash code value for the given {@code String}.
     * 
     * @param s
     *            the {@code String} whose hash code is computed
     * @return a hash code value for the given {@code String}
     * @ensures <pre>
     * {@code hashCode = [hash code value for the given String]}
     * </pre>
     */
    private static int hashCode(String s) {
        assert s != null : "Violation of: s is not null";

        // TODO - fill in body

        // This line added just to make the component compilable.
        return 0;
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
        Queue<T> q2 = new Queue1L<T>();
        int count = 0;
        int length = q.length();
        while (count < length) {
            T y = q.dequeue();
            if (order.compare(x, y) <= 0) {
                q2.enqueue(y);
                q2.enqueue(x);
            } else {
                q2.enqueue(x);
            }
            count++;
        }
        q.transferFrom(q2);
    }

    /**
     * Sorts {@code this} according to the ordering provided by the
     * {@code compare} method from {@code order}.
     * 
     * @param order
     *            ordering by which to sort
     * @updates {@code this}
     * @requires <pre>
     * IS_TOTAL_PREORDER([relation computed by order.compare method])
     * </pre>
     * @ensures <pre>
     * {@code perms(this, #this)  and
     * IS_SORTED(this, [relation computed by order.compare method])}
     * </pre>
     */
    public void sort(Comparator<T> order) {
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

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get hash table size and file name.
         */
        out.print("Hash table size: ");
        int hashTableSize = in.nextInteger();
        out.print("Text file name: ");
        String textFileName = in.nextLine();
        /*
         * Set up counts, counted, and totalHits.
         */
        Array<Integer> counts = new Array1L<Integer>(hashTableSize);
        Set<String> counted = new Set1L<String>();
        int totalHits = 0;
        /*
         * Get some lines of input, hash them, and record counts.
         */
        SimpleReader textFile = new SimpleReader1L(textFileName);
        while (!textFile.atEOS()) {
            String line = textFile.nextLine();
            if (!counted.contains(line)) {
                int bucket = mod(hashCode(line), hashTableSize);
                if (counts.mayBeExaminedAt(bucket)) {
                    counts.setEntryAt(bucket, counts.entryAt(bucket) + 1);
                } else {
                    counts.setEntryAt(bucket, 1);
                }
                totalHits++;
                counted.add(line);
            }
        }
        textFile.close();
        /*
         * Report results.
         */
        out.println();
        out.println("Bucket\tHits\tBar");
        out.println("------\t----\t---");
        for (int i = 0; i < counts.length(); i++) {
            if (counts.mayBeExaminedAt(i)) {
                out.print(i + "\t" + counts.entryAt(i) + "\t");
                for (int j = 0; j < counts.entryAt(i); j++) {
                    out.print("*");
                }
            } else {
                out.print(i + "\t" + 0 + "\t");
            }
            out.println();
        }
        out.println();
        out.println("Total:\t" + totalHits);
        in.close();
        out.close();
    }

}
