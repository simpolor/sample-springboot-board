package io.simpolor.summernote.controller;

import com.google.gson.JsonObject;
import io.simpolor.summernote.component.FileUpload;
import io.simpolor.summernote.model.BoardDto;
import io.simpolor.summernote.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(("/board"))
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final FileUpload fileUpload;

	@GetMapping("/list")
	public ModelAndView list(ModelAndView mav) {

		List<BoardDto> boardDtos = boardService.list();

		mav.addObject("boards", boardDtos);
		mav.setViewName("board-list");
		return mav;
	}

	@GetMapping("/detail/{seq}")
	public ModelAndView detail(ModelAndView mav, @PathVariable long seq) {

		BoardDto boardDto = boardService.get(seq);

		mav.addObject("board", boardDto);
		mav.setViewName("board-detail");
		return mav;
	}

	@GetMapping("/register")
	public ModelAndView registerForm(ModelAndView mav) {

		mav.setViewName("board-register");
		return mav;
	}

	@PostMapping("/register")
	public ModelAndView write(ModelAndView mav, BoardDto boardDto) {

		BoardDto boardDto1 = boardService.create(boardDto);

		mav.setViewName("redirect:/board/detail/"+boardDto1.getSeq());
		return mav;
	}

	@GetMapping("/modify/{seq}")
	public ModelAndView modifyForm(ModelAndView mav, @PathVariable Long seq) {

		BoardDto boardDto = boardService.get(seq);

		mav.addObject("board", boardDto);
		mav.setViewName("board-modify");
		return mav;
	}

	@PostMapping("/modify/{seq}")
	public ModelAndView modify(ModelAndView mav, @PathVariable Long seq, BoardDto boardDto) {

		boardDto.setSeq(seq);
		BoardDto boardDto1 = boardService.update(boardDto);

		mav.setViewName("redirect:/board/detail/"+boardDto1.getSeq());
		return mav;
	}

	@PostMapping("/delete/{seq}")
	public ModelAndView delete(ModelAndView mav, @PathVariable Long seq) {

		boardService.delete(seq);

		mav.setViewName("redirect:/board/list");
		return mav;
	}

	@PostMapping(value = "/upload", produces = "application/json")
	@ResponseBody
	public JsonObject upload(@RequestParam("file") MultipartFile multipartFile) {

		return fileUpload.upload(multipartFile);
	}

}
