package io.simpolor.toasteditor.service;

import io.simpolor.toasteditor.model.BoardDto;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
            throw new EntityNotFoundException("seq : "+seq);
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

        BoardDto original = boardMap.get(boardDto.getSeq());
        if(Objects.isNull(original)){
            throw new EntityNotFoundException("seq : "+boardDto.getSeq());
        }

        boardMap.put(boardDto.getSeq(), boardDto);

        return boardDto;
    }

    public void delete(long seq){

        BoardDto original = boardMap.get(seq);
        if(Objects.isNull(original)){
            throw new EntityNotFoundException("seq : "+seq);
        }
        boardMap.remove(seq);
    }


}
