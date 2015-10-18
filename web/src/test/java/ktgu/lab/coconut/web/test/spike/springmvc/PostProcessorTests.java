package ktgu.lab.coconut.web.test.spike.springmvc;

import ktgu.lab.coconut.web.service.UserService;
import ktgu.lab.coconut.web.test.common.TransactionalSpringContextTestsBase;
import org.junit.Test;

public class PostProcessorTests extends TransactionalSpringContextTestsBase {

    @Test
    public void testPostProcessor() {
        UserService userService = this.applicationContext.getBean(UserService.class);
        System.out.println(userService);
    }
}
