package io.simpolor.boardcomment.repository;

import io.simpolor.boardcomment.repository.entity.Board;
import io.simpolor.boardcomment.repository.querydsl.BoardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

}
