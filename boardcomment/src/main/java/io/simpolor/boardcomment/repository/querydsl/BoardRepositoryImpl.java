package io.simpolor.boardcomment.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.simpolor.boardcomment.repository.entity.Board;
import io.simpolor.boardcomment.utils.QuerydslUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static io.simpolor.boardcomment.repository.entity.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public QueryResults<Board> findAllBySearch(Board search, Pageable pageable) {

        return queryFactory
                .selectFrom(board)
                .where(
                    board.isDeleted.eq(false),
                    QuerydslUtils.likeOperation(board.title, search.getTitle())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.boardId.desc())
                .fetchResults();
    }

    @Override
    public Long updateViews(Long boardId) {

        return queryFactory
                .update(board)
                .where(board.boardId.eq(boardId))
                .set(board.views, board.views.add(1))
                .execute();
    }
}
