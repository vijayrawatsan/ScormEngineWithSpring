package in.xebia.poc.api;

import in.xebia.poc.domain.Course;
import in.xebia.poc.domain.RegistrationDetail;
import in.xebia.poc.domain.User;

public interface RegistrationDetailDao extends GenericDao<Long, RegistrationDetail>{

	public RegistrationDetail getRegistrationByUserAndCourse(User user, Course course);
	public RegistrationDetail findByRegistrationId(String registrationId);
	
}
