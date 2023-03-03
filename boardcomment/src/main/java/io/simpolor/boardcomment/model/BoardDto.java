package io.simpolor.boardcomment.model;

import io.simpolor.boardcomment.repository.entity.Board;
import lombok.Getter;
import lombok.Setter;

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
        private Long views;

        private UserDto.UserSummary creator;
        private LocalDateTime createdAt;

        public static BoardResponse of(Board board){
            BoardResponse response = new BoardResponse();
            response.setId(board.getBoardId());
            response.setTitle(board.getTitle());
            response.setContent(board.getContent());
            response.setCreatedAt(board.getCreatedAt());
            response.setCreator(UserDto.UserSummary.of(board.getCreator()));
            response.setViews(board.getViews());

            return response;
        }

        public static List<BoardResponse> of(List<Board> boards){
            return boards.stream()
                    .map(BoardResponse::of)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class BoardDetailResponse {

        private Long id;
        private String title;
        private String content;
        private Long views;

        private UserDto.UserSummary creator;
        private LocalDateTime createdAt;
        private UserDto.UserSummary updater;
        private LocalDateTime updatedAt;

        public static BoardDetailResponse of(Board board){
            BoardDetailResponse response = new BoardDetailResponse();
            response.setId(board.getBoardId());
            response.setTitle(board.getTitle());
            response.setContent(board.getContent());
            response.setViews(board.getViews());
            response.setCreator(UserDto.UserSummary.of(board.getCreator()));
            response.setCreatedAt(board.getCreatedAt());
            response.setUpdater(UserDto.UserSummary.of(board.getUpdater()));
            response.setUpdatedAt(board.getUpdatedAt());

            return response;
        }
    }

    @Getter
    @Setter
    public static class BoardSearch {
        private String title;

        public Board toEntity(){
            Board board = new Board();
            board.setTitle(this.title);

            return board;
        }
    }

}
