package in.xebia.poc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.xebia.poc.api.RegistrationDetailDao;
import in.xebia.poc.domain.Course;
import in.xebia.poc.domain.RegistrationDetail;
import in.xebia.poc.domain.User;

@Repository("registrationDetailDao")
@Transactional
public class RegistrationDetailDaoImpl extends
		GenericDaoImpl<Long, RegistrationDetail> implements
		RegistrationDetailDao {
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationDetailDaoImpl.class);
	
	@Override
	public RegistrationDetail getRegistrationByUserAndCourse(User learner, Course course) {
		
		logger.info("DAO Layer getRegistrationByUserAndCourse");
		RegistrationDetail registrationDetail = null;
		try{
				registrationDetail = (RegistrationDetail) getEntityManager()
				.createQuery("from RegistrationDetail where learner=:learner and course=:course")
				.setParameter("learner", learner).setParameter("course", course).getSingleResult();
				
		}catch (Exception e) {
			logger.info("Exception occurred returning empty RegistrationDetail.");
			return null;
		}
		
		return registrationDetail;
	}

	@Override
	public RegistrationDetail findByRegistrationId(String registrationId) {
		
		logger.info("DAO Layer findByRegistrationId");
		RegistrationDetail registrationDetail = null;
		try{
				registrationDetail = (RegistrationDetail) getEntityManager()
				.createQuery("from RegistrationDetail where registrationId=:registrationId")
				.setParameter("registrationId", registrationId).getSingleResult();
				
		}catch (Exception e) {
			logger.info("Exception occurred returning empty RegistrationDetail.");
			return null;
		}
		
		return registrationDetail;
	}

}
