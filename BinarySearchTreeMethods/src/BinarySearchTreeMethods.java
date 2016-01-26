import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utility class with implementation of binary search tree static, generic
 * methods isInTree (and removeSmallest).
 * 
 * @mathdefinitions <pre>
 * {@code IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]}
 * </pre>
 * 
 * @author Ronit Kumar
 * 
 */
public final class BinarySearchTreeMethods {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private BinarySearchTreeMethods() {
    }

    /**
     * Returns whether {@code x} is in {@code t}.
     * 
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires <pre>
     * {@code IS_BST(t)}
     * </pre>
     * @ensures <pre>
     * {@code isInTree = (x is in labels(t))}
     * </pre>
     */
    public static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {

        for (T a : t) {
            if (a.equals(x)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     * 
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates {@code t}
     * @requires <pre>
     * {@code IS_BST(t)  and  |t| > 0}
     * </pre>
     * @ensures <pre>
     * {@code IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}}
     * </pre>
     */
    public static <T> T removeSmallest(BinaryTree<T> t) {

        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        T answer;
        T root = t.disassemble(left, right);
        if (left.height() > 0) {
            answer = removeSmallest(left);
            t.assemble(root, left, right);
        } else {
            answer = root;
            t.transferFrom(right);
        }
        return answer;
    }

    /**
     * Checks if the given {@code BinaryTree<Integer>} satisfies the heap
     * ordering property according to the <= relation.
     * 
     * @param t
     *            the binary tree
     * @return true if the given tree satisfies the heap ordering property;
     *         false otherwise
     * @ensures <pre>
     * {@code satisfiesHeapOrdering = [t satisfies the heap ordering property]}
     * </pre>
     */
    private static boolean satisfiesHeapOrdering(BinaryTree<Integer> t) {

        BinaryTree<Integer> left = t.newInstance();
        BinaryTree<Integer> right = t.newInstance();
        boolean heap = true;
        int root;
        if (t.size() > 0) {
            root = t.disassemble(left, right);
            if (root > left.root() || root > right.root()) {
                heap = false;
            }
            satisfiesHeapOrdering(left);
            satisfiesHeapOrdering(right);
        }
        return heap;
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
         * Input tree labels and construct BST.
         */
        out.println("Input the distinct labels for a binary search tree "
                + "in the order in which you want them inserted.");
        out.println("Press Enter on an empty line to terminate your input.");
        out.println();
        out.print("Next label: ");
        String str = in.nextLine();
        BinaryTree<String> t = new BinaryTree1<String>();
        while (str.length() > 0) {
            BinaryTreeUtility.insertInTree(t, str);
            out.println();
            out.println("t = " + BinaryTreeUtility.treeToString(t));
            out.println();
            out.print("Next label: ");
            str = in.nextLine();
        }
        /*
         * Input strings and check whether each is in the BST or not.
         */
        out.println();
        out.print("  Input a label to search "
                + "(or just press Enter to input a new tree): ");
        String label = in.nextLine();
        while (label.length() > 0) {
            if (isInTree(t, label)) {
                out.println("    \"" + label + "\" is in the tree");
            } else {
                out.println("    \"" + label + "\" is not in the tree");
            }
            out.print("  Input a label to search "
                    + "(or just press Enter to terminate the program): ");
            label = in.nextLine();
        }

        out.println();
        out.println("Labels in BST in order:");
        while (t.size() > 0) {
            label = removeSmallest(t);
            out.println("  " + label);
        }

        in.close();
        out.close();
    }
}
