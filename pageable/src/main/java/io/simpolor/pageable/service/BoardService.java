package io.simpolor.pageable.service;

import io.simpolor.pageable.repository.BoardRepository;
import io.simpolor.pageable.repository.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<Board> getAll(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Board get(Long boardId){

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+boardId);
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

        Board origin = optionalBoard.get();
        origin.update(board);

        boardRepository.save(origin);
    }

    public void delete(Long boardId){

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+boardId);
        }

        boardRepository.deleteById(boardId);
    }


}
