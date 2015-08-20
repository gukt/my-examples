package ktgu.examples.spring.beanPostProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ktgu on 15/8/17.
 */
public class Main {
    public static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath*:context-bean-post-processor.xml");

        Foo foo =context.getBean(Foo.class );
        foo.bar();

        context.close();
    }
}
