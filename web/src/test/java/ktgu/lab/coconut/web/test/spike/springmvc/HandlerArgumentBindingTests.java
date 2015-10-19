package ktgu.lab.coconut.web.test.spike.springmvc;

import ktgu.lab.coconut.web.domain.User;
import ktgu.lab.coconut.web.util.StreamUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class HandlerArgumentBindingTests {
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new SampleController())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void testBindRequestAndResponse() throws Exception {
        String content = "hello";
        this.mockMvc.perform(get("/bind/requestResponse").content(content))
                .andExpect(status().isOk())
                .andExpect(content().string(content))
                .andExpect(view().name("success"));
    }

    @Test
    public void testBindInputAndOutputStream() throws Exception {
        String requestBody = "hello spring mvc";
        this.mockMvc.perform(get("/bind/inputOutputStream").content(requestBody))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testBindReaderAndWriter() throws Exception {
        this.mockMvc.perform(get("/bind/readerWriter"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testBindWebRequest() throws Exception {
        this.mockMvc.perform(get("/bind/webRequest"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testBindSession() throws Exception {
        this.mockMvc.perform(get("/bind/session").sessionAttr("sessAttr1", "sessValue1"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testBindHttpEntity1() throws Exception {
        this.mockMvc.perform(get("/bind/httpEntity1?name=ktgu&email=29283212@qq.com").content("aaa"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testBindHttpEntity2() throws Exception {
        this.mockMvc.perform(get("/bind/httpEntity2?name=ktgu&email=29283212@qq.com").content("aaa"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        // .andExpect(MockMvcResultMatchers.model().hasNoErrors());
    }

    @Test
    public void testBindUserByRequestPrams() throws Exception {
        this.mockMvc.perform(get("/bind/user?id=1&name=ktgu&email=29283212@qq.com&mobile=110"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testBindRequestParam() throws Exception {
        // 以下两种方式都可以映射到name参数
        this.mockMvc.perform(get("/bind/requestParam/?name=ktgu"));
        this.mockMvc.perform(get("/bind/requestParam?name=123"));
    }

    @Test
    public void testBindRequestHeader() throws Exception {
        this.mockMvc.perform(get("/bind/requestHeader")
                .header("User-Agent", "Mozilla/5.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testBindErrors() throws Exception {
        this.mockMvc.perform(get("/bind/errors"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    public void testBindRequest() throws Exception {
        this.mockMvc.perform(get("/bind/requestBody").content("aaa"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testLocale() throws Exception {
        this.mockMvc.perform(get("/bind/locale"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testPrincipal() throws Exception {
        this.mockMvc.perform(get("/bind/principal"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testModelAndMap() throws Exception {
        this.mockMvc.perform(get("/bind/modelAndMap"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testBindMisc() throws Exception {
        this.mockMvc.perform(get("/bind/others"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Controller
    @RequestMapping("/bind")
    static class SampleController {

        @RequestMapping("/requestResponse")
        public String requestAndResponse(ServletRequest req1, HttpServletRequest req2, ServletResponse resp1,
                                         HttpServletResponse resp2) throws IOException {

            // 这里的ServletRequest和HttpServletRequest实际上是同一个对象
            Assert.isTrue(req1 == req2);

            // 这里的ServletResponse和HttpServletResponse也是同一个对象
            Assert.isTrue(resp1 == resp2);

            // 直接获取请求字节然后通过Writer将内容写到输出流
            InputStream is = req1.getInputStream();
            String content = StreamUtils.copyToString(is);
            System.out.println(content);
            resp1.getWriter().write(content); // 这种方式输出后的内容类型（contentType）是不可知的

            return "success";
        }

        @RequestMapping("/inputOutputStream")
        public String inputStreamAndOutputStream(InputStream is, OutputStream os) throws IOException {
            return "success";
        }

        @RequestMapping("/readerWriter")
        public String readerAndWriter(Reader reader, Writer writer) throws IOException {
            return "success";
        }

        @RequestMapping("/webRequest")
        public String webRequestAndNative(WebRequest webRequest, NativeWebRequest nativeWebRequest) {
            webRequest.setAttribute("name", "value", RequestAttributes.SCOPE_REQUEST);
            return "success";
        }

        @RequestMapping("/session")
        public String session(HttpSession session, Model model) throws IOException {
            Assert.notNull(session.getAttribute("sessAttr1"));
            return "success";
        }

        @RequestMapping(value = "/httpEntity1", method = RequestMethod.GET)
        public String entity(HttpEntity<String> requestEntity) {
            return "success";
        }

        @RequestMapping(value = "/httpEntity2", method = RequestMethod.GET)
        public ResponseEntity<List<User>> entity2(HttpEntity<String> req, User user1) {
            System.out.println("req headers=" + req.getHeaders() + ", reqBody=" + req.getBody());

            user1.setName(user1.getName() + ",server");
            List<User> list = new ArrayList<User>();
            list.add(user1);

            User user2 = new User();
            user2.setId(2L);
            user2.setName("user2");
            list.add(user2);

            ResponseEntity<List<User>> ret = new ResponseEntity<List<User>>(list, HttpStatus.OK);
            return ret;
        }

        /**
         * 框架会自动根据请求参数组装User对象作为方法参数 /m6?id=1&name=ktgu&email=29283212@qq.com&mobile=110
         */
        @RequestMapping("/user")
        public String commandObject(User user, Model model, BindingResult result) throws IOException {
            return "success";
        }

        @RequestMapping("/requestParam")
        public String requestParameter(@RequestParam String name) {
            return "success";
        }

        @RequestMapping("/requestHeader")
        public String requestHeader(@RequestHeader("User-Agent") String userAgent) {
            return "success";
        }

        @RequestMapping(value = "/requestBody")
        public String requestBody(@RequestBody String body, Writer writer) throws IOException {
            writer.write(body);
            return "success";
        }

        @RequestMapping(value = "/errors")
        public String errors(User user, BindingResult result, Errors errors, Model model) {
            return "error";
        }

        @RequestMapping("/others")
        public String others(Locale locale, Principal principal, ModelMap model) {
            return "success";
        }

        @RequestMapping("/modelAndMap")
        public String modelAndMap(Model model, ModelMap modelMap, Map<?, ?> map) {
            System.out.println(model);
            System.out.println(modelMap);
            System.out.println(map);

            assertEquals(model, modelMap);
            assertEquals(modelMap, map);

            return "success";
        }


        @RequestMapping("/locale")
        public String locale(Locale locale) {
            return "success";
        }

        @RequestMapping("/principal")
        public String principal(Principal principal) {
            return "success";
        }
    }

}
