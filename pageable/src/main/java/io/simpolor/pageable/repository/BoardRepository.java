package io.simpolor.pageable.repository;

import io.simpolor.pageable.repository.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
