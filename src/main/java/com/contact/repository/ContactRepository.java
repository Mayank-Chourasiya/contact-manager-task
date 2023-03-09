package com.contact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.entities.Contact;
import com.contact.entities.User;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	@Query("select c from Contact c where c.user.id= :userId")
	public List<Contact> getContactsByUserId(@Param("userId") int userId);

	@Query("select c from Contact c where c.firstName LIKE %:searchKey1% AND c.user= :user OR c.lastName LIKE %:searchKey2% AND c.user= :user OR c.email LIKE %:searchKey3% AND c.user= :user")
	public List<Contact> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndUser(
			String searchKey1, String searchKey2, String searchKey3, User user);

}
