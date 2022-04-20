package io.simpolor.upload.controller;

import io.simpolor.upload.component.FileUploader;
import io.simpolor.upload.model.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(("/file"))
@RequiredArgsConstructor
public class FileController {

	private final FileUploader fileUploader;

	@GetMapping("/upload")
	public ModelAndView uploadForm(ModelAndView mav){

		mav.setViewName("upload");
		return mav;
	}

	@PostMapping("/upload")
	public ModelAndView upload(ModelAndView mav,
							   @Validated FileDto request) {

		FileUploader.FileInfo fileInfo = fileUploader.createFile(request.getUploadFile(), "test");
		if(Objects.nonNull(fileInfo)) {
			mav.addObject("fileInfo", FileDto.FileDetail.of(fileInfo));
		}

		mav.setViewName("result");
		return mav;
	}

	@GetMapping("/new-upload")
	public ModelAndView newUploadForm(ModelAndView mav){

		mav.setViewName("new-upload");
		return mav;
	}

	@PostMapping("/new-upload")
	public ModelAndView newUpload(MultipartHttpServletRequest request,
								  ModelAndView mav) {

		MultipartFile profile = request.getFile("uploadFile");

		FileUploader.FileInfo fileInfo = fileUploader.newCreateFile(profile, "new");
		if(Objects.nonNull(fileInfo)) {
			mav.addObject("fileInfo", FileDto.FileDetail.of(fileInfo));
		}

		mav.setViewName("result");
		return mav;
	}

	@GetMapping("/multiple-upload")
	public ModelAndView uploadMultipleForm(ModelAndView mav){

		mav.setViewName("multiple-upload");
		return mav;
	}

	@PostMapping("/multiple-upload")
	public ModelAndView uploadMultiple(ModelAndView mav,
									   @Validated FileDto request) {

		List<FileUploader.FileInfo> fileInfos = fileUploader.createFiles(request.getUploadFiles(), "multiple");
		System.out.println("fileInfos : "+fileInfos);
		if(!CollectionUtils.isEmpty(fileInfos)){
			System.out.println("fileInfos : "+fileInfos.size());
			mav.addObject("fileInfos", FileDto.FileDetail.of(fileInfos));
		}

		mav.setViewName("result");
		return mav;
	}
}

