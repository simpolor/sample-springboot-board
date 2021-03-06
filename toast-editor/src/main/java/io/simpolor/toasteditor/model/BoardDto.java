package io.simpolor.toasteditor.model;

import io.simpolor.toasteditor.repository.entity.Board;
import lombok.*;
import org.apache.commons.text.StringEscapeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Board toEntity(){
        return Board.builder()
                .boardId(this.id)
                .title(this.title)
                //.content(this.content)
                .content(StringEscapeUtils.escapeHtml4(this.content))
                .build();
    }

    public static BoardDto of(Board board){
        return BoardDto.builder()
                .id(board.getBoardId())
                .title(board.getTitle())
                .content(StringEscapeUtils.unescapeHtml4(board.getContent()))
                // .content(board.getContent())
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
