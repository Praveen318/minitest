package com.example.demo.config;

//CustomAuthenticationProvider for differentiation between wrong username and wrong password

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Overriding authenticate method of AuthenticationProvider interface
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String useremail = authentication.getName();
		UserDetails user = userDetailsService.loadUserByUsername(useremail);
		String password = authentication.getCredentials().toString();

		// password verification
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Incorrect password");
		}
		return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
