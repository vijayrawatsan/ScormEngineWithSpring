package in.xebia.poc.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.xebia.poc.api.LoginService;
import in.xebia.poc.api.UserDao;
import in.xebia.poc.domain.User;

@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService{

	@Resource
	private UserDao userDao;
	
	@Override
	public User authenticate(String userName, String password) {
		User user = userDao.findUserByUserNameAndPassword(userName, password);
		return user;
	}

}
