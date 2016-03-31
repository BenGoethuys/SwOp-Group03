package bugtrap03.misc;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Admin
 */
public class TreeTest {

    //TODO: Tests for addAll, remove, removeAll, retainAll, iterator, nodeIterator
    
    @Before
    public void setUp() {
        tree = new Tree();
        subTree = tree.addTree(30);
        assertTrue(tree.add(50));
    }

    private Tree<Integer> tree;
    private Tree<Integer> subTree;

    /**
     * Test the constructor of a Tree.
     *
     * <li> Implies size == 1 </li>
     * <li> Implies a value of null (due to top node) </li>
     * <li> Implies no children nodes. </li>
     * <li> Implies empty Tree. </li>
     * </ul>
     */
    @Test
    public void testCons() {
        Tree<Integer> tree2 = new Tree();

        assertEquals(0, tree2.size());
        assertEquals(null, tree2.getValue());
        assertTrue(tree2.contains(null));
        assertEquals(0, tree2.childrenSize());
        assertTrue(tree2.isEmpty());
    }

    /**
     * Test add by adding an element to a root as well as adding a new Tree to the root. This tests contains the checks
     * and the operation of adding is in setUp().
     *
     * @see TreeTest#setUp()
     * @see Tree#add(java.lang.Object)
     * @see Tree#addTree(java.lang.Object)
     */
    @Test
    public void testCheckers() {

        //SubTree
        assertTrue(tree.getSubTree().contains(subTree));
        assertEquals(tree.getSubTree().size(), tree.childrenSize());

        //getValue
        assertEquals(new Integer(30), subTree.getValue());
        assertEquals(null, tree.getValue());

        //Contains
        assertTrue(tree.contains(50));
        assertTrue(tree.contains(30));
        assertFalse(subTree.contains(50));

        //Empty
        assertFalse(tree.isEmpty());
        assertFalse(subTree.isEmpty());

        //Size
        assertEquals(2, tree.size());
        assertEquals(1, subTree.size());
        assertEquals(0, subTree.childrenSize());
        assertEquals(2, tree.childrenSize());
    }

    /**
     * Test whether clear() on root tree will leave the root tree with no sub nodes.
     */
    @Test
    public void testClear() {
        tree.clear();

        assertEquals(null, tree.getValue());
        assertEquals(0, tree.size());
        assertTrue(tree.contains(null));
        assertTrue(tree.getSubTree().isEmpty());
    }

}
