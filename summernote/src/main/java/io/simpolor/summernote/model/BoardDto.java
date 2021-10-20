package io.simpolor.summernote.model;

import io.simpolor.summernote.repository.entity.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long boardId;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Board toEntity(){
        return Board.builder()
                .boardId(this.boardId)
                .title(this.title)
                .content(this.content)
                .build();
    }

    public static BoardDto of(Board board){
        return BoardDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }

    public static List<BoardDto> of(List<Board> boards){
        return boards.stream()
                .map(BoardDto::of)
                .collect(Collectors.toList());
    }
}
