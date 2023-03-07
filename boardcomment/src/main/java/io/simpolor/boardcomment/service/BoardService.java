package io.simpolor.boardcomment.service;

import com.querydsl.core.QueryResults;
import io.simpolor.boardcomment.repository.BoardRepository;
import io.simpolor.boardcomment.repository.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<Board> getAll(Board board, Pageable pageable){

        QueryResults<Board> queryResults = boardRepository.findAllBySearch(board, pageable);

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    @Transactional
    public Board get(Long boardId) {

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+boardId);
        }

        Board board = optionalBoard.get();
        boardRepository.updateViews(board.getBoardId());

        return optionalBoard.get();
    }

    @Transactional
    public void verify(Long boardId) {

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+boardId);
        }
    }

    public Board insert(Board board) {

        return boardRepository.save(board);
    }

    public void update(Board board) {

        Optional<Board> optionalBoard = boardRepository.findById(board.getBoardId());
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+board.getBoardId());
        }

        Board origin = optionalBoard.get();
        origin.setTitle(board.getTitle());
        origin.setContent(board.getContent());
        origin.setUpdater(board.getUpdater());

        boardRepository.save(origin);
    }

    public void delete(Long boardId) {

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(!optionalBoard.isPresent()){
            throw new EntityNotFoundException("boardId : "+boardId);
        }

        Board origin = optionalBoard.get();
        origin.setIsDeleted(true);
        boardRepository.save(origin);
    }

}
