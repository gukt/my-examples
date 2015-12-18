package net.bafeimao.examples.web.test.spike.mockmvc.server.standalone;

import net.bafeimao.examples.web.test.common.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ModelAssertionTests {

    private MockMvc mockMvc;

    @Before
    public void setup() {

        SampleController controller = new SampleController("a string value", 3, new Person("a name"));

        this.mockMvc = standaloneSetup(controller)
                .defaultRequest(get("/"))
                .alwaysExpect(status().isOk())
                .build();
    }

    @Test
    public void testAttributeEqualTo() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(model().attribute("integer", 3))
                .andExpect(model().attribute("string", "a string value"))
                .andExpect(model().attribute("integer", equalTo(3))) // Hamcrest...
                .andExpect(model().attribute("string", equalTo("a string value")));
    }

    @Test
    public void testAttributeExists() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(model().attributeExists("integer", "string", "person"))
                .andExpect(model().attribute("integer", notNullValue())) // Hamcrest...
                .andExpect(model().attribute("INTEGER", nullValue()));
    }

    @Test
    public void testAttributeHamcrestMatchers() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(model().attribute("integer", allOf(greaterThan(2), lessThan(4))))
                .andExpect(model().attribute("string", allOf(startsWith("a string"), endsWith("value"))))
                .andExpect(model().attribute("person", hasProperty("name", equalTo("a name"))));
    }

    @Test
    public void testHasErrors() throws Exception {
        mockMvc.perform(post("/persons")).andExpect(model().attributeHasErrors("person"));
    }

    @Test
    public void testHasNoErrors() throws Exception {
        mockMvc.perform(get("/")).andExpect(model().hasNoErrors());
    }

    //@Controller
    static class SampleController {

        private final Object[] values;

        public SampleController(Object... values) {
            this.values = values;
        }

        @RequestMapping("/")
        public String handle(Model model) {
            for (Object value : this.values) {
                model.addAttribute(value);
            }
            return "view";
        }

        @RequestMapping(value = "/persons", method = RequestMethod.POST)
        public String create(@Valid Person person, BindingResult result, Model model) {
            return "view";
        }
    }

}
