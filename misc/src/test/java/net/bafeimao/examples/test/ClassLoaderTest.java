package net.bafeimao.examples.test;

 
 
import org.junit.Test;

public class ClassLoaderTest {

	@Test
	public void testPrintClassLoaderInCurrentEnviornment() {
		Class<?> c;
		ClassLoader cl;
		cl = ClassLoader.getSystemClassLoader();
		System.out.println(cl); // 该行输出表明当前系统类初始化器（SystemClassLoader）为：sun.misc.Launcher$AppClassLoader
		
		// 第一次输出：sun.misc.Launcher$ExtClassLoader表明AppClassLoader的父ClassLoader是ExtClassLoader，
		// 第二次輸出：null，表明ExtClassLoader的父亲是JVM的bootstrap ClassLoader
		while (cl != null) {
			cl = cl.getParent();  
 			System.out.println(cl);
		}
		try {
			// 核心类java.lang.Object是由bootstrap装载的
			c = Class.forName("java.lang.Object");
			cl = c.getClassLoader();
			System.out.println("java.lang.Object's loader is  " + cl);
			c = Class.forName("ktgu.lab.potato.samples.spike.ClassLoaderTest");
			cl = c.getClassLoader();
			System.out.println("LoaderSample1's loader is  " + cl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSomthingLoadedByBootstrapClassLoader(){
		// URL[] urls=sun.misc.Launcher.getBootstrapClassPath().getURLs();
		// for (int i = 0; i < urls.length; i++) {
		// System.out.println(urls[i]);
		// }
	}
}
