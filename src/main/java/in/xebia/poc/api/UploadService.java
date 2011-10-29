package in.xebia.poc.api;

import in.xebia.poc.domain.User;

import java.io.File;

import com.rusticisoftware.hostedengine.client.datatypes.ImportResult;

public interface UploadService {

	public ImportResult uploadCourse(File file, String title, User owner);
	
}
