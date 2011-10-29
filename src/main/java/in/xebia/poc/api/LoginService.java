package in.xebia.poc.api;

import in.xebia.poc.domain.User;

public interface LoginService {

	public User authenticate(String userName, String password);
}
