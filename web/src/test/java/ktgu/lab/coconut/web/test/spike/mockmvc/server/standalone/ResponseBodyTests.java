package ktgu.lab.coconut.web.test.spike.mockmvc.server.standalone;

import ktgu.lab.coconut.web.test.common.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ResponseBodyTests {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new PersonController())
                .alwaysDo(MockMvcResultHandlers.print()).build();
    }

    @Test
    public void testJsonResponse() throws Exception {
        mockMvc.perform(get("/person/Lee").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name").value("Lee"));
    }

    @Test
    public void testJsonParameterAndResponse() throws Exception {
        String requestBody = "{\"name\":\"ktgu\"}";
        mockMvc.perform(post("/person.json").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("ktgu"));
    }

    @Test
    public void testJsonParameterAndResponseWithError() throws Exception {
        String errorBody = "{name:no-quotes}";
        MvcResult result = mockMvc.perform(post("/person.json").content(errorBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResolvedException().getMessage());
        Assert.assertTrue(HttpMessageNotReadableException.class.isAssignableFrom(
                result.getResolvedException().getClass()));
    }

    @Test
    public void testXmlParameterAndResponse() throws Exception {
        String requestBody = "<person><name>ktgu</name></person>";
        mockMvc.perform(post("/person.xml").content(requestBody)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/person/name/text()").string("ktgu"));
    }

    @Test
    public void testXmlParameterAndResponseWithError() throws Exception {
        String errorBody = "<person><name>ktgu</name>";
        MvcResult result = mockMvc.perform(post("/person.xml").content(errorBody)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assert.assertTrue(HttpMessageNotReadableException.class.isAssignableFrom(result.getResolvedException()
                .getClass()));
    }

    class PersonController {

        @RequestMapping(value = "/person/{name}")
        @ResponseBody
        public Person getPerson(@PathVariable String name) {
            Person person = new Person(name);
            return person;
        }

        @RequestMapping(value = "person.json", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        @ResponseBody
        public Person parsePersonFromJson(@RequestBody Person person) {
            return person;
        }

        @RequestMapping(value = "person.xml", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
        @ResponseBody
        public Person parsePersonFromXml(@RequestBody Person person) {
            person.setName("ktgu");
            return person;
        }
    }

}