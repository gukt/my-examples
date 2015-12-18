package net.bafeimao.examples.web.util;

import net.bafeimao.examples.web.domain.User;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	public void validate(Object obj, Errors e) {
		ValidationUtils.rejectIfEmpty(e, "name", "aafdafdsafs");

		User u = (User) obj;
		if (u != null) {
			if (u.getName() == null) {
				e.rejectValue("name", "user.name.required");
			}

			if (u.getEmail() == null) {
				e.rejectValue("email", "user.email.required");
			}
		}
	}

}
