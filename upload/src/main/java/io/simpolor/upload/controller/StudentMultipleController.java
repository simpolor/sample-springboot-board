package io.simpolor.upload.controller;

import io.simpolor.upload.component.FileUploader;
import io.simpolor.upload.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentMultipleController {

	@Autowired
	private FileUploader fileUploader;

	@RequestMapping(value="/upload/multiple", method=RequestMethod.GET)
	public ModelAndView uploadForm(ModelAndView mav){

		mav.setViewName("upload_multiple");

		return mav;
	}

	@RequestMapping(value="/upload/multiple", method=RequestMethod.POST)
	public int upload(@Validated Student student) {

		List<FileUploader.Files> files = fileUploader.createFiles(student.getProfiles(), "student");
		return files.size();
	}

}

