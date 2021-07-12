package io.simpolor.summernote.service;

import io.simpolor.summernote.model.BoardDto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoardService {

    long seq = 0;
    Map<Long, BoardDto> boardMap = new HashMap<>();

    public List<BoardDto> list(){

        if(boardMap.isEmpty()){
            return new ArrayList<>();
        }

        return boardMap.values().stream().collect(Collectors.toList());
    }

    public BoardDto get(long seq){

        BoardDto boardDto = boardMap.get(seq);
        if(Objects.isNull(boardDto)){
            throw new IllegalArgumentException("seq : "+seq);
        }

        return boardDto;
    }

    public BoardDto create(BoardDto boardDto){

        seq = seq + 1;
        boardDto.setSeq(seq);
        boardMap.put(seq, boardDto);

        return boardDto;
    }

    public BoardDto update(BoardDto boardDto){

        BoardDto boardDto1= boardMap.get(seq);
        if(Objects.isNull(boardDto1)){
            throw new IllegalArgumentException("seq : "+seq);
        }

        boardMap.put(boardDto.getSeq(), boardDto);

        return boardDto;
    }

    public void delete(long seq){

        boardMap.remove(seq);
    }


}
