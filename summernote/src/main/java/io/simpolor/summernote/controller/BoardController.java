package io.simpolor.summernote.controller;

import com.google.gson.JsonObject;
import io.simpolor.summernote.component.EditorFileUploader;
import io.simpolor.summernote.model.BoardDto;
import io.simpolor.summernote.repository.entity.Board;
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
	private final EditorFileUploader editorFileUploader;

	@GetMapping("/list")
	public ModelAndView list(ModelAndView mav) {

		List<Board> boards = boardService.getAll();

		mav.addObject("boards", BoardDto.of(boards));
		mav.setViewName("board_list");
		return mav;
	}

	@GetMapping("/detail/{boardId}")
	public ModelAndView detail(ModelAndView mav,
							   @PathVariable long boardId) {

		Board board = boardService.get(boardId);

		mav.addObject("board", BoardDto.of(board));
		mav.setViewName("board_detail");
		return mav;
	}

	@GetMapping("/register")
	public ModelAndView registerForm(ModelAndView mav) {

		mav.setViewName("board_register");
		return mav;
	}

	@PostMapping("/register")
	public ModelAndView register(ModelAndView mav,
								 BoardDto boardDto) {

		Board board = boardDto.toEntity();
		boardService.create(board);

		mav.setViewName("redirect:/board/detail/"+board.getBoardId());
		return mav;
	}

	@GetMapping("/modify/{boardId}")
	public ModelAndView modifyForm(ModelAndView mav,
								   @PathVariable Long boardId) {

		Board board = boardService.get(boardId);

		mav.addObject("board", BoardDto.of(board));
		mav.setViewName("board_modify");
		return mav;
	}

	@PostMapping("/modify/{boardId}")
	public ModelAndView modify(ModelAndView mav,
							   @PathVariable Long boardId,
							   BoardDto boardDto) {

		boardDto.setId(boardId);
		Board board = boardDto.toEntity();
		boardService.update(board);

		mav.setViewName("redirect:/board/detail/"+board.getBoardId());
		return mav;
	}

	@PostMapping("/delete/{boardId}")
	public ModelAndView delete(ModelAndView mav,
							   @PathVariable Long boardId) {

		boardService.delete(boardId);

		mav.setViewName("redirect:/board/list");
		return mav;
	}

}
