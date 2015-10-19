package net.bafeimao.examples.test;

 
 
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassLoaderTest {
	private static Logger LOGGER = LoggerFactory.getLogger(ClassLoaderTest.class);

	@Test
	public void testPrintClassLoaderInCurrentEnvironment() {
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		// sun.misc.Launcher$AppClassLoader
		System.out.println(cl.getClass().getName());

		// 第一次输出sun.misc.Launcher$ExtClassLoader表明AppClassLoader的父ClassLoader是ExtClassLoader，
		// 第二次輸出null，表明ExtClassLoader没有parent（换句话说就是，ExtClassLoader的父亲是JVM的BootstrapClassLoader，因为它并非是Java类，因此为这里为null）
		while (cl != null) {
			cl = cl.getParent();
 			System.out.println(cl);
		}

		try {
			// 这里输出null，隐含的语义是表示该核心类Object是由BootstrapClassLoader装载的
			Class<?> c = Class.forName("java.lang.Object");
			System.out.println("ClassLoader: " + c.getClassLoader());

			// 我们自定义的'Foo'类是由'sun.misc.Launcher$AppClassLoader'装载的
			c = Class.forName(Foo.class.getName());
			System.out.println("ClassLoader: " + c.getClassLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class Foo {}
}
