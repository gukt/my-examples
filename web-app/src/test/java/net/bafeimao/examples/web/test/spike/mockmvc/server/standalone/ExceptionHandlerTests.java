package net.bafeimao.examples.web.test.spike.mockmvc.server.standalone;

import org.junit.Test;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by ktgu on 15/6/10.
 */
public class ExceptionHandlerTests {

    @Test
    public void testExceptionHandlerMethod() throws Exception {
        standaloneSetup(new PersonController()).build()
                .perform(get("/person/Clyde"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("errorView"));
    }


    private static class PersonController {

        @RequestMapping(value = "/person/{name}", method = RequestMethod.GET)
        public String show(@PathVariable String name) {
            if (name.equals("Clyde")) {
                throw new IllegalArgumentException("Black listed");
            }
            return "person/show";
        }

        @ExceptionHandler
        public String handleException(IllegalArgumentException exception) {
            return "errorView";
        }
    }
}
