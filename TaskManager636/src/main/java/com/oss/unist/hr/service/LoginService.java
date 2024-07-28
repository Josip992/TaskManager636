package com.oss.unist.hr.service;

import com.oss.unist.hr.model.enums.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


public interface LoginService {
	String processLogin(String email , HttpSession session, Model model, Role role, RedirectAttributes redirectAttributes);
}
