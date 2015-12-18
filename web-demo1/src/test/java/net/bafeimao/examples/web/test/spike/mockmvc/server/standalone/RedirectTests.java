package net.bafeimao.examples.web.test.spike.mockmvc.server.standalone;

import net.bafeimao.examples.web.test.common.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by ktgu on 15/6/10.
 */
public class RedirectTests {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new PersonController()).build();
    }

    @Test
    public void save() throws Exception {
        this.mockMvc.perform(post("/persons").param("name", "Andy"))
                .andDo(MockMvcResultHandlers.print()) // print something
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/persons/Joe"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("name"))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attribute("message", "success!"));
    }

    @Test
    public void saveWithErrors() throws Exception {
        this.mockMvc.perform(post("/persons"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("persons/add"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("person"))
                .andExpect(flash().attributeCount(0));
    }

    @Test
    public void getPerson() throws Exception {
        // 发送get请求时带上的flashAttr会自动绑定到响应的model中
        this.mockMvc.perform(get("/persons/Joe").flashAttr("message", "success!"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("persons/index"))
                .andExpect(model().size(2)) // person & message(它从flashAttr中来）
                .andExpect(model().attribute("person", new Person("Joe")))
                .andExpect(model().attribute("message", "success!"))
                .andExpect(flash().attributeCount(0));
    }


    //@Controller
    private static class PersonController {

        @RequestMapping(value = "/persons/{name}", method = RequestMethod.GET)
        public String getPerson(@PathVariable String name, Model model) {
            model.addAttribute(new Person(name));
            return "persons/index";
        }

        @RequestMapping(value = "/persons", method = RequestMethod.POST)
        public String save(@Valid Person person, Errors errors, RedirectAttributes redirectAttrs) {
            if (errors.hasErrors()) {
                return "persons/add";
            }
            redirectAttrs.addAttribute("name", "Joe");
            redirectAttrs.addFlashAttribute("message", "success!");
            return "redirect:/persons/{name}";
        }
    }
}