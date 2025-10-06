package NayaBazzar.service;

import NayaBazzar.exception.UserException;
import NayaBazzar.model.User;

public interface UserService {

	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findUserByEmail(String email) throws UserException;


}
