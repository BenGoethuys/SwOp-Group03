package bugtrap03.misc;

import bugtrap03.model.DataModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Admin
 */
public class TreeTest {

    //TODO: Tests for addAll, removeAll, retainAll, toArray
    @Before
    public void setUp() {
        S_tree = new Tree<>();
        S_subTree = S_tree.addTree(30);
        assertTrue(S_tree.add(50));

        tree = new Tree<>();
        subTree1 = tree.addTree(50);
        subTree2 = tree.addTree(60);
        subTree1_1 = subTree1.addTree(30);
        subTree1_1.add(20);
        subTree2_1 = subTree2.addTree(70);
        subTree2_2 = subTree2.addTree(80);
    }

    private Tree<Integer> S_tree;
    private Tree<Integer> S_subTree;
    private Tree<Integer> tree;
    private Tree<Integer> subTree1;
    private Tree<Integer> subTree2;
    private Tree<Integer> subTree1_1;
    private Tree<Integer> subTree2_1;
    private Tree<Integer> subTree2_2;

    /**
     * Test the constructor of a Tree.
     *
     * <li> Implies size == 0 </li>
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
        assertTrue(S_tree.getSubTree().contains(S_subTree));
        assertEquals(S_tree.getSubTree().size(), S_tree.childrenSize());

        //getValue
        assertEquals(new Integer(30), S_subTree.getValue());
        assertEquals(null, S_tree.getValue());

        //Contains
        assertTrue(S_tree.contains(50));
        assertTrue(S_tree.contains(30));
        assertFalse(S_subTree.contains(50));

        //Empty
        assertFalse(S_tree.isEmpty());
        assertFalse(S_subTree.isEmpty());

        //Size
        assertEquals(2, S_tree.size());
        assertEquals(1, S_subTree.size());
        assertEquals(0, S_subTree.childrenSize());
        assertEquals(2, S_tree.childrenSize());
    }

    /**
     * Test whether clear() on root S_tree will leave the root S_tree with no sub nodes.
     */
    @Test
    public void testClear() {
        S_tree.clear();

        assertEquals(null, S_tree.getValue());
        assertEquals(S_tree.size(), 0);
        assertTrue(S_tree.contains(null));
        assertTrue(S_tree.getSubTree().isEmpty());
    }

    /**
     * Test {@link Tree#remove(java.lang.Object)} and {@link Tree#clearLeftNull()}
     */
    @Test
    public void testRemoveAndClear() {
        /* Tets remove */
        //Before
        assertEquals(tree.size(), 6);
        assertEquals(tree.sizeObjNb(), 6);

        //remove null
        assertFalse(tree.remove(null));
        assertEquals(tree.size(), 6);
        assertEquals(tree.sizeObjNb(), 6);

        //remove non existing element
        assertFalse(tree.remove(48));
        assertEquals(tree.size(), 6);
        assertEquals(tree.sizeObjNb(), 6);

        //remove S_tree in middle.
        assertTrue(tree.remove(50));
        assertFalse(tree.contains(50));
        assertEquals(tree.size(), 6);
        assertEquals(tree.sizeObjNb(), 5);
        assertTrue(tree.contains(30)); //Still contain elements below
        assertTrue(tree.contains(20)); //Still contain elements below

        //Try removing the new null element (no changes expected)
        assertFalse(tree.remove(null));
        assertEquals(6, tree.size());
        assertEquals(5, tree.sizeObjNb());

        /* Test clearLeftNull */
        //Clear dangling nulls (no changes expected)
        tree.clearLeftNull();
        assertEquals(6, tree.size());
        assertEquals(5, tree.sizeObjNb());

        //Remove 2 subTrees and clear dangling nulls (size decrease)
        assertTrue(tree.remove(30));
        assertTrue(tree.remove(20));
        tree.clearLeftNull();
        assertFalse(tree.contains(30));
        assertFalse(tree.contains(20));
        assertEquals(3, tree.size());
        assertEquals(3, tree.sizeObjNb());
    }

    @Test
    public void testRemoveTree() {
        //Before
        assertEquals(6, tree.size());
        assertEquals(6, tree.sizeObjNb());

        assertFalse(tree.removeTree(null)); //remove null
        assertEquals(6, tree.size());
        assertEquals(6, tree.sizeObjNb());

        assertFalse(tree.removeTree(48)); //remove non existing element
        assertEquals(6, tree.size());
        assertEquals(6, tree.sizeObjNb());

        assertTrue(tree.removeTree(50)); //remove S_tree in middle.
        assertFalse(tree.contains(50));
        assertEquals(3, tree.size());
        assertEquals(3, tree.sizeObjNb());
        assertFalse(tree.contains(30)); //no elements below
        assertFalse(tree.contains(20)); //no elements below

        assertFalse(tree.removeTree(null));
        assertEquals(3, tree.size());
        assertEquals(3, tree.sizeObjNb());
    }

