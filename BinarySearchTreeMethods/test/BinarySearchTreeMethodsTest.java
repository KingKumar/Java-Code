import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;

/**
 * JUnit test fixture for {@code BinarySearchTreeMethods}'s static methods
 * isInTree (and removeSmallest).
 */
public class BinarySearchTreeMethodsTest {

    /**
     * Constructs and return a BST created by inserting the given {@code args}
     * into an empty tree in the order in which they are provided.
     * 
     * @param args
     *            the {@code String}s to be inserted in the tree
     * @return the BST with the given {@code String}s
     * @requires <pre>
     * {@code [the Strings in args are all distinct]}
     * </pre>
     * @ensures <pre>
     * {@code createBSTFromArgs = [the BST with the given Strings]}
     * </pre>
     */
    private static BinaryTree<String> createBSTFromArgs(String... args) {
        BinaryTree<String> t = new BinaryTree1<String>();
        for (String s : args) {
            BinaryTreeUtility.insertInTree(t, s);
        }
        return t;
    }

    @Test
    public void sampleTest() {
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c");
        assertEquals(true, BinarySearchTreeMethods.isInTree(t1, "a"));
        assertEquals(t2, t1);
        assertEquals(true, BinarySearchTreeMethods.isInTree(t1, "b"));
        assertEquals(t2, t1);
        assertEquals(true, BinarySearchTreeMethods.isInTree(t1, "c"));
        assertEquals(t2, t1);
        assertEquals(false, BinarySearchTreeMethods.isInTree(t1, "d"));
        assertEquals(t2, t1);
    }

    // TODO: add here other test cases for BinarySearchTreeMethods.isInTree
    // (and for BinarySearchTreeMethods.removeSmallest)

}
