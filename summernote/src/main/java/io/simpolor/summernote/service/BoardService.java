package io.simpolor.summernote.service;

import io.simpolor.summernote.repository.BoardRepository;
import io.simpolor.summernote.repository.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> getAll(){
        return boardRepository.findAll();
    }

    public Board get(Long boardId){

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+boardId);
        }

        return optionalBoard.get();
    }

    public Board create(Board board){

        return boardRepository.save(board);
    }

    public void update(Board board){

        Optional<Board> optionalBoard = boardRepository.findById(board.getBoardId());
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+board.getBoardId());
        }

        boardRepository.save(board);
    }

    public void delete(Long boardId){

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+boardId);
        }

        boardRepository.deleteById(boardId);
    }


}
