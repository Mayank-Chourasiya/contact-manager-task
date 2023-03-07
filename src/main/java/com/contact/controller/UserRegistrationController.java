package com.contact.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contact.dto.UserDto;
import com.contact.httpResponse.DataResponse;
import com.contact.messagesAndCodes.ResponseMessage;
import com.contact.messagesAndCodes.StatusCode;
import com.contact.service.UserRegistrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public")
public class UserRegistrationController {

	@Autowired
	UserRegistrationService userRegistrationService;

	@PostMapping("/register")
	public DataResponse registerUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {

		try {
			if (bindingResult.hasErrors()) {
				List<String> errorMessage = new ArrayList<String>();
				for (Object object : bindingResult.getAllErrors()) {
					if (object instanceof FieldError) {
						FieldError fieldError = (FieldError) object;
						errorMessage.add(fieldError.getDefaultMessage());
					}
				}
				return new DataResponse(StatusCode.BAD_REQUEST, ResponseMessage.BAD_REQUEST, errorMessage);
			}

			userRegistrationService.registerUser(userDto);
			return new DataResponse(StatusCode.SUCCESS, ResponseMessage.OK, ResponseMessage.REGISTRATION_SUCCESS);

		} catch (Exception e) {
			return new DataResponse(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR,
					e.getMessage());

		}

	}

}
