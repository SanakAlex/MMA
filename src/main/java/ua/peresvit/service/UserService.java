package ua.peresvit.service;


import org.springframework.web.multipart.MultipartFile;
import ua.peresvit.dto.UserDto;
import ua.peresvit.entity.Role;
import ua.peresvit.entity.User;
import ua.peresvit.entity.VerificationToken;
import ua.peresvit.error.UserAlreadyExistException;

import java.util.List;


public interface UserService {

	<S extends User> S save(S arg0);
	
	String saveFile(User user, MultipartFile file);
	
	User findOne(Long userId);

	List<User> findAll();

	List<User> findByRang(Role role);

	void delete(User user);

	boolean equals(Object obj);

	User findUserByEmail(String email);

	User getCurrentUser();

	void initializeUserInfo(User user);
	////////////////////////////////////////////////////////////////////////
	void createVerificationTokenForUser(User user, String token);
	User getUser(String verificationToken);
	VerificationToken getVerificationToken(String VerificationToken);
	String validateVerificationToken(String token);
	VerificationToken generateNewVerificationToken(String token);
	User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;
	void authenticateUser(User user);
	User createUserFromDto(UserDto accountDto);
    User findUserByEmailAndPassword(String email, String password);
}