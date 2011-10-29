package in.xebia.poc.api;

import in.xebia.poc.domain.User;

public interface UserDao extends GenericDao<Long, User>{
	public User findUserByUserNameAndPassword(String userName, String password);
	public User findUserByUserName(String userName);
}
