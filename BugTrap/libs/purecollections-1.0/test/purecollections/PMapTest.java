package purecollections;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import purecollections.PList;
import purecollections.PMap;

public class PMapTest {

	@Test
	public void testPMapLinear() {
		PMap<Integer, Integer> map = PMap.empty();
		for (int i = 0; i < 1000000; i++)
			map = map.plus(i, i);
		for (int i = 0; i < 1000000; i++) {
			Assert.assertEquals(i, (int)map.get(i));
			map = map.minus(i);
		}
		Assert.assertSame(PMap.empty(), map);
	}
	
	@Test
	public void testPMapRandom0() {
		Random random = new Random(0);
		PMap<Long, Long> map = PMap.empty();
		for (int i = 0; i < 1000000; i++) {
			long k = random.nextLong();
			map = map.plus(k, k);
		}
		random = new Random(0);
		for (int i = 0; i < 1000000; i++) {
			long k = random.nextLong();
			Assert.assertEquals(k, (long)map.get(k));
			map = map.minus(k);
		}
		Assert.assertSame(PMap.empty(), map);
	}
	
	@Test
	public void testPMapRandom() {
		long seed = System.currentTimeMillis();
		Random random = new Random(seed);
		PMap<Long, Long> map = PMap.empty();
		for (int i = 0; i < 1000000; i++) {
			long k = random.nextLong();
			map = map.plus(k, k);
		}
		random = new Random(seed);
		for (int i = 0; i < 1000000; i++) {
			long k = random.nextLong();
			Assert.assertEquals(k, (long)map.get(k));
			map = map.minus(k);
		}
		Assert.assertSame(PMap.empty(), map);
	}
	
	<K, V> Map.Entry<K, V> entry(K k, V v) {
		return new AbstractMap.SimpleImmutableEntry<K, V>(k, v);
	}
	
	<E> Set<E> toSet(Collection<E> c) {
		return new HashSet<E>(c);
	}
	
	<E> List<E> toArrayList(Collection<E> c) {
		return new ArrayList<E>(c);
	}
	
	@Test
	public void testSecondaryMethods() {
		PMap<Integer, Integer> map = PMap.<Integer, Integer>empty().plus(100, 1000).plus(200, 2000).plus(300, 3000);
		
		Assert.assertEquals("{200: 2000, 100: 1000, 300: 3000}", map.toString());
		Assert.assertEquals(map, PMap.<Integer, Integer>empty().plus(200, 2000).plus(100, 1000).plus(300, 3000));
		Assert.assertFalse(map.equals(PMap.<Integer, Integer>empty().plus(200, 2000).plus(100, 1000).plus(300, 3001)));
		Assert.assertEquals(map, new HashMap<Integer, Integer>(map));
		Assert.assertEquals(new HashMap<Integer, Integer>(map), map);
		Assert.assertFalse(map.equals(new HashMap<Integer, Integer>()));
		Assert.assertFalse(new HashMap<Integer, Integer>().equals(map));
		Assert.assertEquals(new HashMap<Integer, Integer>(map).hashCode(), map.hashCode());
		
		Assert.assertTrue(map.containsKey(200));
		Assert.assertFalse(map.containsKey(2000));
		Assert.assertTrue(map.containsValue(3000));
		Assert.assertFalse(map.containsValue(3001));
		
		PList<Map.Entry<Integer, Integer>> entries = PList.empty();
		entries = entries.plus(entry(100, 1000)).plus(entry(200, 2000)).plus(entry(300, 3000));
		
		Assert.assertEquals(toSet(entries), map.entrySet());
		Assert.assertEquals(toSet(entries), toSet(toArrayList(map.entrySet())));
		Assert.assertEquals(map.entrySet(), toSet(entries));
		Assert.assertEquals(toSet(toArrayList(map.entrySet())), toSet(entries));
		Assert.assertFalse(map.entrySet().isEmpty());
		Assert.assertFalse(map.keySet().isEmpty());
		Assert.assertFalse(map.values().isEmpty());
		Assert.assertTrue(map.keySet().contains(200));
		Assert.assertFalse(map.keySet().contains(201));
		
		Assert.assertEquals(toSet(PList.empty().plus(100).plus(200).plus(300)), map.keySet());
		Assert.assertEquals(toSet(PList.empty().plus(1000).plus(2000).plus(3000)), toSet(map.values()));
		
		Assert.assertEquals(PMap.empty().plus(10, 100).plus(20, 200).plus(30, 300), PMap.empty().plus(10, 100).plusAll(PMap.empty().plus(20, 200).plus(30, 300)));
	}
	
}
