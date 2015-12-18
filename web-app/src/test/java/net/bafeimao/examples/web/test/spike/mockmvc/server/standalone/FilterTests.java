package net.bafeimao.examples.web.test.spike.mockmvc.server.standalone;

import net.bafeimao.examples.web.test.common.Person;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class FilterTests {

    /**
     * 测试过滤器，完成MVC处理请求
     *
     * @throws Exception
     */
    @Test
    public void testWhenFiltersCompleteMvcProcessesRequest() throws Exception {
        standaloneSetup(new PersonController()).addFilters(new ContinueFilter()).build()
                .perform(post("/persons").param("name", "Andy"))
                .andDo(MockMvcResultHandlers.print()) // 打印返回结果信息
                .andExpect(status().isFound()) // 被重定向了
                .andExpect(redirectedUrl("/person/1")) // 断言被重定向的URL
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("id"))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attribute("message", "success!"));
    }

    /**
     * 请求被RedirectFilter重定向到"/login"，而并没有到达Controller去处理
     *
     * @throws Exception
     */
    @Test
    public void testFiltersProcessRequest() throws Exception {
        standaloneSetup(new PersonController())
                .addFilters(new ContinueFilter(), new RedirectFilter()).build()
                .perform(post("/persons").param("name", "Andy"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isFound()) // 被重定向了
                .andExpect(redirectedUrl("/login"));
    }

    /**
     * 测试‘按模式匹配过滤‘
     *
     * @throws Exception
     */
    @Test
    public void testFilterMappedBySuffix() throws Exception {
        standaloneSetup(new PersonController())
                .addFilter(new RedirectFilter(), "*.html").build()
                .perform(post("/persons.html").param("name", "Andy"))
                .andExpect(redirectedUrl("/login"));
    }

    /**
     * 测试’按精确地址映射匹配'
     *
     * @throws Exception
     */
    @Test
    public void testFilterWithExactMapping() throws Exception {
        standaloneSetup(new PersonController())
                .addFilter(new RedirectFilter(), "/p", "/persons").build()
                .perform(post("/persons").param("name", "Andy"))
                .andExpect(redirectedUrl("/login"));
    }

    /**
     * 测试‘RedirectFilter没有过滤到/persons的情况‘,因为persons没有被匹配到，所以被放过
     *
     * @throws Exception
     */
    @Test
    public void testFilterSkipped() throws Exception {
        standaloneSetup(new PersonController())
                .addFilter(new RedirectFilter(), "/p", "/person").build()
                .perform(post("/persons").param("name", "Andy"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/person/1"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("id"))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attribute("message", "success!"));
    }

    @Test
    public void testFilterWrapsRequestResponse() throws Exception {
        standaloneSetup(new PersonController())
                .addFilters(new WrappingRequestResponseFilter()).build()
                .perform(post("/user"))
                .andExpect(model().attribute("principal", WrappingRequestResponseFilter.PRINCIPAL_NAME));
    }

    //@Controller
    static class PersonController {
        @RequestMapping(value = "/persons", method = RequestMethod.POST)
        public String save(@Valid Person person, Errors errors, RedirectAttributes redirectAttrs) {
            if (errors.hasErrors()) {
                return "person/add";
            }
            redirectAttrs.addAttribute("id", "1");
            redirectAttrs.addFlashAttribute("message", "success!");
            return "redirect:/person/{id}"; // 这里会自动替换{}表示的参数值
        }

        @RequestMapping(value = "/user")
        public ModelAndView user(Principal principal) {
            return new ModelAndView("user/view", "principal", principal.getName());
        }

        @RequestMapping(value = "/forward")
        public String forward() {
            return "forward:/persons";
        }
    }

    /**
     * 该Filter什么事都不做，只是继续调用下一个Filter
     */
    private class ContinueFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            filterChain.doFilter(request, response);
        }
    }

    private static class WrappingRequestResponseFilter extends OncePerRequestFilter {

        public static final String PRINCIPAL_NAME = "WrapRequestResponseFilterPrincipal";

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            filterChain.doFilter(new HttpServletRequestWrapper(request) {
                @Override
                public Principal getUserPrincipal() {
                    return new Principal() {
                        public String getName() {
                            return PRINCIPAL_NAME;
                        }
                    };
                }
            }, new HttpServletResponseWrapper(response));
        }
    }

    private class RedirectFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            response.sendRedirect("/login");
        }
    }
}