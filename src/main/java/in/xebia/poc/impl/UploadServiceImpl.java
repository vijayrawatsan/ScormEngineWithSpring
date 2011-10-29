package in.xebia.poc.impl;

import in.xebia.poc.api.CourseDao;
import in.xebia.poc.api.UploadService;
import in.xebia.poc.domain.Course;
import in.xebia.poc.domain.User;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rusticisoftware.hostedengine.client.CourseService;
import com.rusticisoftware.hostedengine.client.ScormEngineService;
import com.rusticisoftware.hostedengine.client.datatypes.ImportResult;

@Service("uploadService")
@Transactional
public class UploadServiceImpl implements UploadService{
	
	private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
	
	@Autowired
	private ScormEngineService scormEngineService;

	@Resource
	private CourseDao courseDao;
	@Override
	public ImportResult uploadCourse(File file, String title, User owner) {
		CourseService courseService = scormEngineService.getCourseService();
		List<ImportResult> importResults = null;
		try {
			Course course = createCourseLocally(title, owner);
			importResults = courseService.ImportCourse(course.getCourseId(), file.getAbsolutePath());
			
		}catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
		return importResults.get(0);
	}

	private Course createCourseLocally(String title, User owner) {
		Course course = new Course();
		course.setDateCreated(new Date());
		course.setCourseId("Course-"+UUID.randomUUID().toString());
		course.setOwner(owner);
		course.setTitle(title);
		courseDao.persist(course);
		return course;
	}
	
}
