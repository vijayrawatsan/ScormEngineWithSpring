package in.xebia.poc;

import com.rusticisoftware.hostedengine.client.datatypes.CourseData;

public class BigFatClass {
	
	private CourseData courseData;
	private String previewUrl;
	
	public CourseData getCourseData() {
		return courseData;
	}
	public void setCourseData(CourseData courseData) {
		this.courseData = courseData;
	}
	public String getPreviewUrl() {
		return previewUrl;
	}
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	
}
