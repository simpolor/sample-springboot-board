package io.simpolor.boardcomment.controller;

import io.simpolor.boardcomment.model.BoardCommentDto;
import io.simpolor.boardcomment.model.BoardDto;
import io.simpolor.boardcomment.repository.entity.Board;
import io.simpolor.boardcomment.repository.entity.BoardComment;
import io.simpolor.boardcomment.repository.entity.User;
import io.simpolor.boardcomment.service.BoardCommentService;
import io.simpolor.boardcomment.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final BoardCommentService boardCommentService;

	@GetMapping("/list")
	public ModelAndView list(ModelAndView mav,
							 BoardDto.BoardSearch search,
							 @SortDefault(sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable){

		Page<Board> page = boardService.getAll(search.toEntity(), pageable);

		List<BoardDto.BoardResponse> responses = page.getContent().stream()
				.map(BoardDto.BoardResponse::of)
				.collect(Collectors.toList());

		Page<BoardDto.BoardResponse> boards = new PageImpl<>(responses, pageable, page.getTotalElements());
		mav.addObject("search", search);
		mav.addObject("boards", boards);

		mav.setViewName("board_list");
		return mav;
	}

	@RequestMapping(value="/detail/{boardId}", method= RequestMethod.GET)
	public ModelAndView detail(ModelAndView mav,
							   @PathVariable Long boardId) {

		Board board = boardService.get(boardId);

		List<BoardComment> boardComments = boardCommentService.getAll(board);

		mav.addObject("board", BoardDto.BoardDetailResponse.of(board));
		mav.addObject("boardCommentList", BoardCommentDto.BoardCommentResponse.of(boardComments));
		mav.setViewName("board_detail");
		return mav;
	}

	@GetMapping(value="/register")
	public ModelAndView registerForm(ModelAndView mav,
									 @ModelAttribute("board") BoardDto.BoardRequest request) {

		mav.setViewName("board_register");
		return mav;
	}

	@PostMapping(value="/register")
	public ModelAndView register(ModelAndView mav,
								 @Valid @ModelAttribute("board") BoardDto.BoardRequest request,
								 BindingResult bindingResult) {

		if(bindingResult.hasErrors()){
			List<FieldError> errors = bindingResult.getFieldErrors();
			for(FieldError error : errors){
				log.debug("Error on object: {}, on field: {}, Message: {}", error.getObjectName(), error.getField(), error.getDefaultMessage());
			}
			mav.setViewName("board_register");
			return mav;
		}

		User user = new User();
		user.setUserId(1L);
		user.setNickname("유저1호");

		Board board = request.toEntity();
		board.setCreator(user);
		board.setUpdater(user);

		boardService.insert(board);

		mav.setViewName("redirect:/board/detail/"+board.getBoardId());
		return mav;
	}

	@GetMapping(value="/modify/{boardId}")
	public ModelAndView modifyForm(ModelAndView mav,
								   @PathVariable Long boardId,
								   @ModelAttribute("board") BoardDto.BoardRequest request) {

		Board board = boardService.get(boardId);

		mav.addObject("board", BoardDto.BoardDetailResponse.of(board));
		mav.setViewName("board_modify");
		return mav;
	}

	@PostMapping(value="/modify/{boardId}")
	public ModelAndView modify(ModelAndView mav,
							   @PathVariable Long boardId,
							   @Valid @ModelAttribute("board") BoardDto.BoardRequest request,
							   BindingResult bindingResult) {

		if(bindingResult.hasErrors()){
			mav.setViewName("board_modify");
			return mav;
		}

		User user = new User();
		user.setUserId(1L);
		user.setNickname("유저1호");

		Board board = request.toEntity(boardId);
		board.setUpdater(user);
		boardService.update(board);

		mav.setViewName("redirect:/board/detail/"+boardId);
		return mav;
	}

	@PostMapping(value="/delete/{boardId}")
	public ModelAndView delete(ModelAndView mav,
							   @PathVariable Long boardId) {

		boardService.delete(boardId);

		mav.setViewName("redirect:/board/list");
		return mav;
	}
}
