package in.xebia.poc;

import in.xebia.poc.api.LaunchService;
import in.xebia.poc.api.LoginService;
import in.xebia.poc.api.RegistrationDetailDao;
import in.xebia.poc.api.UploadService;
import in.xebia.poc.domain.RegistrationDetail;
import in.xebia.poc.domain.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rusticisoftware.hostedengine.client.CourseService;
import com.rusticisoftware.hostedengine.client.ScormEngineService;
import com.rusticisoftware.hostedengine.client.datatypes.CourseData;
import com.rusticisoftware.hostedengine.client.datatypes.RegistrationSummary;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ScormEngineService scormEngineService;
	
	@Resource
	private LoginService loginService;
	
	@Resource
	private UploadService uploadService;
	
	@Resource
	private LaunchService launchService;
	
	@Resource
	private RegistrationDetailDao registrationDetailDao; 
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login() throws Exception {
		return "login";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model,
			@RequestParam(value = "registrationId", required = false, defaultValue = "default") String registrationId)
			throws Exception {
		if(!registrationId.equals("default")) {
			postLaunch(registrationId);
		}
		List<BigFatClass> bigFatClasses = getBigFatClasses();
		model.addAttribute("bigFatClasses", bigFatClasses);
		return "home";
	}

	@RequestMapping(value = "/launch", method = RequestMethod.GET)
	public void launch(@RequestParam String courseId,
			@RequestParam String learnerId,
			HttpServletResponse httpServletResponse) throws Exception {
		
		httpServletResponse.sendRedirect(launchService.getLaunchUrl(courseId,
				learnerId, "http://localhost:8080/poc/home"));
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Locale locale, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) throws Exception {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		logger.info(httpServletRequest.getParameter("userName") + " | " +httpServletRequest.getParameter("password"));
		User user = loginService.authenticate(httpServletRequest.getParameter("userName"),
				httpServletRequest.getParameter("password"));
		if(user==null)
			return "login";
		List<BigFatClass> bigFatClasses = getBigFatClasses();
		model.addAttribute("bigFatClasses", bigFatClasses);
		httpSession.setAttribute("userName", user.getUserName());
		return "home";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(Model model,HttpServletRequest httpServletRequest, HttpSession httpSession) throws Exception {
		//These params will be set to the request params after we parse the file upload request
		HashMap<String, String> params = new HashMap<String, String>();
		
		File tempFile = File.createTempFile("tempFile", ".zip");
		FileUploadUtils.parseFileUploadRequest(httpServletRequest, tempFile, params);
		
		uploadService.uploadCourse(tempFile, params.get("title"), (User) httpSession.getAttribute("user"));
		
		tempFile.delete();
		List<BigFatClass> bigFatClasses = getBigFatClasses();
		model.addAttribute("bigFatClasses", bigFatClasses);
		return "home";
	}
	
	
	private void postLaunch(String registrationId) throws Exception {
		RegistrationSummary summary = scormEngineService.getRegistrationService().GetRegistrationSummary(registrationId);
		RegistrationDetail registrationDetail = registrationDetailDao.findByRegistrationId(registrationId);
		registrationDetail.setComplete(summary.getComplete());
		registrationDetail.setScore(summary.getScore());
		registrationDetail.setSuccess(summary.getSuccess());
		registrationDetail.setTotalTime(summary.getTotalTime());
		if(summary.getComplete().equalsIgnoreCase("complete"))
			registrationDetail.setDateCompleted(new Date());
		registrationDetailDao.merge(registrationDetail);
	}
	
	private List<String> getPreviewUrls(List<CourseData> courseDatas, CourseService courseService) throws Exception {
		
		List<String> previewUrls = new ArrayList<String>();
		Iterator<CourseData> courseDataIterator = courseDatas.iterator();
		while(courseDataIterator.hasNext())
			previewUrls.add(courseService.GetPreviewUrl(courseDataIterator.next().getCourseId(),"http://localhost:8080/poc/home"));
		
		return previewUrls;
	}
	
	private List<BigFatClass> getBigFatClasses() throws Exception {
		CourseService courseService = scormEngineService.getCourseService();
		List<CourseData> courseDatas = courseService.GetCourseList();
		List<String> previewUrls = getPreviewUrls( courseDatas, courseService);
		List<BigFatClass> bigFatClasses =  new ArrayList<BigFatClass>();
		for(int i=0;i<courseDatas.size();i++) {
			BigFatClass bigFatClass = new BigFatClass();
			bigFatClass.setCourseData(courseDatas.get(i));
			bigFatClass.setPreviewUrl(previewUrls.get(i));
			bigFatClasses.add(bigFatClass);
		}
		return bigFatClasses;
	}
}
