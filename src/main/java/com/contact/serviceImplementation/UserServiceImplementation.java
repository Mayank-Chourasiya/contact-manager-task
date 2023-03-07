package com.contact.serviceImplementation;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.dto.ContactDto;
import com.contact.entities.Contact;
import com.contact.entities.User;
import com.contact.messagesAndCodes.ResponseMessage;
import com.contact.repository.ContactRepository;
import com.contact.repository.UserRepository;
import com.contact.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	ModelMapper modelMapper;

	public void addContact(ContactDto contactDto, Principal principal) throws Exception {

		User user = this.userRepository.getUserByUserName(principal.getName());

		contactDto.setUser(user);

		Contact contact = new Contact();

		modelMapper.map(contactDto, contact);
		contactRepository.save(contact);

	}

	public List<Contact> showContacts(Principal principal) throws Exception {

		User user = this.userRepository.getUserByUserName(principal.getName());

		List<Contact> contacts = this.contactRepository.getContactsByUserId(user.getId());

		return  contacts;

	}

	public void deleteContact(Contact contact, Principal principal) throws Exception {

		User user = userRepository.getUserByUserName(principal.getName());

		if (user.getId() == contact.getUser().getId()) {
			user.getContacts().remove(contact);
			userRepository.save(user);
		}

	}

	public void updateContact(ContactDto contactDto, Contact contact , Principal principal) throws Exception {

		User user = userRepository.getUserByUserName(principal.getName());
		
		contact.setUser(user);
		contact.setFirstName(contactDto.getFirstName());
		contact.setLastName(contactDto.getLastName());
		contact.setEmail(contactDto.getEmail());
		contact.setPhoneNumber(contactDto.getPhoneNumber());

		contactRepository.save(contact);
	}
	
	public List<Contact> searchContact(String searchKey, Principal principal) throws Exception {
		
		User user = userRepository.getUserByUserName(principal.getName());
		List<Contact> contacts=contactRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndUser(searchKey,searchKey,searchKey, user);
		
		if(contacts.isEmpty())
			throw new RuntimeException(ResponseMessage.NO_CONTENT);
		else
			return contacts;
	}
}
