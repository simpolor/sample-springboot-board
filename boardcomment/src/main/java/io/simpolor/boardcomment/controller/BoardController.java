package io.simpolor.boardcomment.controller;

import io.simpolor.boardcomment.model.BoardDto;
import io.simpolor.boardcomment.repository.entity.Board;
import io.simpolor.boardcomment.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@GetMapping("/list")
	public ModelAndView list(ModelAndView mav,
							 BoardDto.BoardSearch search,
							 @SortDefault(sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable){

		Page<Board> page = boardService.getAll(search.toEntity(), pageable);

		mav.addObject("search", search);
		mav.addObject("totalCount", page.getTotalElements());
		mav.addObject("boardList", BoardDto.BoardResponse.of(page.getContent()));
		mav.setViewName("board_list");
		return mav;
	}
}
