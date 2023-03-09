package com.contact.service;

import java.security.Principal;
import java.util.List;

import com.contact.dto.ContactDto;
import com.contact.entities.Contact;
import com.contact.entities.User;

public interface UserService {

	void addContact(ContactDto contactDto, Principal principal) throws Exception;

	List<Contact> showContacts(Principal principal) throws Exception;
	
	List<Contact> searchContact(String searchKey, Principal principal) throws Exception;

	void deleteContact(User user, Contact contact) throws Exception;

	void updateContact(ContactDto contactDto, Contact contact , Principal principal) throws Exception;

}
