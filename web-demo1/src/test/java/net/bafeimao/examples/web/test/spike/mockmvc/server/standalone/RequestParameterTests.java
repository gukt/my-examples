package net.bafeimao.examples.web.test.spike.mockmvc.server.standalone;

import net.bafeimao.examples.web.test.common.Person;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Tests demonstrating the use of request parameters.
 *
 * @author Rossen Stoyanchev
 */
public class RequestParameterTests {

    @Test
    public void testQueryParameter() throws Exception {

        standaloneSetup(new PersonController()).build()
                .perform(get("/search?name=George").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name").value("George"));
    }


    //@Controller
    private class PersonController {

        @RequestMapping(value = "/search")
        @ResponseBody
        public Person get(@RequestParam String name) {
            Person person = new Person(name);
            return person;
        }
    }
}


