package io.simpolor.pageable.controller;

import io.simpolor.pageable.model.BoardDto;
import io.simpolor.pageable.repository.entity.Board;
import io.simpolor.pageable.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping(("/board"))
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@GetMapping("/list")
	public ModelAndView listServer(ModelAndView mav,
								   @PageableDefault(sort={"boardId"}, direction=Sort.Direction.DESC) Pageable pageable) {

		Page<Board> page = boardService.getAll(pageable);
		if(!page.isEmpty() && page.getTotalPages() > 0){
			List<Integer> pageNumbers = IntStream.rangeClosed(1, page.getTotalPages())
					.boxed()
					.collect(Collectors.toList());
			mav.addObject("pageNumbers", pageNumbers);

			Page<BoardDto> boardPage = new PageImpl(BoardDto.of(page.getContent()), page.getPageable(), page.getTotalElements());
			mav.addObject("boardPage", boardPage);
		}

		mav.setViewName("board_list");
		return mav;
	}

	@GetMapping("/detail/{boardId}")
	public ModelAndView detail(ModelAndView mav,
							   @PathVariable Long boardId) {

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
