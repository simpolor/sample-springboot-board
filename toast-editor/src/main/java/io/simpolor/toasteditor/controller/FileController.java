package io.simpolor.toasteditor.controller;

import com.google.gson.JsonObject;
import io.simpolor.toasteditor.component.FileUploadComponent;
import io.simpolor.toasteditor.model.FileMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping(("/file"))
@RequiredArgsConstructor
public class FileController {

	private final FileUploadComponent fileUploadComponent;

	@PostMapping(value = "/upload", produces = "application/json")
	@ResponseBody
	public JsonObject upload(@RequestParam("image") MultipartFile multipartFile) {

		FileMessage fileMessage = fileUploadComponent.upload(multipartFile);
		if(Boolean.TRUE.equals(fileMessage.getResult())){
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("imageUrl", "/upload/"+fileMessage.getSavedFileName());
			jsonObject.addProperty("result", Boolean.TRUE);

			return jsonObject;
		}

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("result", Boolean.FALSE);
		return jsonObject;
	}

}
