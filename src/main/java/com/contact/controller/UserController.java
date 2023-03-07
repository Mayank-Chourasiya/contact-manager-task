package com.contact.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contact.dto.ContactDto;
import com.contact.entities.Contact;
import com.contact.httpResponse.DataResponse;
import com.contact.messagesAndCodes.ResponseMessage;
import com.contact.messagesAndCodes.StatusCode;
import com.contact.repository.ContactRepository;
import com.contact.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private ContactRepository contactRepository;

	@PostMapping("/add-contact")
	public DataResponse addContact(@Valid @RequestBody ContactDto contactDto, BindingResult bindingResult,
			Principal principal) {

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

			userService.addContact(contactDto, principal);
			return new DataResponse(StatusCode.SUCCESS, ResponseMessage.OK, ResponseMessage.ADD_SUCCESS);

		} catch (Exception e) {
			return new DataResponse(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR,
					e.getMessage());

		}
	}

	@GetMapping("/show-contacts")
	public DataResponse showContacts(Principal principal) {

		try {
			List<Contact> contacts = userService.showContacts(principal);
			if (contacts != null)
				return new DataResponse(StatusCode.SUCCESS, ResponseMessage.OK, contacts);
			else
				return new DataResponse(StatusCode.NO_CONTENT, ResponseMessage.NO_CONTENT, null);

		} catch (Exception e) {

			return new DataResponse(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR,
					e.getMessage());
		}

	}

	@GetMapping("/delete-contact/{id}")
	public DataResponse deleteContact(@PathVariable("id") Integer id, Principal principal) {

		Contact contact = contactRepository.findById(id).get();

		try {

			if (contact != null) {
				userService.deleteContact(contact, principal);
				return new DataResponse(StatusCode.SUCCESS, ResponseMessage.DELETE_CONTACT, null);
			} else
				return new DataResponse(StatusCode.NO_CONTENT, ResponseMessage.NO_CONTENT, null);
		} catch (Exception e) {
			return new DataResponse(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR,
					e.getMessage());
		}

	}

	@PostMapping("/update-contact/{id}")
	public DataResponse updateForm(@Valid @RequestBody ContactDto contactDto, BindingResult bindingResult,
			@PathVariable("id") Integer id, Principal principal) {

		Contact contact = contactRepository.findById(id).get();

		try {

			if (contact != null) {
				userService.updateContact(contactDto, contact , principal);
				return new DataResponse(StatusCode.SUCCESS, ResponseMessage.UPDATE_SUCCESS, null);
			} else
				return new DataResponse(StatusCode.NO_CONTENT, ResponseMessage.NO_CONTENT, null);
		} catch (Exception e) {
			return new DataResponse(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR,
					e.getMessage());
		}

	}
	
	@GetMapping("search-contact/{searchKey}")
	public DataResponse searchContact(@PathVariable("searchKey") String searchKey, Principal principal) {
		
		try {
			List<Contact> contacts = userService.searchContact(searchKey, principal);
			if (contacts != null)
				return new DataResponse(StatusCode.SUCCESS, ResponseMessage.OK, contacts);
			else
				return new DataResponse(StatusCode.NO_CONTENT, ResponseMessage.NO_CONTENT, null);

		} catch (Exception e) {

			return new DataResponse(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR,
					e.getMessage());
		}
	}

}
