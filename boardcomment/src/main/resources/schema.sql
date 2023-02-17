CREATE TABLE `user` (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '회원 아이디',
    `nickname` VARCHAR(50) NOT NULL COMMENT '닉네임',
    `created_at` DATETIME NULL DEFAULT NULL COMMENT '최초 가입일',
    `updated_at` DATETIME NULL DEFAULT NULL COMMENT '수정일',
    `is_deleted` TINYINT(1) NULL DEFAULT 0 COMMENT '탈퇴 유무',
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `board` (
    `board_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '게시물 아이디',
    `title` VARCHAR(255) NOT NULL COMMENT '제목',
    `content` TEXT NULL DEFAULT NULL COMMENT '내용',
    `views` BIGINT NOT NULL DEFAULT 0 COMMENT '조회수',
    `created_at` DATETIME NULL DEFAULT NULL COMMENT '생성일',
    `creator_id` BIGINT NULL DEFAULT NULL COMMENT '생성자 아이디',
    `updated_at` DATETIME NULL DEFAULT NULL COMMENT '수정일',
    `updater_id` BIGINT NULL DEFAULT NULL COMMENT '수정자 아이디',
    `is_deleted` TINYINT(1) NULL DEFAULT 0 COMMENT '삭제 유무',
    PRIMARY KEY (`board_id`)
);

CREATE TABLE `board_comment` (
    `board_comment_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '게시물 댓글 아이디',
    `board_id` BIGINT NOT NULL COMMENT '게시물 아이디',
    `content` TEXT NULL DEFAULT NULL COMMENT '내용',
    `created_at` DATETIME NULL DEFAULT NULL COMMENT '생성일',
    `creator_id` BIGINT NULL DEFAULT NULL COMMENT '생성자 아이디',
    `updated_at` DATETIME NULL DEFAULT NULL COMMENT '수정일',
    `updater_id` BIGINT NULL DEFAULT NULL COMMENT '수정자 아이디',
    `is_deleted` TINYINT(1) NULL DEFAULT 0 COMMENT '삭제 유무',
    PRIMARY KEY (`board_comment_id`)
);