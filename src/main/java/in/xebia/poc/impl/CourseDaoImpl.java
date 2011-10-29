package in.xebia.poc.impl;

import in.xebia.poc.api.CourseDao;
import in.xebia.poc.domain.Course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("courseDao")
@Transactional
public class CourseDaoImpl extends GenericDaoImpl<Long, Course> implements CourseDao{

	private static final Logger logger = LoggerFactory.getLogger(CourseDaoImpl.class);
	
	@Override
	public Course findByCourseId(String courseId) {
		logger.info("DAO Layer findByCourseId");
		Course course = null;
		try{
				course = (Course) getEntityManager()
				.createQuery("from Course where courseId=:courseId")
				.setParameter("courseId", courseId).getSingleResult();
				
		}catch (Exception e) {
			logger.info("Exception occurred returning empty Course.");
			return null;
		}
		return course;
	}
	
}
