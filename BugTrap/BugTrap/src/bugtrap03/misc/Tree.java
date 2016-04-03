package bugtrap03.misc;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import purecollections.PList;

/**
 * A Tree data structure with an empty root node.
 * <br> This collection is based on nodes holding values.
 * <br> Removing a value does not result in removing the nodes unless the specific methods
 * ({@link Tree#removeTree(java.lang.Object)}, {@link Tree#sizeObjNb()}) are used.
 *
 * <br> The Iterators included will not ignore these left-over-empty-nodes and will iterate over with null values.
 *
 * @author Group 03
 */
public class Tree<T> implements Iterable<T>, Collection<T> {

    /**
     * Create an empty Tree node. This will attach a null reference to the node.
     */
    public Tree() {
        this.children = PList.<Tree<T>>empty();
    }

    /**
     * Create a Tree structure with the given object as the attached value and the parent as the parent Tree.
     *
     * @param parent The parent Tree to add to.
     * @param obj The value to attach to this node.
     */
    private Tree(Tree<T> parent, T obj) {
        this();
        this.value = obj;
        this.parent = parent;
    }

    private T value;
    private PList<Tree<T>> children;
    private Tree<T> parent;

    /**
     * Get the value attached to this node.
     *
     * @return The value attached to this node.
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Get a {@link PList} of sub trees.
     *
     * @return The Trees directly attached to this Tree.
     */
    public PList<Tree<T>> getSubTree() {
        return children;
    }

    /**
     * Create a new Tree node with the given obj as the inserted value. The created Tree will be attached to this Tree.
     *
     * @param obj The value to attach to the new node.
     * @return The newly created Tree.
     */
    public Tree addTree(T obj) {
        Tree<T> node = new Tree(this, obj);
        this.children = this.children.plus(node);
        return node;
    }

    /**
     * Returns an iterator over elements of type T. This will iterate over all elements, including null.
     * <br> It is not advised to make changes during the iteration of the iterator.
     * @returns an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new TreeIterator<>(this);
    }

    /**
     * Returns an iterator over elements of type Tree<T>. This will iterate over all nodes depth-first.
     * <br> It is not advised to make changes during the iteration of the iterator.
     * @return an Iterator
     */
    public Iterator<Tree<T>> nodeIterator() {
        return new TreeNodeIterator<>(this);
    }

    /**
     * The number of nodes in this collection.
     * <br> If this collection contains more than Integer.MAX_VALUE elements, Integer.MAX_VALUE will be returned. This
     * will count the nodes, including this root if the (value != null null || parent != null) else this node is
     * excluded from count.
     *
     * @return The number of nodes in this tree if the tree contains less than Integer.MAX_VALUE elements.
     */
    @Override
    public int size() {
        int count = (this.value == null && this.parent == null) ? 0 : 1;
        for (Tree<T> subTree : this.children) {
            int subSize = subTree.size();
            long result = count + subSize;

            if (result > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }

            count += subSize;
        }
        return count;
    }

    /**
     * Count the amount of nodes holding values != null.
     * <br> If this collection contains more than Integer.MAX_VALUE elements, Integer.MAX_VALUE will be returned.
     *
     * @return The amount of non-null objects in this collection if the tree contains less than Integer.MAX_VALUE
     * elements.
     */
    public int sizeObjNb() {
        int count = (this.value == null) ? 0 : 1;
        for (Tree<T> subTree : this.children) {
            int subSize = subTree.sizeObjNb();
            long result = count + subSize;

            if (result > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }

            count += subSize;
        }
        return count;
    }

    /**
     * True if this collection contains no elements. This means there are no subTrees and no value attached.
     *
     * @return true if this collection contains no elements
     */
    @Override
    public boolean isEmpty() {
        return (this.children.isEmpty() && this.value == null);
    }

