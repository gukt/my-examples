package net.bafeimao.examples.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HashMapBenchmark {
	
	@Test
	public void test1() {
		Map<Integer, Object	> map = new HashMap<Integer, Object>();
		for(int i = 0; i < 10000;i++){
			map.put(i, "String_" + i);
		}
		
		long startTime = System.nanoTime();
		System.out.println(map.get(50000));
		System.out.println(System.nanoTime() - startTime);
		
	}

}
