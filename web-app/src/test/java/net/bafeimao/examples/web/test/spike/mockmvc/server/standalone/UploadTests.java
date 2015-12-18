package net.bafeimao.examples.web.test.spike.mockmvc.server.standalone;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class UploadTests {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new SampleController())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void testFileUpload() throws Exception {
        byte[] bytes = "avatar".getBytes();
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload", 1L).file("avatar", bytes))
                .andExpect(model().attribute("avatar", bytes))
                .andExpect(view().name("success"));
    }

    //@Controller
    public static class SampleController {

        @RequestMapping(value = "/upload", method = RequestMethod.POST)
        public String upload(@RequestParam MultipartFile avatar, Model model) throws IOException {
            model.addAttribute("avatar", avatar.getBytes());
            return "success";
        }
    }
}