    /**
     * Returns true if this collection contains the specified element. More formally, returns true if and only if this
     * collection contains at least one element e such that (o==null ? e==null : o.equals(e)).
     * <br> This works for both passing the elements inside the Tree as the Tree references themselves.
     *
     * @param o - element whose presence in this collection is to be tested
     * @return true if this collection contains the specified element
     */
    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return this.value == null;
        } else if (o.equals(this.value) || o == this) {
            return true;
        } else {
            for (Tree<T> subTree : this.children) {
                if (subTree.contains(o)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Returns an array containing all of the elements in this collection.
     * <br> If this collection makes any guarantees as to what order its elements are returned by its iterator, this
     * method must return the elements in the same order. The returned array will be "safe" in that no references to it
     * are maintained by this collection. (In other words, this method must allocate a new array even if this collection
     * is backed by an array). The caller is thus free to modify the returned array. This method acts as bridge between
     * array-based and collection-based APIs.
     *
     * @return an array containing all of the elements in this collection
     * @see Tree#size()
     */
    @Override
    public Object[] toArray() {
        int length = this.size();
        Object[] arr = new Object[length];
        if (length != 0) {
            int currIndex = 0;
            if (!(parent == null && this.value == null)) {
                arr[currIndex] = this.value;
                currIndex++;
            }
            for (Tree<T> subTree : this.children) {
                Object[] sArr = subTree.toArray();

                long result = currIndex + sArr.length;

                int sLength = (result > Integer.MAX_VALUE) ? Integer.MAX_VALUE - currIndex : sArr.length;
                System.arraycopy(sArr, 0, arr, currIndex, sLength);

                currIndex = (int) Math.min(result, Integer.MAX_VALUE);
            }
        }

        return arr;
    }

    /**
     * Not implemented! Don't use.
     *
     * @param <T>
     * @param a
     * @return
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    /**
     * Ensures that this collection contains the specified element.
     * <br> This will add the element in a new tree node attached to this Tree.
     * <br> Caution: null can be added but not removed.
     *
     * @param e The element to add.
     * @return Returns true if this collection changed as a result of the call.
     */
    @Override
    public boolean add(T e) {
        this.addTree(e);
        return true;
    }

    /**
     * Removes a single instance of the specified element from this collection, if it is present (optional operation).
     * <br> More formally, removes an element e such that (o==null ? e==null : o.equals(e)), if this collection contains
     * one or more such elements. Returns true if this collection contained the specified element (or equivalently, if
     * this collection changed as a result of the call).
     *
     * <p>
     * Clarification: This will remove based on the values held in the tree nodes. When such a value is found the the
     * value is only removed from that node but the node remains. (Check {@link Tree#removeTree(java.lang.Object)} to
     * remove whole trees).
     *
     * <p>
     * When o == null no changes will be made as removing results in setting the value to null.
     *
     * @param o - element to be removed from this collection, if present
     * @return True if the element was removed due to this call.
     */
    @Override
    public boolean remove(Object o) {
        //Check this node
        if (o == null) {
            return false;
        } else if (o.equals(this.value)) {
            this.value = null;
            return true;
        }

        //Check child nodes
        for (Tree<T> subTree : this.children) {
            if (subTree.remove(o)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Clear the dangling null values.
     * <br> Every Tree that does not contain any non null value in the node and below in all its subTrees will be
     * removed.
     */
    public void clearLeftNull() {
        //Remove null from all its subtrees
        for (Tree<T> subTree : this.children) {
            subTree.clearLeftNull();
        }

        //If left empty and contains null remove itself from parent
        if (this.children.isEmpty()) {
            if (this.value == null && this.parent != null) {
                this.parent.children = this.parent.children.minus(this);
            }
        }
    }

    /**
     * Remove the node and all its subtrees from this Tree for the node that contains o.
     * <br> null will not be removed from top elements (value == null && no parent).
     *
     * @param o The object to remove the node over.
     * @return Whether the collection was changed.
     */
    public boolean removeTree(Object o) {
        //Check this node
        if (o == null) {
            if (o == this.value) {
                this.value = null;
                if (this.parent != null) { //Unlink from parent
                    this.parent.children = this.parent.children.minus(this);
                    return true;
                }
            }
        } else if (o.equals(this.value)) {
            this.value = null;
            if (this.parent != null) { //Unlink from parent
                this.parent.children = this.parent.children.minus(this);
            }
            return true;
        }

        //Check child nodes
        for (Tree<T> subTree : this.children) {
            if (subTree.removeTree(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (!this.contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean changed = false;
        for (T obj : c) {
            if (this.add(obj)) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Removes all of this collection's elements that are also contained in the specified collection (optional
     * operation).
     * <br>After this call returns, this collection will contain no elements in common with the specified collection.
     * <br><b> When removing any element of a Tree the node as well as all its sub-nodes will be removed as well.</b>
     *
     * @param c collection containing elements to be removed from this collection
     * @return true if this collection changed as a result of the call
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object obj : c) {
            if (this.remove(obj)) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Retains only the elements in this collection that are contained in the specified collection (optional operation).
     * <br> In other words, removes from this collection all of its elements that are not contained in the specified
     * collection.
     * <br><b>When removing any element of a Tree, the node as well as all its sub-nodes will be removed.</b>
     * <br> This can be a fairly expensive operation O(n²).
     *
     * @param c collection containing elements to be retained in this collection
     * @return true if this collection changed as a result of the call
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            this.value = null;
            this.children = PList.<Tree<T>>empty();
            return true;
        }

        Iterator<Tree<T>> iterator = new TreeNodeIterator<>(this);
        boolean changed = false;

        while (iterator.hasNext()) {
            Tree<T> node = iterator.next();
            if (!c.contains(node.value)) {
                changed = this.remove(c) || changed;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        for (Tree<T> tree : children) {
            tree.parent = null;
        }

        this.children = PList.<Tree<T>>empty();
    }

    /**
     * Get the amount of shallow children.
     *
     * @return The amount of children attached to this Tree without checking deep.
     */
    public int childrenSize() {
        return this.children.size();
    }

    /**
     * Depth first TreeIterator. This will skip the value of the top/root node passed. Will return the TreeNodes
     * themselves.
     *
     * @param <T> The type of object contained in the tree nodes.
     */
    private class TreeNodeIterator<T> implements Iterator<Tree<T>> {

        /**
         * Create a TreeIterator which will Iterate the tree depth-first starting from the given root. The value of the
         * root itself is ignored.
         *
         * @param root The root where to start iterating from.
         * @throws IllegalArgumentException When root is a null reference.
         */
        public TreeNodeIterator(Tree<T> root) {
            if (root == null) {
                throw new IllegalArgumentException("Can not create a TreeIterator with root equal to a null reference.");
            }

            this.startRoot = root;
            this.currNode = root;
            this.nextIndex = 0;

            this.bufferNode = null;
        }

        private final Tree<T> startRoot;
        private Tree<T> currNode;
        private int nextIndex;

        /* The element received from doing hasNext() */
        private Tree<T> bufferNode;

        @Override
        public boolean hasNext() {
            if (bufferNode != null) {
                return true;
            }

            /* Try to fill buffer */
            try {
                bufferNode = next();
            } catch (NoSuchElementException e) {
                //Can't fill buffer, no element left.
                return false;
            }

            //Buffer succesfully filled.
            return true;
        }

        @Override
        public Tree<T> next() {
            /* Check in buffer */
            if (bufferNode != null) {
                Tree<T> tempNode = bufferNode;
                bufferNode = null;
                return tempNode;
            }

            /* Check if possible to go deeper */
            if (currNode.childrenSize() > nextIndex) {
                //Go deeper, print value and reset index.
                return goOneDeeper();
            }

            /* Hit a leaf, going back up and settings index to next node.*/
            goOneUp();

            /* Check if nextIndex is valid. If not, go up. */
            if (nextIndex < currNode.childrenSize()) {
                return goOneDeeper();
            } else {
                goOneUp();
                return next();
            }
        }

        /**
         * Go one node up and set the nextIndex to the correct one.
         *
         * @throws NoSuchElementException When the next node up is the parent node of the initial startNode meaning there
         * is no next element.
         */
        private void goOneUp() {
            Tree<T> oldNode = currNode;
            currNode = currNode.parent;
            if (currNode == startRoot.parent || currNode == null) {
                throw new NoSuchElementException("Trying to get next element while there is none left in the Tree.");
            }
            nextIndex = currNode.children.indexOf(oldNode) + 1;
        }

        /**
         * Go one level deeper in the nextIndex node, set the nextIndex to zero and return the value of the new node.
         *
         * @return The value of the old.nextIndex node.
         */
        private Tree<T> goOneDeeper() {
            currNode = currNode.children.get(nextIndex);
            nextIndex = 0;
            return currNode;
        }

    }

    /**
     * Depth first TreeIterator. This will skip the value of the top/root node passed. Will return the values inside the
     * treeNodes.
     *
     * @param <T> The Type of object contained in the Tree nodes.
     */
    private class TreeIterator<T> implements Iterator<T> {

        /**
         * Create a TreeIterator which will Iterate the tree depth-first starting from the given root. The value of the
         * root itself is ignored.
         *
         * @param root The root where to start iterating from.
         * @throws IllegalArgumentException When root is a null reference.
         */
        public TreeIterator(Tree<T> root) {
            if (root == null) {
                throw new IllegalArgumentException("Can not create a TreeIterator with root equal to a null reference.");
            }

            this.startRoot = root;
            this.currNode = root;
            this.nextIndex = 0;

            this.bufferNode = null;
        }

        private final Tree<T> startRoot;
        private Tree<T> currNode;
        private int nextIndex;

        /* The element received from doing hasNext() */
        private Tree<T> bufferNode;

        @Override
        public boolean hasNext() {
            if (bufferNode != null) {
                return true;
            }

            /* Try to fill buffer */
            try {
                next();
                bufferNode = currNode;
            } catch (NoSuchElementException e) {
                //Can't fill buffer, no element left.
                return false;
            }

            //Buffer succesfully filled.
            return true;
        }

        @Override
        public T next() {
            /* Check in buffer */
            if (bufferNode != null) {
                T value = bufferNode.getValue();
                bufferNode = null;
                return value;
            }

            /* Check if possible to go deeper */
            if (currNode.childrenSize() > nextIndex) {
                //Go deeper, print value and reset index.
                return goOneDeeper();
            }

            /* Hit a leaf, going back up and settings index to next node.*/
            goOneUp();

            /* Check if nextIndex is valid. If not, go up. */
            if (nextIndex < currNode.childrenSize()) {
                return goOneDeeper();
            } else {
                goOneUp();
                return next();
            }
        }

        /**
         * Go one node up and set the nextIndex to the correct one.
         *
         * @throws NoSuchElementException When the next node up is the parent node of the initial startNode meaning there
         * is no next element.
         */
        private void goOneUp() {
            Tree<T> oldNode = currNode;
            currNode = currNode.parent;
            if (currNode == startRoot.parent) {
                throw new NoSuchElementException("Trying to get next element while there is none left in the Tree.");
            }
            nextIndex = currNode.children.indexOf(oldNode) + 1;
        }

        /**
         * Go one level deeper in the nextIndex node, set the nextIndex to zero and return the value of the new node.
         *
         * @return The value of the old.nextIndex node.
         */
        private T goOneDeeper() {
            currNode = currNode.children.get(nextIndex);
            nextIndex = 0;
            return currNode.getValue();
        }
    }

}
