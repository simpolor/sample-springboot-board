package io.simpolor.upload.controller;

import io.simpolor.upload.component.FileUpload;
import io.simpolor.upload.component.FileUploadComponent;
import io.simpolor.upload.component.NewFileUploadComponent;
import io.simpolor.upload.model.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(("/file"))
@RequiredArgsConstructor
public class FileController {

	private final FileUploadComponent fileUploadComponent;
	private final NewFileUploadComponent newFileUploadComponent;

	@GetMapping("/single-upload")
	public ModelAndView singleUploadForm(ModelAndView mav){

		mav.setViewName("single-upload");
		return mav;
	}

	@PostMapping("/single-upload")
	public ModelAndView singleUpload(ModelAndView mav,
									 @Validated MultipartFile uploadFile) {

		FileUpload fileUpload = fileUploadComponent.create(uploadFile);
		if(Boolean.TRUE.equals(fileUpload.getResult())) {
			mav.addObject("file", FileDto.FileResponse.of(fileUpload));
		}

		mav.setViewName("result");
		return mav;
	}

	@GetMapping("/new-upload")
	public ModelAndView newUploadForm(ModelAndView mav){

		mav.setViewName("single-upload");
		return mav;
	}

	@PostMapping("/new-upload")
	public ModelAndView newUpload(ModelAndView mav,
								  @Validated MultipartFile uploadFile) {

		FileUpload fileUpload = newFileUploadComponent.create(uploadFile);
		if(Boolean.TRUE.equals(fileUpload.getResult())) {
			mav.addObject("file", FileDto.FileResponse.of(fileUpload));
		}

		mav.setViewName("result");
		return mav;
	}

	@GetMapping("/multi-upload")
	public ModelAndView multiUploadForm(ModelAndView mav){

		mav.setViewName("multi-upload");
		return mav;
	}

	@PostMapping("/multi-upload")
	public ModelAndView multiUpload(ModelAndView mav,
									@Validated MultipartFile[] uploadFiles) {

		List<FileUpload> fileUploads = fileUploadComponent.create(uploadFiles);
		if(!CollectionUtils.isEmpty(fileUploads)){
			mav.addObject("files", FileDto.FileResponse.of(fileUploads));
		}

		mav.setViewName("result");
		return mav;
	}
}

