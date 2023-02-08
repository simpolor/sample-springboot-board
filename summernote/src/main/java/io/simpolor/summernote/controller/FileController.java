package io.simpolor.summernote.controller;

import com.google.gson.JsonObject;
import io.simpolor.summernote.component.FileUploadComponent;
import io.simpolor.summernote.model.FileMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(("/file"))
@RequiredArgsConstructor
public class FileController {

	private final FileUploadComponent fileUploadComponent;

	@PostMapping(value = "/upload", produces = "application/json")
	@ResponseBody
	public JsonObject upload(@RequestParam("image") MultipartFile multipartFile) {

		FileUploadComponent.FileUpload fileUpload = fileUploadComponent.upload(multipartFile);
		if(Boolean.TRUE.equals(fileUpload.getResult())){
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("imageUrl", "/upload/"+fileUpload.getSavedFileName());
			jsonObject.addProperty("result", Boolean.TRUE);

			return jsonObject;
		}

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("result", Boolean.FALSE);
		return jsonObject;
	}

}
