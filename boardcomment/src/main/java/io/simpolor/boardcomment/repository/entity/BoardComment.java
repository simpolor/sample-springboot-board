package io.simpolor.boardcomment.repository.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class BoardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardCommentId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String content;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "updater_id")
    private User updater;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Boolean isDeleted = Boolean.FALSE;
}
