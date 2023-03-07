package com.contact.serviceImplementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.contact.dto.UserDto;
import com.contact.entities.User;
import com.contact.messagesAndCodes.ResponseMessage;
import com.contact.repository.UserRepository;
import com.contact.service.UserRegistrationService;

@Service
public class UserRegistrationServiceImplementation implements UserRegistrationService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	public void registerUser(UserDto userDto) throws Exception {
		User user = userRepository.findByEmail(userDto.getEmail());

		if (user != null)
			throw new RuntimeException(ResponseMessage.USER_EXIST);

		else
		{
			userDto.setRole("ROLE_USER");
			userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
			user = new User();
		}
		modelMapper.map(userDto, user);
		userRepository.save(user);

	}

}
