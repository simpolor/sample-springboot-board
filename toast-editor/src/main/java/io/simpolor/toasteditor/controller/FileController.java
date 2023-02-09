package io.simpolor.toasteditor.controller;

import com.google.gson.JsonObject;
import io.simpolor.toasteditor.component.FileComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping(("/file"))
@RequiredArgsConstructor
public class FileController {

	private final FileComponent fileComponent;

	@PostMapping(value = "/upload", produces = "application/json")
	@ResponseBody
	public JsonObject upload(@RequestParam("image") MultipartFile multipartFile) {

		FileComponent.FileUpload fileMessage = fileComponent.upload(multipartFile);
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
