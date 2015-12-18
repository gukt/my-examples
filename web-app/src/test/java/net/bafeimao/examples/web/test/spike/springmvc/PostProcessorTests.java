package net.bafeimao.examples.web.test.spike.springmvc;

import net.bafeimao.examples.web.service.UserService;
import net.bafeimao.examples.web.test.common.TransactionalSpringContextTestsBase;
import org.junit.Test;

public class PostProcessorTests extends TransactionalSpringContextTestsBase {

    @Test
    public void testPostProcessor() {
        UserService userService = this.applicationContext.getBean(UserService.class);
        System.out.println(userService);
    }
}
