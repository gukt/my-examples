package ktgu.lab.coconut.web.test.spike.mockmvc.server.standalone;

import ktgu.lab.coconut.web.test.common.Person;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AsyncTests {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new SampleController())
                // .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void testAsyncCallable() throws Exception {
        // Callable
        MvcResult result = mockMvc.perform(get("/async1?name=ktgu"))
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(CoreMatchers.instanceOf(Person.class)))
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("ktgu"));
    }

    @Test
    public void testAsyncDeferred() throws Exception {
        // DeferredResult
        MvcResult result = mockMvc.perform(get("/async2?name=ktgu")) // 执行请求
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(CoreMatchers.instanceOf(Person.class))) // 默认会等10秒超时
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("ktgu"));
    }

    //@Controller
    public static class SampleController {

        @RequestMapping(value = "/async1", produces = MediaType.APPLICATION_JSON_VALUE)
        @ResponseBody
        public Callable<Person> async1(final Person person) {
            return new Callable<Person>() {
                @Override
                public Person call() throws Exception {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                    }
                    return person;
                }
            };
        }

        @RequestMapping(value = "/async2", produces = MediaType.APPLICATION_JSON_VALUE)
        @ResponseBody
        public DeferredResult<Person> async2(final Person person) {
            final DeferredResult<Person> result = new DeferredResult<Person>();
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                    }
                    result.setResult(person);
                }
            }.start();
            return result;
        }
    }
}
