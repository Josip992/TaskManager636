package com.oss.unist.hr.security;


import com.oss.unist.hr.model.User;
import com.oss.unist.hr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);

		if(user.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		return new MyUserDetails(user.get());
	}
}