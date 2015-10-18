package net.bafeimao.examples.gramma;

public class ClassB extends ClassA {
	public static int n1 = 1;
	public static int n2;

	private final int n3 = 3;
	private final int n4;
	private final int n5;

	static {
		n2 = 2;
	}

	{
		n4 = 4;
	}

	public ClassB() {
		n5 = 5;
	}

	public static void main(String[] args) {
		ClassB classB = new ClassB();
		System.out.println(classB);
	}
}
