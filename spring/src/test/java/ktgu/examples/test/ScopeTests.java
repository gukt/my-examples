package ktgu.examples.test;

import ktgu.examples.spring.scope.Bar;
import ktgu.examples.spring.scope.Baz;
import ktgu.examples.spring.scope.Foo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by ktgu on 15/8/19.
 */
@ContextConfiguration(locations = {"classpath:context-scope.xml"})
public class ScopeTests extends AbstractJUnit4SpringContextTests {

    @Autowired
    AbstractApplicationContext context;


    @Test
    public void testSingletonAndPrototypeScopes() {
        Foo foo1 = context.getBean(Foo.class);
        Foo foo2 = context.getBean(Foo.class);
        Assert.assertEquals(foo1, foo2);

        Bar bar1 = context.getBean(Bar.class);
        Bar bar2 = context.getBean(Bar.class);
        Assert.assertNotEquals(bar1, bar2);
    }

    @Test
    public void testCustomThreadScope() {
        final Baz[] arr = new Baz[3];

        final ApplicationContext ctx = this.context;

        // 开启两个线程分别获取Baz实例

       new Thread() {
            @Override
            public void run() {
                arr[0] = ctx.getBean(Baz.class);
                arr[1] = ctx.getBean(Baz.class);
            }
        }.start();

       new Thread() {
            @Override
            public void run() {
                arr[2] = ctx.getBean(Baz.class);
            }
        }.start();


        // 检查是否在同一个线程内返回共享实例，而不同的线程会生成不同的bean实例
        Assert.assertEquals(arr[0], arr[1]);
        Assert.assertNotEquals(arr[1], arr[2]);
    }
}
