package ktgu.examples.spring.annotationBased;

 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 import org.springframework.context.ApplicationContext;
 import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by ktgu on 15/8/17.
 */
public class Main {
    public static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("context.xml");

        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath*:context-annotation-based.xml");

        //Student studnet = context.getBean(Student.class );

        Foo foo = context.getBean(Foo.class);
        foo.bar();

        context.close();
    }
}
