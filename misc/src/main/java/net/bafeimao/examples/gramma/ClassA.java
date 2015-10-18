package net.bafeimao.examples.gramma;

public class ClassA {
	private final String NAME = "KTGU";
	private final static String UNVALUED;
	private final int a;
	private final int b;

	static {
		UNVALUED = "aaa";
	}

	{
		a = 1;
		b = 2;
	}

	public ClassA() {
		System.out.println(NAME);
		System.out.println(UNVALUED);
	}

	public void method1() {

	}
}
