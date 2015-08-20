package ktgu.examples.test;

import ktgu.examples.spring.hibernate.domain.User;
import ktgu.examples.spring.hibernate.UserService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

/**
 * Created by ktgu on 15/8/20.
 */
@ContextConfiguration(locations = {"classpath:context-hibernate.xml"})

public class SpringHibernateTests extends AbstractJUnit4SpringContextTests {
    static Logger logger = LoggerFactory.getLogger(SpringHibernateTests.class);

    @Autowired
    AbstractApplicationContext context;

    @Autowired
    private UserService userService;

    @Test
    public void test1(){
        List<User> users = userService.getUsers();
        logger.info("{}", users);
    }
}
