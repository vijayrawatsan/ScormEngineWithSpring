package in.xebia.poc.api;

import in.xebia.poc.domain.Course;

public interface CourseDao extends GenericDao<Long, Course> {
	
	public Course findByCourseId(String courseId);
}
