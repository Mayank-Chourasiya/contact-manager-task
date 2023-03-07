package com.contact.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.contact.entities.User;
import com.contact.repository.UserRepository;



public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.getUserByUserName(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("Couldn't found the User !!"); 
		}
		
		CustomUserDetails customUserDetails= new CustomUserDetails(user);
		
		return customUserDetails;
	}
}
