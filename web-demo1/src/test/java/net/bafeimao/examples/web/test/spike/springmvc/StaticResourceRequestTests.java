package net.bafeimao.examples.web.test.spike.springmvc;

import net.bafeimao.examples.web.test.common.TransactionalSpringContextTestsBase;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
public class StaticResourceRequestTests extends TransactionalSpringContextTestsBase {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void testStaticResource() throws Exception {
        mockMvc.perform(get("/assets/js/bootstrap.js"))
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.anything("var")));

    }

    @Test
    public void testStaticResourceNotFound() throws Exception {
        mockMvc.perform(get("/assets/js/bootstrap-miss.js"))
                .andExpect(status().isNotFound());
    }
}
