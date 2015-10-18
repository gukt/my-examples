package net.bafeimao.examples.test.java;

import org.junit.Test;

import com.google.common.base.Objects;

public class StringTests {

	@Test
	public void test1() {
		StringBuilder sb = new StringBuilder();
		sb.append("aaa");
		sb.append(1);
		sb.append(true);
		System.out.println(sb.toString());
	}

	@Test
	public void testStringBufferCapacity() {
		// 初始化一个16字节长度的StringBuffer
		StringBuffer buf1 = new StringBuffer();
		System.out.println(buf1.capacity()); // 16

		// 出事后一个长度512字节的缓冲区
		StringBuffer buf2 = new StringBuffer(512);
		System.out.println(buf2.capacity()); // 512

		String str = "Hello world! I am StringBuffer";
		StringBuffer buf3 = new StringBuffer(str);
		System.out.println(buf3.capacity()); // 46
	}

	@Test
	public void test3() {
		
	}
}
