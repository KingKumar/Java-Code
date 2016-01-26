import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.binarytree.BinaryTree;

/**
 * JUnit test fixture for {@code BinaryTreeMethods}'s static methods height and
 * isInTree.
 */
public class BinaryTreeMethodsTest {

    @Test
    public void testHeightEmpty() {
        BinaryTree<String> t = BinaryTreeUtility.treeFromString("()");
        BinaryTree<String> tOld = BinaryTreeUtility.treeFromString("()");
        int h = BinaryTreeMethods.height(t);
        assertEquals(0, h);
        assertEquals(tOld, t);
    }

    @Test
    public void testHeightOne() {
        BinaryTree<String> t = BinaryTreeUtility.treeFromString("a(()())");
        BinaryTree<String> tOld = BinaryTreeUtility.treeFromString("a(()())");
        int h = BinaryTreeMethods.height(t);
        assertEquals(1, h);
        assertEquals(tOld, t);
    }

    @Test
    public void testHeightTwo() {
        BinaryTree<String> t = BinaryTreeUtility.treeFromString("a(b(()())())");
        BinaryTree<String> tOld = BinaryTreeUtility
                .treeFromString("a(b(()())())");
        int h = BinaryTreeMethods.height(t);
        assertEquals(2, h);
        assertEquals(tOld, t);
    }

    @Test
    public void testHeightMany() {
        BinaryTree<String> t = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        BinaryTree<String> tOld = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        int h = BinaryTreeMethods.height(t);
        assertEquals(5, h);
        assertEquals(tOld, t);
    }

    @Test
    public void testIsInTreeREmpty() {
        BinaryTree<String> t = BinaryTreeUtility.treeFromString("()");
        BinaryTree<String> tOld = BinaryTreeUtility.treeFromString("()");
        boolean found = BinaryTreeMethods.isInTree(t, "a");
        assertEquals(false, found);
        assertEquals(tOld, t);
    }

    @Test
    public void testIsInTreeROneTrue() {
        BinaryTree<String> t = BinaryTreeUtility.treeFromString("a(()())");
        BinaryTree<String> tOld = BinaryTreeUtility.treeFromString("a(()())");
        boolean found = BinaryTreeMethods.isInTree(t, "a");
        assertEquals(true, found);
        assertEquals(tOld, t);
    }

    @Test
    public void testIsInTreeROneFalse() {
        BinaryTree<String> t = BinaryTreeUtility.treeFromString("a(()())");
        BinaryTree<String> tOld = BinaryTreeUtility.treeFromString("a(()())");
        boolean found = BinaryTreeMethods.isInTree(t, "b");
        assertEquals(false, found);
        assertEquals(tOld, t);
    }

    @Test
    public void testIsInTreeRManyLeftTrue() {
        BinaryTree<String> t = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        BinaryTree<String> tOld = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        boolean found = BinaryTreeMethods.isInTree(t, "b");
        assertEquals(true, found);
        assertEquals(tOld, t);
    }

    @Test
    public void testIsInTreeRManyRightTrue() {
        BinaryTree<String> t = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        BinaryTree<String> tOld = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        boolean found = BinaryTreeMethods.isInTree(t, "c");
        assertEquals(true, found);
        assertEquals(tOld, t);
    }

    @Test
    public void testIsInTreeRManyLeafTrue() {
        BinaryTree<String> t = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        BinaryTree<String> tOld = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        boolean found = BinaryTreeMethods.isInTree(t, "f");
        assertEquals(true, found);
        assertEquals(tOld, t);
    }

    @Test
    public void testIsInTreeRManyRootTrue() {
        BinaryTree<String> t = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        BinaryTree<String> tOld = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        boolean found = BinaryTreeMethods.isInTree(t, "a");
        assertEquals(true, found);
        assertEquals(tOld, t);
    }

    @Test
    public void testIsInTreeRManyFalse() {
        BinaryTree<String> t = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        BinaryTree<String> tOld = BinaryTreeUtility
                .treeFromString("a(b(()())c(d(()e(f(()())()))()))");
        boolean found = BinaryTreeMethods.isInTree(t, "g");
        assertEquals(false, found);
        assertEquals(tOld, t);
    }

}
