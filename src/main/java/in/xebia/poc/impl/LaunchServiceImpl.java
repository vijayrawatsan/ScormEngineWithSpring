package in.xebia.poc.impl;

import in.xebia.poc.api.CourseDao;
import in.xebia.poc.api.LaunchService;
import in.xebia.poc.api.RegistrationDetailDao;
import in.xebia.poc.api.UserDao;
import in.xebia.poc.domain.Course;
import in.xebia.poc.domain.RegistrationDetail;
import in.xebia.poc.domain.User;

import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rusticisoftware.hostedengine.client.RegistrationService;
import com.rusticisoftware.hostedengine.client.ScormEngineService;

@Service("launchService")
@Transactional
public class LaunchServiceImpl implements LaunchService{

	private static final Logger logger = LoggerFactory.getLogger(LaunchServiceImpl.class);
	
	@Autowired
	private ScormEngineService scormEngineService; 
	
	@Resource
	private CourseDao courseDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private RegistrationDetailDao registrationDetailDao;
	
	@Override
	public String getLaunchUrl(String courseId, String learnerId, String redirectOnExitUrl) {
		String launchUrl="";
		RegistrationService registrationService = scormEngineService.getRegistrationService();
		Course course = courseDao.findByCourseId(courseId);
		User learner = userDao.findUserByUserName(learnerId);
		RegistrationDetail registrationDetail = registrationDetailDao
				.getRegistrationByUserAndCourse(learner, course);
		if(registrationDetail==null) {
			String registrationId = createRegistrarion(course, learner);
			try {
				launchUrl = registrationService
						.GetLaunchUrl(registrationId, redirectOnExitUrl
								+ "?registrationId=" + registrationId);
			}catch (Exception e) {
				logger.info("Exception GetLaunchUrl : "+ e.getMessage());
			}
		}
		else {
			try {
				launchUrl = registrationService.GetLaunchUrl(
						registrationDetail.getRegistrationId(),
						redirectOnExitUrl + "?registrationId="
								+ registrationDetail.getRegistrationId());
			}catch (Exception e) {
				logger.info("Exception GetLaunchUrl : "+ e.getMessage());
			}
			
		}
		return launchUrl;
	}

	private String createRegistrarion(Course course, User learner) {
		RegistrationDetail registrationDetail = createRegistrationDetailLocally(course,learner);
		RegistrationService registrationService = scormEngineService.getRegistrationService();
		try {
			registrationService.CreateRegistration(
			registrationDetail.getRegistrationId(), course.getCourseId(),
			learner.getUserName(), learner.getFirstName(),
			learner.getLastName());
			
		}catch (Exception e) {
			logger.info("Exception createRegistration : "+ e.getMessage());
		}
		return registrationDetail.getRegistrationId();
	}

	private RegistrationDetail createRegistrationDetailLocally(Course course,
			User learner) {
		RegistrationDetail registrationDetail = new RegistrationDetail();
		registrationDetail.setLearner(learner);
		registrationDetail.setCourse(course);
		registrationDetail.setRegistrationId("Registration-"+UUID.randomUUID().toString());
		registrationDetailDao.persist(registrationDetail);
		return registrationDetail;
	}

}