    /**
     * Test {@link Tree#iterator()} using {@link Iterator#next()} and {@link Iterator#hasNext()} to see if every element
     * returned is in the Tree and if the amount of elements is the same with the amount in the tree.
     */
    @Test
    public void testIterator() {
        Iterator<Integer> iterator = tree.iterator();
        int counter = 0;

        assertTrue(iterator.hasNext());

        while (iterator.hasNext()) {
            int ele = iterator.next();
            counter++;
            assertTrue(tree.contains(ele));
        }

        assertEquals(tree.size(), counter);
    }

    /**
     * Test {@link Tree#iterator()} using {@link Iterator#next()} to have the depth-first pattern.
     */
    @Test
    public void testIteratorNext() {
        Iterator<Integer> iterator = tree.iterator();

        //50, 30, 20, 60, 70, 80
        assertEquals((int) 50, (int) iterator.next());
        assertEquals((int) 30, (int) iterator.next());
        assertEquals((int) 20, (int) iterator.next());
        assertEquals((int) 60, (int) iterator.next());
        assertEquals((int) 70, (int) iterator.next());
        assertEquals((int) 80, (int) iterator.next());
    }

    /**
     * Test {@link Tree#iterator()} using {@link Iterator#next()} causing a NoSuchElementException
     */
    @Test(expected = NoSuchElementException.class)
    public void testIteratorNextNoSuchElement() {
        Tree tree = new Tree();
        tree.add(50);
        Iterator<Integer> iterator = tree.iterator();
        assertEquals((int) 50, (int) iterator.next());
        iterator.next();
    }

    /**
     * Test {@link Tree#iterator()} using {@link Iterator#next()} and {@link Iterator#hasNext()} to see if every element
     * returned is in the Tree and if the amount of elements is the same with the amount in the tree.
     */
    @Test
    public void testNodeIterator() {
        Iterator<Tree<Integer>> iterator = tree.nodeIterator();
        int counter = 0;

        assertTrue(iterator.hasNext());

        while (iterator.hasNext()) {
            Tree<Integer> node = iterator.next();
            counter++;
            assertTrue(tree.contains(node));
            assertTrue(tree.contains(node.getValue()));
        }

        assertEquals(tree.size(), counter);
    }

    /**
     * Test {@link Tree#nodeIterator()} using {@link Iterator#next()} to have the depth-first pattern.
     */
    @Test
    public void testNodeIteratorNext() {
        Iterator<Tree<Integer>> iterator = tree.nodeIterator();

        //50, 30, 20, 60, 70, 80
        assertEquals((int) 50, (int) iterator.next().getValue());
        assertEquals((int) 30, (int) iterator.next().getValue());
        assertEquals((int) 20, (int) iterator.next().getValue());
        assertEquals((int) 60, (int) iterator.next().getValue());
        assertEquals((int) 70, (int) iterator.next().getValue());
        assertEquals((int) 80, (int) iterator.next().getValue());
    }

    /**
     * Test {@link Tree#nodeIterator()} using {@link Iterator#next()} causing a NoSuchElementException
     */
    @Test(expected = NoSuchElementException.class)
    public void testNodeIteratorNextNoSuchElement() {
        Tree<Integer> tree = new Tree<>();
        tree.add(50);
        Iterator<Tree<Integer>> iterator = tree.nodeIterator();
        assertEquals((int) 50, (int) iterator.next().getValue());
        iterator.next();
    }

    /**
     * Test {@link Tree#removeAll(java.util.Collection)} with both present and not present values.
     */
    @Test
    public void removeAll() {
        List<Integer> list = new ArrayList<>();
        list.add(30);
        list.add(0); //not element of
        list.add(50);
        list.add(70);
        list.add(450); //not element of

        tree.removeAll(list);

        assertEquals(6, tree.size());
        assertEquals(3, tree.sizeObjNb());
    }

    /**
     * Test {@link Tree#containsAll(java.util.Collection) } for both True and False; for both Values as trees.
     */
    @Test
    public void testContainsAll() {
        List list = new ArrayList<>();

        //Test true with mix of values and trees.
        list.add(30);
        list.add(50);
        list.add(subTree2_1);
        list.add(null);

        assertTrue(tree.containsAll(list));

        //Test False by values.
        list.add(15);
        assertFalse(tree.containsAll(list));

        //revert
        list.remove((Integer) 15);

        //Test False by tree
        list.add(new Tree());
        assertFalse(tree.containsAll(list));
    }

    @Test
    public void testAddAll() {
        List<Integer> list = new ArrayList();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(null);
        Tree<Integer> tree = new Tree<>();

        tree.addAll(list);

        assertTrue(tree.contains(10));
        assertTrue(tree.contains(20));
        assertTrue(tree.contains(30));

        assertEquals(4, tree.size());
        assertEquals(3, tree.sizeObjNb());
    }

    /**
     * Test {@link Tree#toArray(T[])} which should do nothing and return null (because not implemented).
     */
    public void testToArray_Obj() {
        Tree<Integer> tree = new Tree<>();
        assertNull(tree.toArray(new Integer[5]));
    }
    
    @Test
    public void testRetainAll() {
        List<Integer> list = new ArrayList<>();
        list.add(30);
        assertTrue(tree.retainAll(list));
        
        assertEquals(2, tree.size());
        assertEquals(1, tree.sizeObjNb());
    }

}
