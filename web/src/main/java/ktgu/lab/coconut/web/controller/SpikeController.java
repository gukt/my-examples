package ktgu.lab.coconut.web.controller;

import java.util.ArrayList;
import java.util.List;

import ktgu.lab.coconut.web.domain.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpikeController {

	@RequestMapping("/spike/jstl")
	public String tagUsage(Model model) {

		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setName("test" + i);
			user.setId(Integer.valueOf(i).longValue());
			users.add(user);
		}
		model.addAttribute("users", users);

		// Add another attribute
		User user = new User();
		user.setId(888L);
		user.setName("ktgu");
		user.setEmail("29283212@qq.com");
		model.addAttribute("user", user);

		return "spike/jstl";
	}
}
