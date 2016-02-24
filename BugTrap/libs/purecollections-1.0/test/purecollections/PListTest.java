package purecollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import org.junit.Assert;
import org.junit.Test;

import purecollections.PList;

public class PListTest {

	@Test
	public void testPlusAndMinusFirst() {
		PList<Integer> list = PList.empty();
		for (int i = 0; i < 1000000; i++)
			list = list.plus(i);
		for (int i = 0; i < 1000000; i++) {
			Assert.assertEquals(i, (int)list.getFirst());
			list = list.minusFirst();
		}
		Assert.assertSame(PList.empty(), list);
	}
	
	@Test
	public void testSecondaryMethods() {
		PList<Integer> list = PList.<Integer>empty().plus(1).plus(2).plus(3).plus(4);
		
		Assert.assertEquals("[1, 2, 3, 4]", list.toString());
		Assert.assertEquals(Arrays.asList(new Integer[] {1,2,3,4}), list);
		Assert.assertEquals(list, Arrays.asList(new Integer[] {1,2,3,4}));
		Assert.assertEquals(Arrays.asList(new Integer[] {1,2,3,4}).hashCode(), list.hashCode());
		Assert.assertFalse(list.equals(Arrays.asList(new Integer[] {1,2,3})));
		Assert.assertFalse(list.equals(Arrays.asList(new Integer[] {1,2,3,5})));
		
		Assert.assertEquals(4, list.size());
		Assert.assertFalse(list.isEmpty());
		Integer[] xs = {1, 2, 3, 4};
		Assert.assertArrayEquals(xs, new ArrayList<Integer>(list).toArray()); // tests the iterator
		Assert.assertArrayEquals(xs, list.toArray());
		Assert.assertArrayEquals(xs, list.toArray(new Integer[0]));
		Assert.assertArrayEquals(new Integer[] {1,2,4}, list.minus((Integer)3).toArray());
		Assert.assertArrayEquals(new Integer[] {1,3,4}, list.minus(1).toArray());
		Assert.assertArrayEquals(new Integer[] {1,0,2,3,4}, list.plus(1, 0).toArray());
		Assert.assertArrayEquals(new Integer[] {0,1,2,3,4}, list.plus(0, 0).toArray());
		Assert.assertArrayEquals(new Integer[] {0,1,2,3,4}, list.plusFirst(0).toArray());
		Assert.assertArrayEquals(new Integer[] {-2,-1,0,1,2,3,4}, list.plusFirst(0).plusFirst(-1).plusFirst(-2).toArray());
		Assert.assertArrayEquals(new Integer[] {1,2,5,4}, list.with(2, 5).toArray());
		Assert.assertArrayEquals(new Integer[] {1,2,3,4,5,6}, list.plusAll(Arrays.asList(new Integer[]{5,6})).toArray());
		Assert.assertArrayEquals(new Integer[] {1,3}, list.minusAll(Arrays.asList(new Integer[]{2,4})).toArray());
		Assert.assertArrayEquals(new Integer[] {2,3}, list.subList(1,3).toArray());
		Assert.assertEquals(0, list.indexOf(1));
		Assert.assertEquals(-1, list.indexOf(5));
		Assert.assertEquals(4, list.plus(1).lastIndexOf(1));
		Assert.assertEquals(-1, list.lastIndexOf(5));
		Assert.assertTrue(list.contains(3));
		Assert.assertFalse(list.contains(-100));
		Assert.assertEquals((Object)2, list.get(1));
		Assert.assertTrue(list.containsAll(Arrays.asList(new Integer[] {1, 4})));
		Assert.assertFalse(list.containsAll(Arrays.asList(new Integer[] {1, 5})));
		
		ListIterator<Integer> iterator = list.listIterator();
		Assert.assertEquals(0, iterator.nextIndex());
		Assert.assertEquals(-1, iterator.previousIndex());
		Assert.assertEquals(true, iterator.hasNext());
		Assert.assertEquals(false, iterator.hasPrevious());
		Assert.assertEquals((Integer)1, iterator.next());
		Assert.assertEquals(1, iterator.nextIndex());
		Assert.assertEquals(0, iterator.previousIndex());
		Assert.assertEquals(true, iterator.hasNext());
		Assert.assertEquals(true, iterator.hasPrevious());
		Assert.assertEquals((Integer)1, iterator.previous());
		Assert.assertEquals(0, iterator.nextIndex());
		
		iterator = list.listIterator(4);
		Assert.assertEquals(4, iterator.nextIndex());
		Assert.assertEquals(3, iterator.previousIndex());
		Assert.assertEquals(false, iterator.hasNext());
		Assert.assertEquals(true, iterator.hasPrevious());
		Assert.assertEquals((Integer)4, iterator.previous());
		Assert.assertEquals(3, iterator.nextIndex());
		Assert.assertEquals(2, iterator.previousIndex());
		Assert.assertEquals(true, iterator.hasNext());
		Assert.assertEquals(true, iterator.hasPrevious());
		Assert.assertEquals((Integer)4, iterator.next());
		Assert.assertEquals(4, iterator.nextIndex());
	}
	
}
