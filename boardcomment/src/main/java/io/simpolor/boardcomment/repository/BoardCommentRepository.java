package io.simpolor.boardcomment.repository;

import io.simpolor.boardcomment.repository.entity.Board;
import io.simpolor.boardcomment.repository.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    List<BoardComment> findAllByBoardOrderByBoardCommentIdDesc(Board board);

}
