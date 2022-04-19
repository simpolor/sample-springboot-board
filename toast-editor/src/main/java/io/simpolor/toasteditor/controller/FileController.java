package io.simpolor.toasteditor.controller;

import com.google.gson.JsonObject;
import io.simpolor.toasteditor.component.EditorFileUploader;
import io.simpolor.toasteditor.model.BoardDto;
import io.simpolor.toasteditor.repository.entity.Board;
import io.simpolor.toasteditor.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(("/file"))
@RequiredArgsConstructor
public class FileController {

	private final EditorFileUploader editorFileUploader;

	@PostMapping(value = "/upload", produces = "application/json")
	@ResponseBody
	public JsonObject upload(@RequestParam("image") MultipartFile multipartFile) {

		return editorFileUploader.upload(multipartFile);
	}

}
