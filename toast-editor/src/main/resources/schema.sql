CREATE TABLE `board` (
    `board_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '게시물 아이디',
    `title` VARCHAR(255) NOT NULL COMMENT '제목',
    `content` TEXT NULL DEFAULT NULL COMMENT '내용',
    `created_at` DATETIME NULL DEFAULT NULL COMMENT '생성일',
    `updated_at` DATETIME NULL DEFAULT NULL COMMENT '수정일',
    PRIMARY KEY (`board_id`)
);