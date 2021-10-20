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

    public Board get(long id){

        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+id);
        }

        return optionalBoard.get();
    }

    public void create(Board board){

        boardRepository.save(board);
    }

    public void update(Board board){

        Optional<Board> optionalBoard = boardRepository.findById(board.getBoardId());
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+board.getBoardId());
        }

        boardRepository.save(board);
    }

    public void delete(long id){

        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+id);
        }

        boardRepository.deleteById(id);
    }


}
