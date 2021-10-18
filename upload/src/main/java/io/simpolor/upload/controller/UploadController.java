package io.simpolor.upload.controller;

import io.simpolor.upload.component.FileUploader;
import io.simpolor.upload.model.UploadRequest;
import io.simpolor.upload.model.UploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UploadController {

	private final FileUploader fileUploader;

	@GetMapping("/upload")
	public ModelAndView uploadForm(ModelAndView mav){

		mav.setViewName("upload");
		return mav;
	}

	@PostMapping("/upload")
	public UploadResponse upload(@Validated UploadRequest request) {

		FileUploader.FileInfo fileInfo = fileUploader.createFile(request.getUploadFile(), "test");
		if(Objects.nonNull(fileInfo)) {
			return UploadResponse.builder()
					.orgFileName(fileInfo.getOrgFileName())
					.savedFileName(fileInfo.getSavedFileName())
					.fileSize(fileInfo.getFileSize())
					.fileExt(fileInfo.getFileExt())
					.build();
		}

		return new UploadResponse();
	}

	@GetMapping("/multiple-upload")
	public ModelAndView uploadMultipleForm(ModelAndView mav){

		mav.setViewName("multiple_upload");
		return mav;
	}

	@PostMapping("/multiple-upload")
	public List<UploadResponse> uploadMultiple(@Validated UploadRequest request) {

		List<FileUploader.FileInfo> fileInfos = fileUploader.createFiles(request.getUploadFiles(), "multiple-test");

		return UploadResponse.of(fileInfos);
	}

	@GetMapping("/upload2")
	public ModelAndView uploadForm2(ModelAndView mav){

		mav.setViewName("upload2");
		return mav;
	}

	@PostMapping("/upload2")
	public UploadResponse upload2(MultipartHttpServletRequest request) {

		MultipartFile profile = request.getFile("uploadFile");

		FileUploader.FileInfo fileInfo = fileUploader.createFile2(profile, "test2");
		return UploadResponse.of(fileInfo);
	}
}

