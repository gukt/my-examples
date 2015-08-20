package ktgu.examples.spring.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ktgu on 15/8/17.
 */
public class Main {
    public static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath*:context-scope.xml");


        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        Foo foo1 = new Foo();
        beanFactory.registerSingleton("foo111", foo1);
        Foo foo2 = context.getBean("foo111", Foo.class);
        System.out.print(foo1==foo2);


        context.close();
    }
}
