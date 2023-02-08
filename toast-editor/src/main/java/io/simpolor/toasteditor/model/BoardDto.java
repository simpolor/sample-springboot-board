package io.simpolor.toasteditor.model;

import io.simpolor.toasteditor.repository.entity.Board;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.text.StringEscapeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BoardDto {

    @Getter
    @Setter
    public static class BoardRequest {

        private String title;
        private String content;

        public Board toEntity(){
            return this.toEntity(null);
        }

        public Board toEntity(Long id){
            Board board = new Board();
            board.setBoardId(id);
            board.setTitle(this.title);
            board.setContent(StringEscapeUtils.escapeHtml4(this.content));

            return board;
        }
    }

    @Getter
    @Setter
    public static class BoardResponse {

        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static BoardResponse of(Board board){
            BoardResponse response = new BoardResponse();
            response.setId(board.getBoardId());
            response.setTitle(board.getTitle());
            response.setContent(StringEscapeUtils.unescapeHtml4(board.getContent()));
            response.setCreatedAt(board.getCreatedAt());
            response.setUpdatedAt(board.getUpdatedAt());

            return response;
        }

        public static List<BoardResponse> of(List<Board> boards){
            return boards.stream()
                    .map(BoardResponse::of)
                    .collect(Collectors.toList());
        }
    }

}
