package io.simpolor.upload.controller;

import io.simpolor.upload.component.FileUploader;
import io.simpolor.upload.model.Result;
import io.simpolor.upload.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private FileUploader fileUploader;

	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public ModelAndView uploadForm(ModelAndView mav){

		mav.setViewName("upload");
		return mav;
	}

	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public Result upload(@Validated Student student) {

		FileUploader.Files files = fileUploader.createFile(student.getProfile(), "student");
		if(files != null) {
			return Result.builder()
					.name(student.getName())
					.orgFileName(files.getOrgFileName())
					.savedFileName(files.getSavedFileName())
					.fileSize(files.getFileSize())
					.fileExt(files.getFileExt())
					.build();
		}

		return Result.builder().name(student.getName()).build();
	}

	/***
	 * MultipartFile
	 * - getName() : 파라미터 이름
	 * - getOriginalFilename() : 파일 이름
	 * - isEmpty() : 파일 존재 유무
	 * - getBytes : 파일 데이터
	 * - getInputStream() : 파일 데이터를 읽어오는 InputStream을 얻어옴
	 * - transferTo(File file) : 파일 데이터를 지정한 파일로 저장
	 */



}

