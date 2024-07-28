package com.oss.unist.hr.service.implementation;


import com.oss.unist.hr.model.User;
import com.oss.unist.hr.model.enums.Role;
import com.oss.unist.hr.repository.UserRepository;
import com.oss.unist.hr.service.UserService;
import com.oss.unist.hr.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Optional;
import java.util.UUID;
import com.oss.unist.hr.service.UserService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;


	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Override
	public String processLogin(String email, HttpSession session, Model model, Role role, RedirectAttributes redirectAttributes) {
		try {
			logger.info("Attempting login for: {}", email);

			UUID userID = userRepository.findUserIdByEmail(email);

			Optional<User> userOptional = userRepository.findByEmail(email);
			User user = userOptional.orElse(null);

			Optional<String> usernameOptional = userRepository.findUsernameByEmail(email);
			String username = usernameOptional.orElse(null);

			if (role == Role.USER) {
				session.setAttribute("username", username);
				session.setAttribute("role", user.getRole());
				session.setAttribute("email", user.getEmail());
				session.setAttribute("country", user.getCountry());
				session.setAttribute("userId", user.getId());
				logger.info("User login successful. Redirecting to dashboard for user: {}", userID);
				return "redirect:/dashboard";
			}

			if (role == Role.ADMIN) {
				session.setAttribute("userId", userID);
				session.setAttribute("username", username);
				session.setAttribute("role", user.getRole());
				session.setAttribute("email", user.getEmail());
				session.setAttribute("country", user.getCountry());
				session.setAttribute("userId", user.getId());
				logger.info("Admin login successful. Redirecting to all_users_page for admin: {}", userID);
				return "redirect:/admin/users";
			}
			logger.info("Not recognized as USER or ADMIN!");
			return "redirect:/login";

		} catch (RuntimeException e) {
			logger.error("Login failed. Error: {}", e.getMessage());
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "/login/login_page";
		}
	}
}