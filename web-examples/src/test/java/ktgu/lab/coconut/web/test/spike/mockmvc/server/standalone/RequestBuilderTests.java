package ktgu.lab.coconut.web.test.spike.mockmvc.server.standalone;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by ktgu on 15/6/10.
 */
public class RequestBuilderTests {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new SampleController())
                .defaultRequest(get("/").accept(MediaType.TEXT_PLAIN))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysExpect(status().isOk()).build();
    }

    @Test
    public void fooHeader() throws Exception {
        this.mockMvc.perform(get("/")
                .with(headers().foo("a=b"))) // 添加RequestPostProcessor，同时添加在header中添加foo='a=b'
                .andExpect(content().string("Foo"));
    }

    @Test
    public void barHeader() throws Exception {
        this.mockMvc.perform(get("/").with(headers().bar("a=b")))
                .andExpect(content().string("Bar"));
    }

    private static HeaderRequestPostProcessor headers() {
        return new HeaderRequestPostProcessor();
    }


    /**
     * Implementation of {@code RequestPostProcessor} with additional request
     * building methods.
     */
    private static class HeaderRequestPostProcessor implements RequestPostProcessor {

        private HttpHeaders headers = new HttpHeaders();

        public HeaderRequestPostProcessor foo(String value) {
            this.headers.add("Foo", value);
            return this;
        }

        public HeaderRequestPostProcessor bar(String value) {
            this.headers.add("Bar", value);
            return this;
        }

        public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
            for (String headerName : this.headers.keySet()) {
                request.addHeader(headerName, this.headers.get(headerName));
            }
            return request;
        }
    }

    @RequestMapping("/")
    private static class SampleController {

        @RequestMapping(headers = "Foo")
        @ResponseBody
        public String handleFoo() {
            return "Foo";
        }

        @RequestMapping(headers = "Bar")
        @ResponseBody
        public String handleBar() {
            return "Bar";
        }
    }

}
