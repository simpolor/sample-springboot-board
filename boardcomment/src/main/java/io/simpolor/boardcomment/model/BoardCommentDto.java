package io.simpolor.boardcomment.model;

import io.simpolor.boardcomment.repository.entity.Board;
import io.simpolor.boardcomment.repository.entity.BoardComment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BoardCommentDto {

    @Getter
    @Setter
    public static class BoardCommentRequest {

        private String content;

        public BoardComment toEntity(Board board){
            BoardComment boardComment = new BoardComment();
            boardComment.setBoard(board);
            boardComment.setContent(this.content);

            return boardComment;
        }

        public BoardComment toEntity(Long id){
            BoardComment boardComment = new BoardComment();
            boardComment.setBoardCommentId(id);
            boardComment.setContent(this.content);

            return boardComment;
        }
    }

    @Getter
    @Setter
    public static class BoardCommentResponse {

        private Long id;
        private String content;

        private UserDto.UserSummary creator;
        private LocalDateTime createdAt;

        public static BoardCommentResponse of(BoardComment boardComment){
            BoardCommentResponse response = new BoardCommentResponse();
            response.setId(boardComment.getBoardCommentId());
            response.setContent(boardComment.getContent());
            response.setCreator(UserDto.UserSummary.of(boardComment.getCreator()));
            response.setCreatedAt(boardComment.getCreatedAt());

            return response;
        }

        public static List<BoardCommentResponse> of(List<BoardComment> boardComments){
            return boardComments.stream()
                    .map(BoardCommentResponse::of)
                    .collect(Collectors.toList());
        }
    }

}
