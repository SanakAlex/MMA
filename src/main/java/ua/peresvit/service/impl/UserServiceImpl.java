package ua.peresvit.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.peresvit.config.Constant;
import ua.peresvit.dao.RoleRepository;
import ua.peresvit.dao.UserRepository;
import ua.peresvit.dao.VerificationTokenRepository;
import ua.peresvit.dto.UserDto;
import ua.peresvit.entity.Role;
import ua.peresvit.entity.User;
import ua.peresvit.entity.VerificationToken;
import ua.peresvit.error.UserAlreadyExistException;
import ua.peresvit.service.UserService;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

//import org.bionic.dao.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private RoleRepository roleRepository;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Override
    public <S extends User> S save(S arg0) {
        return userRepository.save(arg0);
    }

    @Override
    public User findOne(Long userId) {
        User user = userRepository.findOne(userId);
        initializeUserInfo(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByRang(Role role) {
        return userRepository.findByRang(role);
    }

    @Override
    public void delete(User user) {
        final VerificationToken verificationToken = tokenRepository.findByUser(user);

        if (verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }

        userRepository.delete(user);
    }

    @Override
    public boolean equals(Object obj) {
        return userRepository.equals(obj);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        initializeUserInfo(user);
        return user;
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        return userRepository.findByEmail(name);
    }

    @Override
    public void initializeUserInfo(User user) {
        if(user == null)
            return;


    }


    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public User getUser(String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(final UserDto accountDto) {
        if (emailExist(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        user.setRole(roleRepository.findOne(4L));
        user.setAvatarURL("http://image.flaticon.com/icons/svg/126/126486.svg");
        if (accountDto.getProfileFB() != null) {
            user.setProfileFB(accountDto.getProfileFB());
        }
        if (accountDto.getProfileVK() != null) {
            user.setProfileVK(accountDto.getProfileVK());
        }
        if (accountDto.getProfileGoogle() != null) {
            user.setProfileGoogle(accountDto.getProfileGoogle());
        }

        return userRepository.save(user);
    }

    @Override
    public void authenticateUser(User user) {

    }

    @Override
    public User createUserFromDto(UserDto accountDto) {
        final User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        user.setRole(roleRepository.findOne(4L)); //TODO what is this magic number doing?
        return user;
    }

    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

	@Override
	public String saveFile(User user, MultipartFile inputFile) {

        // return old avatar
		if(inputFile.isEmpty())
			return user.getAvatarURL();
			
		if(user.getUserId() == null)
			save(user);
			
		String pathFile = Constant.UPLOAD_PATH + "/" + Constant.USR_FOLDER + user.getUserId() + "/" + Constant.AVATAR;
		String fileURL = Constant.uploadingFile(inputFile, pathFile);
		
		// delete old file
		if(user.getUserId() != null){
			String oldPath = findOne(user.getUserId()).getAvatarURL();
			if(!fileURL.equals(oldPath))
				Constant.deleteFile(findOne(user.getUserId()).getAvatarURL());
		}
			
		return fileURL;
	}
    @Override
    public User findUserByEmailAndPassword(String email,String password){
        return userRepository.findUserByEmailAndPassword(email, password);
    }
}