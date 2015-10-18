package ktgu.lab.coconut.web.test.spike.springmvc;

import ktgu.lab.coconut.web.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

//@WebAppConfiguration
//public class ModelAttributeTests extends TransactionalSpringContextTestsBase {
public class ModelAttributeTests {
//    @Autowired
//    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mockMvc = standaloneSetup(new SampleController())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void testModelAttr1() throws Exception {
        this.mockMvc.perform(get("/attr1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attribute("attr1", "value1"))
                .andExpect(MockMvcResultMatchers.model().attribute("attr2", "value2"))
                .andExpect(MockMvcResultMatchers.model().attribute("attr3", "value3"));
    }

    @Test
    public void testModelAttr2() throws Exception {
        this.mockMvc.perform(get("/attr2?name=ktgu"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
    }

    @Test
    public void testModelAttr4() throws Exception {
        this.mockMvc.perform(get("/attr4"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attribute("modAttr4", "value4"))
                .andExpect(MockMvcResultMatchers.view().name("spike/model/attr4"));
    }

    // @Test
    // public void testModelAttr3() throws Exception {
    // // 这里为了测试requestAttr和param指定的参数是不一样的
    // this.mockMvc.perform(get("/spike/model/attr3?name=ktgu"))
    // .andDo(MockMvcResultHandlers.print())
    // .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("user"));
    // }

    @Controller
    static class SampleController {

        /**
         * 用法一：标注在控制器的非Handler方法上（返回viod的情况)
         * <p/>
         * 在这种用法下，我们可以利用参数model，将需要添加的attribute手动的添加到model中
         */
        @ModelAttribute
        public void addModelAttr1(Model model) {
            model.addAttribute("attr1", "value1");
            model.addAttribute("attr2", "value2");
            model.addAttribute("attr3", "value3");
        }

        /**
         * 用法二：标注在控制器的非hanglder方法上(方法有返回值）
         * <p/>
         * 这种情况会根据返回值类型的首字母小写作为属性名称，本例执行完成后会在model中添加一个名称为“user"的属性
         */
        @ModelAttribute
        public User addModelAttr2() {
            return new User();
        }

        /**
         * 用法三：标注在控制器的非handler方法上（有返回值，有显式指定属性名）
         * 指定了attribute名称：modAttr3 => modValue3
         */
        @ModelAttribute("attr3")
        public String addModelAttr3() {
            return "value3-v2";
        }

        /**
         * 这是@ModelAttribute和@RequestMapping共同作用的情况：
         * 这种情况下，方法的返回值将作为modelValue,而不是视图名称，视图名称由RequestMapping决定(本例中是 "spike/model/attr4")
         */
        @RequestMapping("/attr4")
        @ModelAttribute("attr4")
        public String modelAttr4() {
            return "value4";
        }

        /**
         * 当@ModelAttribute作用于handler参数时，表示从model中取出对应的attribute，
         * 本例中从model中取出属性名称为modAttr3的属性并将其绑定到attr参数上（modAttr3是在该handler之前方法{@link #modelAttr3}中定义的}
         */
        @RequestMapping("/attr1")
        public String usage1(@ModelAttribute("modAttr3") String attr, User user, Model model, BindingResult result) {

            // 此时的model值为：{modAttr1=modValue1, string=modValue2, modAttr3=modValue3,
            // org.springframework.validation.BindingResult.modAttr3=org.springframework.validation.BeanPropertyBindingResult:
            // 0 errors}

            return "success";
        }

        @RequestMapping("/attr2")
        public String usage2(@ModelAttribute User user) {
            return "success";
        }

    }

}
