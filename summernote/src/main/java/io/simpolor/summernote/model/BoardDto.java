package io.simpolor.summernote.model;

import io.simpolor.summernote.repository.entity.Board;
import lombok.*;

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
            board.setContent(this.content);

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
            response.setContent(board.getContent());
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
