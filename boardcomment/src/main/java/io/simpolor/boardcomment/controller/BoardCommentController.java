package io.simpolor.boardcomment.controller;

import io.simpolor.boardcomment.model.BoardCommentDto;
import io.simpolor.boardcomment.repository.entity.Board;
import io.simpolor.boardcomment.repository.entity.BoardComment;
import io.simpolor.boardcomment.repository.entity.User;
import io.simpolor.boardcomment.service.BoardCommentService;
import io.simpolor.boardcomment.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardCommentController {

	private final BoardService boardService;
	private final BoardCommentService boardCommentService;

	@GetMapping(value="/{boardId}/comment/detail/{boardCommentId}")
	public BoardCommentDto.BoardCommentResponse detail(@PathVariable Long boardId,
													   @PathVariable Long boardCommentId) {

		boardService.verify(boardId);

		BoardComment boardComment = boardCommentService.get(boardCommentId);

		return BoardCommentDto.BoardCommentResponse.of(boardComment);
	}

	@PostMapping(value="/{boardId}/comment/register")
	public BoardCommentDto.BoardCommentResponse register(@PathVariable Long boardId,
														 @RequestBody BoardCommentDto.BoardCommentRequest request) {

		Board board = boardService.get(boardId);

		User user = new User();
		user.setUserId(1L);
		user.setNickname("유저1호");

		BoardComment boardComment = request.toEntity(board);
		boardComment.setCreator(user);
		boardComment.setUpdater(user);
		boardCommentService.insert(boardComment);

		return BoardCommentDto.BoardCommentResponse.of(boardComment);
	}

	@PostMapping(value="/{boardId}/comment/modify/{boardCommentId}")
	public BoardCommentDto.BoardCommentResponse modify(@PathVariable Long boardId,
					   @PathVariable Long boardCommentId,
					   @RequestBody BoardCommentDto.BoardCommentRequest request) {

		boardService.verify(boardId);

		User user = new User();
		user.setUserId(1L);
		user.setNickname("유저1호");

		BoardComment boardComment = request.toEntity(boardCommentId);
		boardComment.setUpdater(user);
		BoardComment result = boardCommentService.update(boardComment);

		return BoardCommentDto.BoardCommentResponse.of(result);
	}

	@PostMapping(value="/{boardId}/comment/delete/{boardCommentId}")
	public BoardCommentDto.BoardCommentResponse delete(@PathVariable Long boardId,
													   @PathVariable Long boardCommentId) {

		boardService.verify(boardId);

		BoardComment boardComment = boardCommentService.delete(boardCommentId);

		return BoardCommentDto.BoardCommentResponse.of(boardComment);
	}
}
