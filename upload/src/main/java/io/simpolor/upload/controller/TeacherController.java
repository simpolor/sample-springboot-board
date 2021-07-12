package io.simpolor.upload.controller;

import io.simpolor.upload.component.FileUploader;
import io.simpolor.upload.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private FileUploader fileUploader;

	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public ModelAndView uploadForm(ModelAndView mav){

		mav.setViewName("upload_teacher");

		return mav;
	}

	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public Result upload(MultipartHttpServletRequest request) {

		String name = request.getParameter("name");
		String grade = request.getParameter("grade");
		String age = request.getParameter("age");
		MultipartFile profile = request.getFile("profile");

		FileUploader.Files files = fileUploader.createFileV2(profile, "teacher");
		if(files != null) {
			return Result.builder()
					.name(name)
					.orgFileName(files.getOrgFileName())
					.savedFileName(files.getSavedFileName())
					.fileSize(files.getFileSize())
					.fileExt(files.getFileExt())
					.build();
		}

		return Result.builder().name(name).build();
	}

	


}

