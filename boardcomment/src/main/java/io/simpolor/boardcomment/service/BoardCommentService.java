package io.simpolor.boardcomment.service;

import io.simpolor.boardcomment.repository.BoardCommentRepository;
import io.simpolor.boardcomment.repository.entity.Board;
import io.simpolor.boardcomment.repository.entity.BoardComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCommentService {

    private final BoardCommentRepository boardCommentRepository;

    public List<BoardComment> getAll(Board board){

        return boardCommentRepository.findAllByBoardOrderByBoardCommentIdDesc(board);
    }

    public BoardComment get(Long boardCommentId) {

        Optional<BoardComment> optionalBoardComment = boardCommentRepository.findById(boardCommentId);
        if(!optionalBoardComment.isPresent()){
            throw new EntityNotFoundException("boardCommentId : "+boardCommentId);
        }

        return optionalBoardComment.get();
    }


    public BoardComment insert(BoardComment boardComment) {

        return boardCommentRepository.save(boardComment);
    }

    public BoardComment update(BoardComment boardComment) {

        Optional<BoardComment> optionalBoardComment = boardCommentRepository.findById(boardComment.getBoardCommentId());
        if(!optionalBoardComment.isPresent()){
            throw new EntityNotFoundException("boardCommentId : "+boardComment.getBoardCommentId());
        }

        BoardComment original = optionalBoardComment.get();
        original.setContent(boardComment.getContent());
        original.setUpdater(boardComment.getUpdater());

        return boardCommentRepository.save(original);
    }

    public BoardComment delete(Long boardCommentId) {

        Optional<BoardComment> optionalBoardComment = boardCommentRepository.findById(boardCommentId);
        if(!optionalBoardComment.isPresent()){
            throw new EntityNotFoundException("boardCommentId : "+boardCommentId);
        }

        BoardComment original = optionalBoardComment.get();

        boardCommentRepository.deleteById(boardCommentId);

        return original;
    }
}
