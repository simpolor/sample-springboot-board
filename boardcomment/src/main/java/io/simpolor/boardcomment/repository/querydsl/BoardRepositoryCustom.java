package io.simpolor.boardcomment.repository.querydsl;

import com.querydsl.core.QueryResults;
import io.simpolor.boardcomment.repository.entity.Board;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    QueryResults<Board> findAllBySearch(Board board, Pageable pageable);

    Long updateViews(Long boardId);
}
