package in.xebia.poc.impl;

import in.xebia.poc.api.UserDao;
import in.xebia.poc.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDao")
@Transactional
public class UserDaoImpl extends GenericDaoImpl<Long, User> implements UserDao{
	
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Override
	public User findUserByUserNameAndPassword(String userName, String password) {
		
		logger.info("DAO Layer findUserByUserNameAndPassword");
		User user = null;
		try{
				user = (User) getEntityManager()
				.createQuery("from User where userName=:userName and password=:password")
				.setParameter("userName", userName).setParameter("password", password).getSingleResult();
				
		}catch (Exception e) {
			logger.info("Exception occurred returning empty user.");
			return null;
		}
		return user;
	}
	
	@Override
	public User findUserByUserName(String userName) {
		
		logger.info("DAO Layer findUserByUserName");
		User user = null;
		try{
				user = (User) getEntityManager()
				.createQuery("from User where userName=:userName")
				.setParameter("userName", userName).getSingleResult();
				
		}catch (Exception e) {
			logger.info("Exception occurred returning empty user.");
			return null;
		}
		return user;
	}
}
